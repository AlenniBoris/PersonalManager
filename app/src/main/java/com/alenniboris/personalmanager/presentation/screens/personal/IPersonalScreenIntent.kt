package com.alenniboris.personalmanager.presentation.screens.personal

import com.alenniboris.personalmanager.domain.model.user.FitnessGoal
import com.alenniboris.personalmanager.domain.model.user.UserGender
import com.alenniboris.personalmanager.presentation.model.activity.ActivityModelUi
import com.alenniboris.personalmanager.presentation.model.heart.HeartRateModelUi
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.screens.tasks.ITasksScreenIntent
import java.util.Date

sealed interface IPersonalScreenIntent {
    data object NavigateBack : IPersonalScreenIntent
    data object ExitApp : IPersonalScreenIntent
    data class ChangeOption(val option: PersonalScreenOption) : IPersonalScreenIntent
    data object ChangeUserUpdateDialogVisibility : IPersonalScreenIntent
    data object UpdateUser : IPersonalScreenIntent
    data class ChangeUserUpdateModelName(val newValue: String) : IPersonalScreenIntent
    data class ChangeUserUpdateModelPhone(val newValue: String) : IPersonalScreenIntent
    data class ChangeUserUpdateModelAge(val newValue: String) : IPersonalScreenIntent
    data class ChangeUserUpdateModelHeight(val newValue: String) : IPersonalScreenIntent
    data class ChangeUserUpdateModelWeight(val newValue: String) : IPersonalScreenIntent
    data class ChangeUserUpdateModelAddress(val newValue: String) : IPersonalScreenIntent
    data class ChangeUserUpdateModelFitnessGoal(val newValue: FitnessGoal) : IPersonalScreenIntent
    data class ChangeUserUpdateModelGender(val newValue: UserGender) : IPersonalScreenIntent
    data object ChangeSettingsDialogVisibility: IPersonalScreenIntent


    data class UpdateWeightsSelectedDate(val date: Date?) : IPersonalScreenIntent
    data object UpdateWeightsDatePickerVisibility : IPersonalScreenIntent
    data object UpdateWeightsAddDialogVisibility : IPersonalScreenIntent
    data object AddWeight : IPersonalScreenIntent
    data class UpdateWeightAddModelWeight(val value: String) : IPersonalScreenIntent
    data class UpdateHeartRateAddModelValue(val value: String) : IPersonalScreenIntent
    data class UpdateActivityAddModelTitle(val value: String) : IPersonalScreenIntent
    data class UpdateActivityAddModelDuration(val value: String) : IPersonalScreenIntent
    data class UpdateActivityAddModelCalories(val value: String) : IPersonalScreenIntent
    data class UpdateHeartRatesSelectedDate(val date: Date?) : IPersonalScreenIntent
    data object UpdateHeartRatesDatePickerVisibility : IPersonalScreenIntent
    data object UpdateHeartRatesAddDialogVisibility : IPersonalScreenIntent
    data object AddHeartRate : IPersonalScreenIntent

    data class UpdateActivitiesSelectedDate(val date: Date?) : IPersonalScreenIntent
    data object UpdateActivitiesDatePickerVisibility : IPersonalScreenIntent
    data object UpdateActivitiesAddDialogVisibility : IPersonalScreenIntent
    data object AddActivity : IPersonalScreenIntent
    data class DeleteWeight(val selected: WeightModelUi) : IPersonalScreenIntent
    data class DeleteActivity(val selected: ActivityModelUi) : IPersonalScreenIntent
    data class DeleteHeartRate(val selected: HeartRateModelUi) : IPersonalScreenIntent
}