package com.alenniboris.personalmanager.presentation.uikit.model

import androidx.compose.ui.graphics.Color

data class PieSegment(
    val value: Float,
    val color: Color,
    val labelId: Int? = null
)