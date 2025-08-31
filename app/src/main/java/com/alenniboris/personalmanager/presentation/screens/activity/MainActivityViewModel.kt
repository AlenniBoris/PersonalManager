package com.alenniboris.personalmanager.presentation.screens.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.utils.SingleFlowEvent
import com.alenniboris.personalmanager.presentation.uikit.utils.DatastoreRepository
import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import com.alenniboris.personalmanager.presentation.uikit.values.DatastoreValues
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val datastore: DatastoreRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        MainActivityState(
            isAuthenticated = getCurrentUserUseCase.userFlow.value != null
        )
    )
    val state = _state.asStateFlow()
    private val _event = SingleFlowEvent<IMainActivityEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.userFlow.collect { user ->
                _state.update { it.copy(isAuthenticated = user != null) }
            }
        }
    }

    fun proceedIntent(intent: IMainActivityIntent) {
        when (intent) {
            is IMainActivityIntent.GetUserLocation -> getUserLocation(intent.fusedLocationProviderClient)
            is IMainActivityIntent.OpenSettingsAndHidePermissionDialog -> openSettingsAndHidePermissionDialog()
            is IMainActivityIntent.UpdateRequestedPermissionAndShowDialog -> updateRequestedPermissionAndShowDialog(
                intent.newRequestedPermission
            )
        }
    }

    private fun openSettingsAndHidePermissionDialog() {
        _event.emit(
            IMainActivityEvent.OpenSettings
        )
        hidePermissionDialog()
    }

    private fun hidePermissionDialog() {
        _state.update {
            it.copy(
                isPermissionDialogVisible = false
            )
        }
    }

    private fun updateRequestedPermissionAndShowDialog(newPermission: PermissionType) {
        _state.update {
            it.copy(
                requestedPermission = newPermission,
                isPermissionDialogVisible = true
            )
        }
    }

    @Suppress("MissingPermission")
    private fun getUserLocation(fusedLocationProviderClient: FusedLocationProviderClient) {
        viewModelScope.launch {
            val location = fusedLocationProviderClient.lastLocation.await()
            datastore.saveData(
                key = DatastoreValues.LATITUDE,
                value = location.latitude.toString()
            )
            datastore.saveData(
                key = DatastoreValues.LONGITUDE,
                value = location.longitude.toString()
            )
        }
    }
}