package com.alenniboris.personalmanager.presentation.screens.login_registration

import com.alenniboris.personalmanager.presentation.model.user.UserModelUi
import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType

sealed class LogRegScreenState(
    val currentProcess: LogRegScreenProcess
) {

    data class Login(
        val email: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val requestedPermission: PermissionType? = null,
        val isPermissionDialogVisible: Boolean = false
    ) : LogRegScreenState(currentProcess = LogRegScreenProcess.Login)

    data class Registration(
        val user: UserModelUi = UserModelUi(),
        val password: String = "",
        val passwordCheck: String = "",
        val isPasswordVisible: Boolean = false,
        val isPasswordCheckVisible: Boolean = false
    ) : LogRegScreenState(currentProcess = LogRegScreenProcess.Registration)

    data class PasswordReset(
        val email: String = ""
    ) : LogRegScreenState(currentProcess = LogRegScreenProcess.PasswordReset)
}