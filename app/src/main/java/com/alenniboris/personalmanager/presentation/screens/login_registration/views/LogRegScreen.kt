package com.alenniboris.personalmanager.presentation.screens.login_registration.views

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.presentation.screens.login_registration.ILogRegScreenEvent
import com.alenniboris.personalmanager.presentation.screens.login_registration.LogRegScreenViewModel
import com.alenniboris.personalmanager.presentation.screens.weather.IWeatherScreenEvent
import com.alenniboris.personalmanager.presentation.uikit.utils.ToastUtil
import com.alenniboris.personalmanager.presentation.uikit.values.LogRegScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch


@RootNavGraph(start = true)
@Destination(route = LogRegScreenRoute)
@Composable
fun LogRegScreen(
) {
    val viewModel: LogRegScreenViewModel = hiltViewModel()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current

    LaunchedEffect(event) {
        launch {
            event.filterIsInstance<ILogRegScreenEvent.ShowToast>().collect { coming ->
                ToastUtil.show(
                    context = context,
                    resId = coming.messageId
                )
            }
        }

        launch {
            event.filterIsInstance<ILogRegScreenEvent.OpenSettings>().collect {
                val openingIntent =
                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                context.startActivity(openingIntent)
            }
        }
    }

    LogRegScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}