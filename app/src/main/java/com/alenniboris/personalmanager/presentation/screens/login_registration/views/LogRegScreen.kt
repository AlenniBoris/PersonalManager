package com.alenniboris.personalmanager.presentation.screens.login_registration.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.presentation.screens.login_registration.LogRegScreenViewModel
import com.alenniboris.personalmanager.presentation.uikit.values.LogRegScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph


@RootNavGraph(start = true)
@Destination(route = LogRegScreenRoute)
@Composable
fun LogRegScreen(
) {
    val viewModel: LogRegScreenViewModel = hiltViewModel()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val state by viewModel.state.collectAsStateWithLifecycle()

    LogRegScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}



