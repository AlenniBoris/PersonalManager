package com.alenniboris.personalmanager.presentation.screens.login_registration

import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import com.google.android.gms.location.FusedLocationProviderClient

sealed interface ILogRegScreenIntent {
    data class ChangeProcess(val process: LogRegScreenProcess) : ILogRegScreenIntent
    data class UpdateEmail(val newValue: String) : ILogRegScreenIntent
    data class UpdatePassword(val newValue: String) : ILogRegScreenIntent
    data class UpdateFullName(val newValue: String) : ILogRegScreenIntent
    data class UpdatePasswordCheck(val newValue: String) : ILogRegScreenIntent
    data object ProceedFinalButtonAction : ILogRegScreenIntent
    data object UpdatePasswordVisibility : ILogRegScreenIntent
    data object UpdatePasswordCheckVisibility : ILogRegScreenIntent
    data class GetUserLocation(
        val fusedLocationProviderClient: FusedLocationProviderClient
    ) : ILogRegScreenIntent

    data class UpdateRequestedPermissionAndShowDialog(
        val newRequestedPermission: PermissionType
    ) : ILogRegScreenIntent

    data class UpdateRequestedPermission(
        val permission: PermissionType
    ) : ILogRegScreenIntent

    data object OpenSettingsAndHidePermissionDialog : ILogRegScreenIntent
}