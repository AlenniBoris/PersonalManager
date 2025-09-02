package com.alenniboris.personalmanager.presentation.screens.activity.views

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.presentation.screens.NavGraphs
import com.alenniboris.personalmanager.presentation.screens.activity.IMainActivityEvent
import com.alenniboris.personalmanager.presentation.screens.activity.IMainActivityIntent
import com.alenniboris.personalmanager.presentation.screens.activity.MainActivityViewModel
import com.alenniboris.personalmanager.presentation.screens.destinations.HomeScreenDestination
import com.alenniboris.personalmanager.presentation.screens.destinations.LogRegScreenDestination
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.bottomBarInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import com.alenniboris.personalmanager.presentation.uikit.utils.launchForPermission
import com.alenniboris.personalmanager.presentation.uikit.utils.toPermission
import com.alenniboris.personalmanager.presentation.uikit.values.BottomBarValues
import com.alenniboris.personalmanager.presentation.uikit.values.HealthScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.values.HomeScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.values.TasksScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.values.WeatherScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.values.toModelUi
import com.alenniboris.personalmanager.presentation.uikit.views.AppBottomBar
import com.alenniboris.personalmanager.presentation.uikit.views.AppPermissionRationaleDialog
import com.google.android.gms.location.LocationServices
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.rememberNavHostEngine
import com.ramcosta.composedestinations.utils.destination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PersonalManagerTheme {
                PersonalManagerUi()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
private fun PersonalManagerUi() {

    val viewModel: MainActivityViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IMainActivityEvent.OpenSettings>().collect {
                val openingIntent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                context.startActivity(openingIntent)
            }
        }
    }

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
        context
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            proceedIntent(
                IMainActivityIntent.GetUserLocation(
                    fusedLocationProviderClient = fusedLocationProviderClient
                )
            )
        }
    }
    LaunchedEffect(Unit) {
        launchForPermission(
            permission = PermissionType.PERMISSION_FINE_LOCATION,
            context = context,
            onPermissionGrantedAction = {
                proceedIntent(
                    IMainActivityIntent.GetUserLocation(
                        fusedLocationProviderClient = fusedLocationProviderClient
                    )
                )
            },
            onPermissionNotGrantedAction = {},
            onShowRationale = { permission ->
                proceedIntent(
                    IMainActivityIntent.UpdateRequestedPermissionAndShowDialog(
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
                        IMainActivityIntent.OpenSettingsAndHidePermissionDialog
                    )
                }
            )
        }
    }

    AppUi(
        isAuthenticated = state.isAuthenticated
    )
}

@Composable
private fun AppUi(
    isAuthenticated: Boolean,
) {

    val navHostEngine = rememberNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations(
            enterTransition = { fadeIn(animationSpec = tween(600)) },
            exitTransition = { fadeOut(animationSpec = tween(600)) }
        )
    )

    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination()?.baseRoute ?: ""

    LaunchedEffect(currentRoute) {
        LogPrinter.printLog("!!!", currentRoute)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isAuthenticated) {
                AppBottomBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(appColor)
                        .padding(bottomBarInnerPadding),
                    items = BottomBarValues.entries.toList().map {
                        it.toModelUi(
                            onClick = {
                                when (it) {
                                    BottomBarValues.Home -> {
                                        if (currentRoute != HomeScreenRoute) {
                                            navController.navigate(
                                                HomeScreenRoute
                                            )
                                        }
                                    }

                                    BottomBarValues.Weather -> {
                                        if (currentRoute != WeatherScreenRoute) {
                                            navController.navigate(
                                                WeatherScreenRoute
                                            )
                                        }
                                    }

                                    BottomBarValues.Tasks -> {
                                        if (currentRoute != TasksScreenRoute) {
                                            navController.navigate(
                                                TasksScreenRoute
                                            )
                                        }
                                    }

                                    BottomBarValues.Health -> {
                                        if (currentRoute != HealthScreenRoute) {
                                            LogPrinter.printLog("!!!", HealthScreenRoute)
                                        }
                                    }
                                }
                            }
                        )
                    },
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            DestinationsNavHost(
                navGraph = NavGraphs.root,
                startRoute = if (isAuthenticated) HomeScreenDestination else LogRegScreenDestination,
                engine = navHostEngine,
                navController = navController
            )
        }
    }
}