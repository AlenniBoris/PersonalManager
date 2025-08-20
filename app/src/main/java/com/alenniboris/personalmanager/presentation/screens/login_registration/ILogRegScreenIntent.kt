package com.alenniboris.personalmanager.presentation.screens.login_registration

sealed interface ILogRegScreenIntent {
    data class ChangeProcess(val process: LogRegScreenProcess) : ILogRegScreenIntent
    data class UpdateEmail(val newValue: String) : ILogRegScreenIntent
    data class UpdatePassword(val newValue: String) : ILogRegScreenIntent
    data class UpdateFullName(val newValue: String) : ILogRegScreenIntent
    data class UpdatePasswordCheck(val newValue: String) : ILogRegScreenIntent
    data object ProceedFinalButtonAction : ILogRegScreenIntent
    data object UpdatePasswordVisibility : ILogRegScreenIntent
    data object UpdatePasswordCheckVisibility : ILogRegScreenIntent
}