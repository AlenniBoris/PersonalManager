package com.alenniboris.personalmanager.presentation.screens.login_registration

import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.usecase.logic.user.ILoginUserByEmailAndPasswordUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IRegisterUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.ISendResetPasswordLinkUseCase
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.domain.utils.SingleFlowEvent
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.user.toDomainModel
import com.alenniboris.personalmanager.presentation.screens.login_registration.views.LoginProcessUi
import com.alenniboris.personalmanager.presentation.uikit.utils.DatastoreRepository
import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import com.alenniboris.personalmanager.presentation.uikit.values.DatastoreValues
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class LogRegScreenViewModel @Inject constructor(
    private val loginUserByEmailAndPasswordUseCase: ILoginUserByEmailAndPasswordUseCase,
    private val registerUserUseCase: IRegisterUserUseCase,
    private val datastore: DatastoreRepository,
    private val sendResetPasswordLinkUseCase: ISendResetPasswordLinkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LogRegScreenState>(LogRegScreenState.Login())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<ILogRegScreenEvent>(viewModelScope)
    val event = _event.flow

    private var locationJob: Job? = null

    fun proceedIntent(intent: ILogRegScreenIntent) {
        when (intent) {
            is ILogRegScreenIntent.ChangeProcess -> changeProcess(intent.process)
            is ILogRegScreenIntent.UpdateFullName -> updateFullName(intent.newValue)
            is ILogRegScreenIntent.UpdatePassword -> updatePassword(intent.newValue)
            is ILogRegScreenIntent.UpdatePasswordCheck -> updatePasswordCheck(intent.newValue)
            is ILogRegScreenIntent.UpdateEmail -> updateEmail(intent.newValue)
            is ILogRegScreenIntent.ProceedFinalButtonAction -> proceedFinalButtonAction()
            is ILogRegScreenIntent.UpdatePasswordVisibility -> updatePasswordVisibility()
            is ILogRegScreenIntent.UpdatePasswordCheckVisibility -> updatePasswordCheckVisibility()
            is ILogRegScreenIntent.GetUserLocation -> getUserLocation(intent.fusedLocationProviderClient)
            is ILogRegScreenIntent.OpenSettingsAndHidePermissionDialog -> openSettingsAndHidePermissionDialog()
            is ILogRegScreenIntent.UpdateRequestedPermissionAndShowDialog -> updateRequestedPermissionAndShowDialog(
                intent.newRequestedPermission
            )

            is ILogRegScreenIntent.UpdateRequestedPermission -> updateRequestedPermission(
                intent.permission
            )

            is ILogRegScreenIntent.ChangeBackToLogin -> changeBackToLogin()
        }
    }

    private fun changeBackToLogin() {
        _state.update { LogRegScreenState.Login() }
    }

    private fun updateRequestedPermission(permission: PermissionType) {
        _state.update { state ->
            (state as? LogRegScreenState.Login)?.copy(requestedPermission = permission) ?: state
        }
    }

    private fun openSettingsAndHidePermissionDialog() {
        _event.emit(
            ILogRegScreenEvent.OpenSettings
        )
        hidePermissionDialog()
    }

    private fun hidePermissionDialog() {
        _state.update { state ->
            (state as? LogRegScreenState.Login)?.copy(isPermissionDialogVisible = false) ?: state
        }
    }

    private fun updateRequestedPermissionAndShowDialog(newPermission: PermissionType) {
        _state.update { state ->
            (state as? LogRegScreenState.Login)?.copy(
                requestedPermission = newPermission,
                isPermissionDialogVisible = true
            ) ?: state
        }
    }

    @Suppress("MissingPermission")
    private fun getUserLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        locationJob?.cancel()
        locationJob = viewModelScope.launch(SupervisorJob() + Dispatchers.IO) {
            try {
                val location = fusedLocationProviderClient.lastLocation.await()
                location?.let {
                    datastore.saveData(DatastoreValues.LATITUDE, location.latitude.toString())
                    datastore.saveData(DatastoreValues.LONGITUDE, location.longitude.toString())
                } ?: requestSingleLocationUpdate(fusedLocationProviderClient)
            } catch (e: SecurityException) {
                LogPrinter.printLog("!!!", "Permission denied: ${e.message}")
            } catch (e: Exception) {
                LogPrinter.printLog("!!!", "Error getting location: ${e.message}")
            }
        }
    }

    @Suppress("MissingPermission")
    private suspend fun requestSingleLocationUpdate(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 0L
        ).setMaxUpdates(1).build()

        val location = suspendCancellableCoroutine { cont ->
            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    fusedLocationProviderClient.removeLocationUpdates(this)
                    cont.resume(result.lastLocation, null)
                }

                override fun onLocationAvailability(p0: LocationAvailability) {
                    if (!p0.isLocationAvailable) {
                        fusedLocationProviderClient.removeLocationUpdates(this)
                        cont.resume(null, null)
                    }
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                callback,
                Looper.getMainLooper()
            )

            cont.invokeOnCancellation {
                fusedLocationProviderClient.removeLocationUpdates(callback)
            }
        }

        location?.let {
            datastore.saveData(DatastoreValues.LATITUDE, it.latitude.toString())
            datastore.saveData(DatastoreValues.LONGITUDE, it.longitude.toString())
        }
    }

    private fun updatePasswordVisibility() {
        _state.update { state ->
            (state as? LogRegScreenState.Login)?.let { casted ->
                casted.copy(isPasswordVisible = !casted.isPasswordVisible)
            } ?: (state as? LogRegScreenState.Registration)?.let { casted ->
                casted.copy(isPasswordVisible = !casted.isPasswordVisible)
            } ?: state
        }
    }

    private fun updatePasswordCheckVisibility() {
        _state.update { state ->
            (state as? LogRegScreenState.Registration)?.let { casted ->
                casted.copy(isPasswordCheckVisible = !casted.isPasswordCheckVisible)
            } ?: state
        }
    }

    private fun proceedFinalButtonAction() {
        when (_state.value) {
            is LogRegScreenState.Login -> {
                proceedUserLogin()
            }

            is LogRegScreenState.Registration -> {
                proceedUserRegistration()
            }

            is LogRegScreenState.PasswordReset -> sendResetLink()
        }
    }

    private fun sendResetLink() {
        (_state.value as? LogRegScreenState.PasswordReset)?.let { state ->
            viewModelScope.launch {
                when (
                    val res = sendResetPasswordLinkUseCase.invoke(state.email)
                ) {
                    is CustomResultModelDomain.Success -> {
                        _event.emit(
                            ILogRegScreenEvent.ShowToast(
                                R.string.email_send_text
                            )
                        )
                        changeBackToLogin()
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            ILogRegScreenEvent.ShowToast(
                                res.exception.toUiString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun proceedUserRegistration() {
        (_state.value as? LogRegScreenState.Registration)?.let {
            viewModelScope.launch {
                when (
                    val res = registerUserUseCase.invoke(
                        user = it.user.toDomainModel(),
                        password = it.password,
                        passwordCheck = it.passwordCheck
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        _event.emit(
                            ILogRegScreenEvent.ShowToast(
                                R.string.welcome_text
                            )
                        )
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            ILogRegScreenEvent.ShowToast(
                                res.exception.toUiString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun proceedUserLogin() {
        (_state.value as? LogRegScreenState.Login)?.let {
            viewModelScope.launch {
                when (
                    val res = loginUserByEmailAndPasswordUseCase.invoke(
                        email = it.email,
                        password = it.password
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        _event.emit(
                            ILogRegScreenEvent.ShowToast(
                                R.string.welcome_back_text
                            )
                        )
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            ILogRegScreenEvent.ShowToast(
                                res.exception.toUiString()
                            )
                        )
                    }
                }
            }
        }
    }

    private fun updateEmail(newValue: String) {
        _state.update {
            when (it) {
                is LogRegScreenState.Login -> it.copy(email = newValue)
                is LogRegScreenState.Registration -> it.copy(user = it.user.copy(email = newValue))
                is LogRegScreenState.PasswordReset -> it.copy(email = newValue)
            }
        }
    }

    private fun updatePasswordCheck(newValue: String) {
        _state.update {
            when (it) {
                is LogRegScreenState.Login -> it
                is LogRegScreenState.Registration -> it.copy(passwordCheck = newValue)
                is LogRegScreenState.PasswordReset -> it
            }
        }
    }

    private fun updatePassword(newValue: String) {
        _state.update {
            when (it) {
                is LogRegScreenState.Login -> it.copy(password = newValue)
                is LogRegScreenState.Registration -> it.copy(password = newValue)
                is LogRegScreenState.PasswordReset -> it
            }
        }
    }

    private fun updateFullName(newValue: String) {
        _state.update {
            when (it) {
                is LogRegScreenState.Login -> it
                is LogRegScreenState.Registration -> it.copy(user = it.user.copy(name = newValue))
                is LogRegScreenState.PasswordReset -> it
            }
        }
    }

    private fun changeProcess(process: LogRegScreenProcess) {
        when (process) {
            LogRegScreenProcess.Login -> {
                (_state.value as? LogRegScreenState.Registration)?.let {
                    _state.update { LogRegScreenState.Login() }
                }
            }

            LogRegScreenProcess.Registration -> {
                (_state.value as? LogRegScreenState.Login)?.let {
                    _state.update { LogRegScreenState.Registration() }
                }
            }

            LogRegScreenProcess.PasswordReset -> {
                (_state.value as? LogRegScreenState.Login)?.let {
                    _state.update { LogRegScreenState.PasswordReset() }
                }
            }
        }
    }
}