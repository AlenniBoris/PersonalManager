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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.presentation.screens.destinations.PersonalScreenDestination
import com.alenniboris.personalmanager.presentation.screens.home.HomeScreenState
import com.alenniboris.personalmanager.presentation.screens.home.HomeScreenViewModel
import com.alenniboris.personalmanager.presentation.screens.home.IHomeScreenEvent
import com.alenniboris.personalmanager.presentation.screens.home.IHomeScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenUpcomingTaskColor
import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import com.alenniboris.personalmanager.presentation.uikit.utils.ToastUtil
import com.alenniboris.personalmanager.presentation.uikit.utils.launchForPermission
import com.alenniboris.personalmanager.presentation.uikit.utils.toPermission
import com.alenniboris.personalmanager.presentation.uikit.values.HomeScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.views.AppPermissionRationaleDialog
import com.google.android.gms.location.LocationServices
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Composable
@RootNavGraph
@Destination(route = HomeScreenRoute)
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IHomeScreenEvent.ShowToast>().collect { coming ->
                ToastUtil.show(
                    context = context,
                    resId = coming.messageId
                )
            }
        }

        launch {
            event.filterIsInstance<IHomeScreenEvent.OpenPersonalScreen>().collect {
                navigator.navigate(PersonalScreenDestination)
            }
        }
    }

    HomeScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}