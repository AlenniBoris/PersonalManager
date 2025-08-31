package com.alenniboris.personalmanager.presentation.uikit.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alenniboris.personalmanager.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun launchForPermission(
    permission: PermissionType,
    context: Context,
    onPermissionGrantedAction: (PermissionType) -> Unit,
    onPermissionNotGrantedAction: (PermissionType) -> Unit,
    onShowRationale: (PermissionType) -> Unit,
    onLaunchAgain: (PermissionType) -> Unit
) {
    when {
        ContextCompat.checkSelfPermission(
            context,
            permission.toPermission()
        ) == PackageManager.PERMISSION_GRANTED -> {
            onPermissionGrantedAction(permission)
        }

        ActivityCompat.shouldShowRequestPermissionRationale(
            context.findActivity(), permission.toPermission()
        ) -> {
            onShowRationale(permission)
        }

        else -> {
            onPermissionNotGrantedAction(permission)
            onLaunchAgain(permission)
        }
    }
}

enum class PermissionType {
    PERMISSION_COARSE_LOCATION,
    PERMISSION_FINE_LOCATION,
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun PermissionType.toPermission(): String = when (this) {
    PermissionType.PERMISSION_COARSE_LOCATION -> Manifest.permission.ACCESS_COARSE_LOCATION
    PermissionType.PERMISSION_FINE_LOCATION -> Manifest.permission.ACCESS_FINE_LOCATION
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun PermissionType.toPermissionExplanation(): Int = when (this) {
    PermissionType.PERMISSION_COARSE_LOCATION -> R.string.coarse_location_permission_explanation
    PermissionType.PERMISSION_FINE_LOCATION -> R.string.fine_location_permission_explanation
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun PermissionType.toPermissionName(): Int = when (this) {
    PermissionType.PERMISSION_COARSE_LOCATION -> R.string.coarse_location_permission_name
    PermissionType.PERMISSION_FINE_LOCATION -> R.string.fine_location_permission_name
}