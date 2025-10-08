package com.alenniboris.personalmanager.presentation.uikit.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeBig
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
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
        containerColor = appColor,
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = onOpenSettings,
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = appColor
                )
            ) {
                Text(
                    style = bodyStyle.copy(
                        color = appMainTextColor
                    ),
                    text = stringResource(R.string.go_to_settings_text)
                )
            }
        },
        dismissButton = {},
        title = {
            Text(
                style = bodyStyle.copy(
                    fontSize = appTextSizeMedium,
                    color = appMainTextColor,
                    fontWeight = FontWeight.Bold
                ),
                text = stringResource(permissionType.toPermissionName())
            )
        },
        text = {
            Text(
                style = bodyStyle.copy(
                    fontSize = appTextSize,
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