package com.alenniboris.personalmanager.presentation.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alenniboris.personalmanager.presentation.uikit.values.HomeScreenRoute
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@Composable
@RootNavGraph
@Destination(route = HomeScreenRoute)
fun HomeScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Red)
    ) { }
}