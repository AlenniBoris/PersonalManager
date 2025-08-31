package com.alenniboris.personalmanager.presentation.uikit.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import com.alenniboris.personalmanager.presentation.uikit.utils.toPermissionExplanation
import com.alenniboris.personalmanager.presentation.uikit.utils.toPermissionName

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppPermissionRationaleDialog(
    permissionType: PermissionType,
    onOpenSettings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = onOpenSettings
            ) {
                Text(
                    style = bodyStyle.copy(
                        color = appMainTextColor
                    ),
                    text = "Go to settings"
                )
            }
        },
        dismissButton = {},
        title = {
            Text(
                style = bodyStyle.copy(
                    color = appMainTextColor
                ),
                text = stringResource(permissionType.toPermissionName())
            )
        },
        text = {
            Text(
                style = bodyStyle.copy(
                    color = appMainTextColor
                ),
                text = stringResource(permissionType.toPermissionExplanation())
            )
        }
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                AppPermissionRationaleDialog(
                    permissionType = PermissionType.PERMISSION_COARSE_LOCATION,
                    onOpenSettings = {}
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun DarkTheme() {
    PersonalManagerTheme(
        darkTheme = true
    ) {
        Surface {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                AppPermissionRationaleDialog(
                    permissionType = PermissionType.PERMISSION_COARSE_LOCATION,
                    onOpenSettings = {}
                )
            }
        }
    }
}