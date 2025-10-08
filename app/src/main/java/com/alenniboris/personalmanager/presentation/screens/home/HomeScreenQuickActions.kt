package com.alenniboris.personalmanager.presentation.screens.home

import com.alenniboris.personalmanager.R

enum class HomeScreenQuickActions {
    AddTask,
    LogFood,
    WeightCheck,
    AddHeartRate
}

fun HomeScreenQuickActions.toUiString(): Int = when (this) {
    HomeScreenQuickActions.AddTask -> R.string.add_task_action
    HomeScreenQuickActions.LogFood -> R.string.log_food_action
    HomeScreenQuickActions.WeightCheck -> R.string.weight_check_action
    HomeScreenQuickActions.AddHeartRate -> R.string.add_heart_rate_action
}

fun HomeScreenQuickActions.toUiPicture(): Int = when (this) {
    HomeScreenQuickActions.AddTask -> R.drawable.tasks_button_icon
    HomeScreenQuickActions.LogFood -> R.drawable.log_food_icon
    HomeScreenQuickActions.WeightCheck -> R.drawable.weight_scale
    HomeScreenQuickActions.AddHeartRate -> R.drawable.heart_rate_icon
}