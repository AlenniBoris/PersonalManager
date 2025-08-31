package com.alenniboris.personalmanager.presentation.screens.activity

import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType

data class MainActivityState(
    val isAuthenticated: Boolean,
    val requestedPermission: PermissionType? = null,
    val isPermissionDialogVisible: Boolean = false
)
