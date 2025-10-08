package com.alenniboris.personalmanager.presentation.screens.home

import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.presentation.screens.health_screen.IHealthScreenIntent
import java.util.Date

sealed interface IHomeScreenIntent {
    data object ChangeSettingsDialogVisibility : IHomeScreenIntent
    data object OpenPersonalScreen : IHomeScreenIntent
    data object RefreshData : IHomeScreenIntent
    data object ChangeTaskAddDialogVisibility : IHomeScreenIntent
    data object AddNewTask : IHomeScreenIntent
    data class UpdateAddTaskTitle(val newValue: String) : IHomeScreenIntent
    data class UpdateAddTaskDescription(val newValue: String) : IHomeScreenIntent
    data class UpdateAddTaskPriority(val newValue: TaskPriority) : IHomeScreenIntent
    data class UpdateAddTaskDate(val newValue: Date) : IHomeScreenIntent
    data class UpdateAddTaskTime(val newValue: Date) : IHomeScreenIntent
    data object UpdateDatePickerVisibility : IHomeScreenIntent
    data object UpdateTimePickerVisibility : IHomeScreenIntent
    data object AddWeight : IHomeScreenIntent
    data class UpdateWeightAddModelWeight(val value: String) : IHomeScreenIntent
    data object UpdateWeightsAddDialogVisibility : IHomeScreenIntent
    data object AddHeartRate : IHomeScreenIntent
    data class UpdateHeartRateAddModelValue(val value: String) : IHomeScreenIntent
    data object UpdateHeartRatesAddDialogVisibility : IHomeScreenIntent
    data object UpdateFoodIntakeAddDialogVisibility : IHomeScreenIntent
    data class UpdateFoodIntakeAddModelTitle(val newValue: String) : IHomeScreenIntent
    data class UpdateFoodIntakeAddModelProteins(val newValue: String) : IHomeScreenIntent
    data class UpdateFoodIntakeAddModelFats(val newValue: String) : IHomeScreenIntent
    data class UpdateFoodIntakeAddModelCarbs(val newValue: String) : IHomeScreenIntent
    data class UpdateFoodIntakeAddModelCalories(val newValue: String) : IHomeScreenIntent
    data object ProceedFoodIntakeAdd : IHomeScreenIntent
    data class ProceedQuickAction(val action: HomeScreenQuickActions) : IHomeScreenIntent
}