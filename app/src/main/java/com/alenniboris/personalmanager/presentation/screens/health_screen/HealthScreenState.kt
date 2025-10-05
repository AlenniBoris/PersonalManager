package com.alenniboris.personalmanager.presentation.screens.health_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.domain.utils.stripTime
import com.alenniboris.personalmanager.presentation.mapper.WeightMapper
import com.alenniboris.personalmanager.presentation.mapper.toDayType
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeModelUi
import com.alenniboris.personalmanager.presentation.model.health.TodayHealthStatisticsModelUi
import com.alenniboris.personalmanager.presentation.model.heart.HeartRateModelUi
import com.alenniboris.personalmanager.presentation.model.user.UserModelUi
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import com.alenniboris.personalmanager.presentation.uikit.values.toUiString
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class HealthScreenState(
    val screenOption: HealthScreenOption = HealthScreenOption.Overview,
    val listOfScreenOption: List<HealthScreenOption> = HealthScreenOption.entries.toList(),
    val user: UserModelUi? = null,
    val isTodayStatisticsLoading: Boolean = false,
    val todayHealthStatistics: TodayHealthStatisticsModelUi = TodayHealthStatisticsModelUi(),
    val isWeightDataLoading: Boolean = false,
    val weightChartStartDate: Date = ScreensCommonUtils.getDateWeekAgo(),
    val weightChartEndDate: Date = Calendar.getInstance().time,
    val weightChartList: List<WeightModelUi> = emptyList(),
    val isWeightChartStartDatePickerVisible: Boolean = false,
    val isWeightChartEndDatePickerVisible: Boolean = false,
    val isHeartRateDataLoading: Boolean = false,
    val heartRateChartDate: Date = Calendar.getInstance().time.stripTime(),
    val heartRateChartList: List<HeartRateModelUi> = emptyList(),
    val isHeartRateDatePickerVisible: Boolean = false,
    val isFoodDataLoading: Boolean = false,
    val foodIntakeDate: Date = Calendar.getInstance().time.stripTime(),
    val foodIntakeList: List<FoodIntakeModelUi> = emptyList(),
    val isFoodIntakeAddDialogVisible: Boolean = false,
    val isFoodIntakeAddProceeding: Boolean = false,
    val isFoodIntakeUpdateProceeding: Boolean = false,
    val foodIntakeAddModel: FoodIntakeAddModel = FoodIntakeAddModel(),
    val foodIntakeDetailsSelected: FoodIntakeModelUi? = null,
    val foodIntakeUpdateSelected: FoodIntakeModelUi? = null,
    val isFoodIntakeDatePickerVisible: Boolean = false,
    val isSettingsVisible: Boolean = false
) {

    data class FoodIntakeAddModel(
        val id: String = "",
        val userId: String = "",
        val title: String = "",
        val proteins: String = "",
        val fats: String = "",
        val carbohydrates: String = "",
        val markingDate: Date = Calendar.getInstance().time.stripTime(),
        val markingTime: Date = Calendar.getInstance().time,
        val calories: String = ""
    ) {

        fun toFoodIntakeModelDomain(): FoodIntakeModelDomain =
            FoodIntakeModelDomain(
                id = this.id,
                userId = this.userId,
                title = this.title,
                proteins = if (this.proteins.isEmpty()) ScreensCommonUtils.ZERO_DOUBLE_VALUE else this.proteins.toDouble(),
                fats = if (this.fats.isEmpty()) ScreensCommonUtils.ZERO_DOUBLE_VALUE else this.fats.toDouble(),
                carbohydrates = if (this.carbohydrates.isEmpty()) ScreensCommonUtils.ZERO_DOUBLE_VALUE else this.carbohydrates.toDouble(),
                markingDate = this.markingDate,
                markingTime = this.markingTime,
                calories = if (this.calories.isEmpty()) ScreensCommonUtils.ZERO_DOUBLE_VALUE else this.calories.toDouble()
            )
    }

    val weightChartStartDateText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(weightChartStartDate)

    @RequiresApi(Build.VERSION_CODES.O)
    val weightChartStartDateDayText: Int = weightChartStartDate.toDayType().toUiString()
    val weightChartEndDateText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(weightChartEndDate)

    @RequiresApi(Build.VERSION_CODES.O)
    val weightChartEndDateDayText: Int = weightChartEndDate.toDayType().toUiString()

    val weightChange: Double =
        (weightChartList.lastOrNull()?.weightValue
            ?: ScreensCommonUtils.ZERO_DOUBLE_VALUE) - (weightChartList.firstOrNull()?.weightValue
            ?: ScreensCommonUtils.ZERO_DOUBLE_VALUE)
    val weightChangeText: String = "$weightChange kg"
    val weightChangeIcon: Int = WeightMapper.getWeightChangeIcon(weightChange = weightChange)
    val weightChangeColor: Color = WeightMapper.getWeightChangeColor(weightChange = weightChange)
    val currentWeight: Double =
        weightChartList.lastOrNull()?.weightValue ?: ScreensCommonUtils.ZERO_DOUBLE_VALUE
    val currentWeightText: String = currentWeight.toString()

    val heartRateChartDateText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(heartRateChartDate)

    @RequiresApi(Build.VERSION_CODES.O)
    val heartRateChartDateDayText: Int = heartRateChartDate.toDayType().toUiString()

    val foodIntakeDateText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(foodIntakeDate)

    val totalCaloriesText = foodIntakeList.sumOf { it.caloriesValue }.toString()
    val totalProteinsText = foodIntakeList.sumOf { it.proteinsValue }
        .let { number -> String.format(Locale.getDefault(), "%.1f", number) }
    val totalFatsText =
        foodIntakeList.sumOf { it.fatsValue }
            .let { number -> String.format(Locale.getDefault(), "%.1f", number) }

    val totalCarbsText = foodIntakeList.sumOf { it.carbsValue }
        .let { number -> String.format(Locale.getDefault(), "%.1f", number) }
}
