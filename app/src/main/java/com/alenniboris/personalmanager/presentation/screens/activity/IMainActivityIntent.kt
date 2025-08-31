package com.alenniboris.personalmanager.presentation.screens.activity

import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import com.google.android.gms.location.FusedLocationProviderClient

sealed interface IMainActivityIntent {
    data class GetUserLocation(
        val fusedLocationProviderClient: FusedLocationProviderClient
    ) : IMainActivityIntent

    data class UpdateRequestedPermissionAndShowDialog(
        val newRequestedPermission: PermissionType
    ): IMainActivityIntent

    data object OpenSettingsAndHidePermissionDialog : IMainActivityIntent
}