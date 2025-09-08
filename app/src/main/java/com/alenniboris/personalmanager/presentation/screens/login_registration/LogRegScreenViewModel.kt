package com.alenniboris.personalmanager.presentation.screens.login_registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.usecase.logic.user.ILoginUserByEmailAndPasswordUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IRegisterUserUseCase
import com.alenniboris.personalmanager.domain.utils.SingleFlowEvent
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.toDomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogRegScreenViewModel @Inject constructor(
    private val loginUserByEmailAndPasswordUseCase: ILoginUserByEmailAndPasswordUseCase,
    private val registerUserUseCase: IRegisterUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<LogRegScreenState>(LogRegScreenState.Login())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<ILogRegScreenEvent>(viewModelScope)
    val event = _event.flow

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
                        Log.e("!!!!", "login success")
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
                        Log.e("!!!!", "register succ")
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
            }
        }
    }

    private fun updatePasswordCheck(newValue: String) {
        _state.update {
            when (it) {
                is LogRegScreenState.Login -> it
                is LogRegScreenState.Registration -> it.copy(passwordCheck = newValue)
            }
        }
    }

    private fun updatePassword(newValue: String) {
        _state.update {
            when (it) {
                is LogRegScreenState.Login -> it.copy(password = newValue)
                is LogRegScreenState.Registration -> it.copy(password = newValue)
            }
        }
    }

    private fun updateFullName(newValue: String) {
        _state.update {
            when (it) {
                is LogRegScreenState.Login -> it
                is LogRegScreenState.Registration -> it.copy(user = it.user.copy(name = newValue))
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
        }
    }
}