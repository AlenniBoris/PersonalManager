package com.alenniboris.personalmanager.presentation.screens.home.views

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.presentation.screens.home.HomeScreenState
import com.alenniboris.personalmanager.presentation.screens.home.HomeScreenViewModel
import com.alenniboris.personalmanager.presentation.screens.home.IHomeScreenEvent
import com.alenniboris.personalmanager.presentation.screens.home.IHomeScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import com.alenniboris.personalmanager.presentation.uikit.utils.launchForPermission
import com.alenniboris.personalmanager.presentation.uikit.utils.toPermission
import com.alenniboris.personalmanager.presentation.uikit.values.HomeScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.views.AppPermissionRationaleDialog
import com.google.android.gms.location.LocationServices
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Composable
@RootNavGraph
@Destination(route = HomeScreenRoute)
fun HomeScreen() {

    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IHomeScreenEvent.OpenSettings>().collect {
                val openingIntent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                context.startActivity(openingIntent)
            }
        }
    }

    HomeScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@Composable
private fun HomeScreenUi(
    state: HomeScreenState,
    proceedIntent: (IHomeScreenIntent) -> Unit
) {

    val context = LocalContext.current
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
        context
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            LogPrinter.printLog("!!!", "granted home screen")
        }
    }
    LaunchedEffect(Unit) {
        launchForPermission(
            permission = PermissionType.PERMISSION_FINE_LOCATION,
            context = context,
            onPermissionGrantedAction = {
//                proceedIntent(
//                    IHomeScreenIntent.GetUserLocation(
//                        fusedLocationProviderClient = fusedLocationProviderClient
//                    )
//                )
            },
            onPermissionNotGrantedAction = {},
            onShowRationale = { permission ->
                proceedIntent(
                    IHomeScreenIntent.UpdateRequestedPermissionAndShowDialog(
                        newRequestedPermission = permission
                    )
                )
            },
            onLaunchAgain = { permission ->
                permissionLauncher.launch(permission.toPermission())
            }
        )
    }
    if (state.isPermissionDialogVisible) {
        state.requestedPermission?.let {
            AppPermissionRationaleDialog(
                permissionType = it,
                onOpenSettings = {
                    proceedIntent(
                        IHomeScreenIntent.OpenSettingsAndHidePermissionDialog
                    )
                }
            )
        }
    }
}