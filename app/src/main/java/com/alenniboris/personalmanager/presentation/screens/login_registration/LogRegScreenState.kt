package com.alenniboris.personalmanager.presentation.screens.login_registration

import com.alenniboris.personalmanager.presentation.model.UserModelUi

sealed class LogRegScreenState(
    val currentProcess: LogRegScreenProcess
) {

    data class Login(
        val email: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false
    ) : LogRegScreenState(currentProcess = LogRegScreenProcess.Login)

    data class Registration(
        val user: UserModelUi = UserModelUi(),
        val password: String = "",
        val passwordCheck: String = "",
        val isPasswordVisible: Boolean = false,
        val isPasswordCheckVisible: Boolean = false
    ) : LogRegScreenState(currentProcess = LogRegScreenProcess.Registration)
}