package com.alenniboris.personalmanager.presentation.mapper

import androidx.compose.ui.graphics.Color
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.weightChangeGrowthColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weightChangeLossColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weightChangeNoGrowthColor

object WeightMapper {

    fun getWeightChangeIcon(weightChange: Double): Int =
        if (weightChange > 0) R.drawable.growing_up_icon
        else if (weightChange < 0) R.drawable.growing_down_icon
        else R.drawable.no_growing_icon

    fun getWeightChangeColor(weightChange: Double): Color =
        if (weightChange > 0) weightChangeGrowthColor
        else if (weightChange < 0) weightChangeLossColor
        else weightChangeNoGrowthColor
}