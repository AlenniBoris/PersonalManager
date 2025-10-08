package com.alenniboris.personalmanager.presentation.screens.login_registration.views

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.screens.login_registration.ILogRegScreenIntent
import com.alenniboris.personalmanager.presentation.screens.login_registration.LogRegScreenProcess
import com.alenniboris.personalmanager.presentation.screens.login_registration.LogRegScreenState
import com.alenniboris.personalmanager.presentation.screens.login_registration.toUiString
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appIcon
import com.alenniboris.personalmanager.presentation.uikit.theme.appIconPlaceholderShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenColumnPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenFinalButtonPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenHeaderTextBottomPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenIconInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenInnerSectionPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenMainShape
import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import com.alenniboris.personalmanager.presentation.uikit.utils.launchForPermission
import com.alenniboris.personalmanager.presentation.uikit.utils.toPermission
import com.alenniboris.personalmanager.presentation.uikit.views.AppButtonRow
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppIconPlaceholder
import com.alenniboris.personalmanager.presentation.uikit.views.AppPermissionRationaleDialog
import com.google.android.gms.location.LocationServices


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LogRegScreenUi(
    state: LogRegScreenState,
    proceedIntent: (ILogRegScreenIntent) -> Unit
) {

    val context = LocalContext.current

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
        context
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            proceedIntent(
                ILogRegScreenIntent.GetUserLocation(
                    fusedLocationProviderClient = fusedLocationProviderClient
                )
            )
        } else {
            proceedIntent(
                ILogRegScreenIntent.UpdateRequestedPermission(
                    PermissionType.PERMISSION_FINE_LOCATION
                )
            )
        }
    }
    (state as? LogRegScreenState.Login)?.let {
        LaunchedEffect(state.requestedPermission) {
            launchForPermission(
                permission = PermissionType.PERMISSION_FINE_LOCATION,
                context = context,
                onPermissionGrantedAction = {
                    proceedIntent(
                        ILogRegScreenIntent.GetUserLocation(
                            fusedLocationProviderClient = fusedLocationProviderClient
                        )
                    )
                },
                onPermissionNotGrantedAction = {},
                onShowRationale = { permission ->
                    proceedIntent(
                        ILogRegScreenIntent.UpdateRequestedPermissionAndShowDialog(
                            newRequestedPermission = permission
                        )
                    )
                },
                onLaunchAgain = { permission ->
                    permissionLauncher.launch(permission.toPermission())
                }
            )
        }
    }
    (state as? LogRegScreenState.Login)?.let {
        if (state.isPermissionDialogVisible) {
            state.requestedPermission?.let {
                AppPermissionRationaleDialog(
                    permissionType = it,
                    onOpenSettings = {
                        proceedIntent(
                            ILogRegScreenIntent.OpenSettingsAndHidePermissionDialog
                        )
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(logRegScreenBackgroundColor)
            .padding(logRegScreenColumnPadding)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppIconPlaceholder(
            modifier = Modifier
                .clip(appIconPlaceholderShape)
                .background(appMainTextColor)
                .padding(logRegScreenIconInnerPadding),
            icon = painterResource(appIcon)
        )

        HeaderTextSection()

        MainSection(
            state = state,
            proceedIntent = proceedIntent
        )
    }
}

@Composable
private fun MainSection(
    state: LogRegScreenState,
    proceedIntent: (ILogRegScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .clip(logRegScreenMainShape)
            .fillMaxWidth()
            .background(appColor)
            .padding(logRegScreenColumnPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HeaderTextSection(
            mainText = stringResource(R.string.get_started_text),
            subtleTextString = stringResource(R.string.log_reg_options_description)
        )

        AppButtonRow(
            modifier = Modifier
                .clip(appRoundedShape)
                .fillMaxWidth()
                .background(buttonRowBackgroundColor)
                .padding(appButtonRowInnerPadding),
            currentElement = ClickableElement(
                text = stringResource(state.currentProcess.toUiString()),
                onClick = {}
            ),
            listOfElements = LogRegScreenProcess.entries.toList().map {
                ClickableElement(
                    text = stringResource(it.toUiString()),
                    onClick = {
                        proceedIntent(
                            ILogRegScreenIntent.ChangeProcess(it)
                        )
                    }
                )
            }
        )

        when (state) {
            is LogRegScreenState.Login ->
                LoginProcessUi(
                    state = state,
                    proceedIntent = proceedIntent
                )

            is LogRegScreenState.Registration ->
                RegistrationProcessUi(
                    state = state,
                    proceedIntent = proceedIntent
                )
        }

        AppCustomButton(
            modifier = Modifier
                .padding(logRegScreenFinalButtonPadding)
                .fillMaxWidth(),
            text = stringResource(state.currentProcess.toUiString()),
            onClick = {
                proceedIntent(
                    ILogRegScreenIntent.ProceedFinalButtonAction
                )
            }
        )
    }
}

@Composable
private fun HeaderTextSection(
    mainText: String = stringResource(R.string.app_name),
    subtleTextString: String = stringResource(R.string.app_description)
) {

    Column(
        modifier = Modifier.padding(logRegScreenInnerSectionPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(logRegScreenHeaderTextBottomPadding),
            text = mainText,
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSizeMedium,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            modifier = Modifier.padding(logRegScreenHeaderTextBottomPadding),
            text = subtleTextString,
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize
            )
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
@Preview
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                LogRegScreenUi(
                    state = LogRegScreenState.Login(),
                    proceedIntent = {}
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
            Box(modifier = Modifier.fillMaxSize()) {
                LogRegScreenUi(
                    state = LogRegScreenState.Registration(),
                    proceedIntent = {}
                )
            }
        }
    }
}