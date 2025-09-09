package com.alenniboris.personalmanager.presentation.screens.health_screen

import com.alenniboris.personalmanager.R

enum class HealthScreenOption {
    Overview,
    Weight,
    Activity,
    Nutrition
}

fun HealthScreenOption.toUiString(): Int =
    when (this) {
        HealthScreenOption.Overview -> R.string.overview_option
        HealthScreenOption.Weight -> R.string.weight_option
        HealthScreenOption.Activity -> R.string.activity_option
        HealthScreenOption.Nutrition -> R.string.nutrition_option
    }