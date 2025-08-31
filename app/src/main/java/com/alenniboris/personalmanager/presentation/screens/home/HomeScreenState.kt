package com.alenniboris.personalmanager.presentation.screens.home

import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType

data class HomeScreenState(
    val requestedPermission: PermissionType? = null,
    val isPermissionDialogVisible: Boolean = false
)
