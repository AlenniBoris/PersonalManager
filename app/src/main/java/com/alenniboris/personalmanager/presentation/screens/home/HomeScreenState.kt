package com.alenniboris.personalmanager.presentation.screens.home

import android.icu.util.Calendar
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.domain.utils.stripTime
import com.alenniboris.personalmanager.presentation.model.activity.ActivityModelUi
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeAddModel
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeModelUi
import com.alenniboris.personalmanager.presentation.model.heart.AddingHeartRate
import com.alenniboris.personalmanager.presentation.model.heart.HeartRateModelUi
import com.alenniboris.personalmanager.presentation.model.task.TaskAddingData
import com.alenniboris.personalmanager.presentation.model.task.TaskModelUi
import com.alenniboris.personalmanager.presentation.model.user.UserModelUi
import com.alenniboris.personalmanager.presentation.model.weather.CurrentWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.weight.AddingWeight
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.screens.personal.PersonalScreenState.AddingActivity

data class HomeScreenState(
    val isRefreshing: Boolean = false,
    val user: UserModelUi = UserModelUi(),
    val isSettingsVisible: Boolean = false,
    val quickActions: List<HomeScreenQuickActions> = HomeScreenQuickActions.entries.toList(),
    val isTemperatureLoading: Boolean = false,
    val currentWeatherForecast: CurrentWeatherForecastModelUi = CurrentWeatherForecastModelUi(),
    val isTasksListLoading: Boolean = false,
    val tasksList: List<TaskModelUi> = emptyList(),
    val isAddTaskDialogVisible: Boolean = false,
    val isTaskUploading: Boolean = false,
    val addTaskData: TaskAddingData = TaskAddingData(),
    val isDatePickerVisible: Boolean = false,
    val isTimePickerVisible: Boolean = false,
    val isHeartRatesLoading: Boolean = false,
    val lastHeartRate: HeartRateModelUi? = null,
    val isHeartRateAddDialogVisible: Boolean = false,
    val addingHeartRate: AddingHeartRate = AddingHeartRate(userId = user.id),
    val isHeartRateUploading: Boolean = false,
    val isWeightsLoading: Boolean = false,
    val lastWeight: WeightModelUi? = null,
    val isWeightAddDialogVisible: Boolean = false,
    val addingWeight: AddingWeight = AddingWeight(userId = user.id),
    val isWeightUploading: Boolean = false,
    val isActivityAddDialogVisible: Boolean = false,
    val activities: List<ActivityModelUi> = emptyList(),
    val isActivitiesLoading: Boolean = false,
    val addingActivity: AddingActivity = AddingActivity(userId = user.id),
    val isActivityUploading: Boolean = false,
    val isFoodDataLoading: Boolean = false,
    val listOfConsumedFood: List<FoodIntakeModelUi> = emptyList(),
    val isFoodIntakeAddDialogVisible: Boolean = false,
    val isFoodIntakeAddProceeding: Boolean = false,
    val foodIntakeAddModel: FoodIntakeAddModel = FoodIntakeAddModel()
) {
    val numberOfTodayTasks: Int =
        tasksList.filter { it.domainModel.dueDate == Calendar.getInstance().time.stripTime() }.size
    val numberOfTodayCompletedTasks: Int =
        tasksList.filter {
            (it.domainModel.dueDate == Calendar.getInstance().time.stripTime())
                    && it.domainModel.status == TaskStatus.Completed
        }.size
    val tasksStatisticsText: String = "$numberOfTodayCompletedTasks/$numberOfTodayTasks"
    val tasksPercentage: Double = if (numberOfTodayTasks != 0)
        (numberOfTodayCompletedTasks / numberOfTodayTasks).toDouble()
    else 0.0

    val totalConsumedCalories: Int = listOfConsumedFood.sumOf { it.caloriesValue }
    val caloriesStatisticsText: String = "$totalConsumedCalories/${user.caloriesIntake}"
    val caloriesPercentage: Double = if (user.caloriesIntake != 0.0)
        totalConsumedCalories.toDouble() / user.caloriesIntake
    else 0.0

    val listOfRecentActivities =
        if (activities.size <= HomeScreenValues.NUMBER_OF_ACTIVITIES) activities
        else activities.subList(0, HomeScreenValues.NUMBER_OF_ACTIVITIES)
}
