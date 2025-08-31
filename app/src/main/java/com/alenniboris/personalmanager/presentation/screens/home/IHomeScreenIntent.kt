package com.alenniboris.personalmanager.presentation.screens.home

import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType

interface IHomeScreenIntent {
    data class UpdateRequestedPermissionAndShowDialog(
        val newRequestedPermission: PermissionType
    ) : IHomeScreenIntent

    data object OpenSettingsAndHidePermissionDialog : IHomeScreenIntent
}