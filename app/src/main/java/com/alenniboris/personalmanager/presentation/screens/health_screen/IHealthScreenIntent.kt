package com.alenniboris.personalmanager.presentation.screens.health_screen

import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeModelUi
import java.util.Date

sealed interface IHealthScreenIntent {
    data object OpenPersonalScreen: IHealthScreenIntent
    data class ChangeScreenOption(val option: HealthScreenOption) : IHealthScreenIntent
    data class UpdateWeightChartStartDate(val date: Date) : IHealthScreenIntent
    data object UpdateWeightChartStartDateToDefault : IHealthScreenIntent
    data class UpdateWeightChartEndDate(val date: Date) : IHealthScreenIntent
    data object UpdateWeightChartEndDateToDefault : IHealthScreenIntent
    data class UpdateHeartRateChartDate(val date: Date) : IHealthScreenIntent
    data object UpdateHeartRateChartDateToDefault : IHealthScreenIntent
    data object UpdateWeightChartStartDatePickerVisibility : IHealthScreenIntent
    data object UpdateWeightChartEndDatePickerVisibility : IHealthScreenIntent
    data object UpdateHeartRateDatePickerVisibility : IHealthScreenIntent
    data object UpdateFoodIntakeDatePickerVisibility : IHealthScreenIntent

    data class UpdateFoodIntakeFilterDate(val date: Date) : IHealthScreenIntent
    data object UpdateFoodIntakeFilterDateToDefault : IHealthScreenIntent
    data class UpdatedFoodIntakeDetailsSelected(val foodIntake: FoodIntakeModelUi?) :
        IHealthScreenIntent

    data class UpdatedFoodIntakeUpdateSelected(val foodIntake: FoodIntakeModelUi?) :
        IHealthScreenIntent

    data object UpdateFoodIntakeAddDialogVisibility : IHealthScreenIntent
    data class UpdateFoodIntakeAddModelTitle(val newValue: String) : IHealthScreenIntent
    data class UpdateFoodIntakeAddModelProteins(val newValue: String) : IHealthScreenIntent
    data class UpdateFoodIntakeAddModelFats(val newValue: String) : IHealthScreenIntent
    data class UpdateFoodIntakeAddModelCarbs(val newValue: String) : IHealthScreenIntent
    data class UpdateFoodIntakeAddModelCalories(val newValue: String) : IHealthScreenIntent
    data object ProceedFoodIntakeUpdate : IHealthScreenIntent
    data object ProceedFoodIntakeAdd : IHealthScreenIntent
    data class ProceedFoodIntakeDelete(val foodIntake: FoodIntakeModelUi) : IHealthScreenIntent
    data class UpdateFoodIntakeUpdateModelProteins(val newValue: String) : IHealthScreenIntent
    data class UpdateFoodIntakeUpdateModelFats(val newValue: String) : IHealthScreenIntent
    data class UpdateFoodIntakeUpdateModelCarbs(val newValue: String) : IHealthScreenIntent
    data class UpdateFoodIntakeUpdateModelCalories(val newValue: String) : IHealthScreenIntent
    data class UpdateFoodIntakeUpdateModelTitle(val newValue: String) : IHealthScreenIntent
}