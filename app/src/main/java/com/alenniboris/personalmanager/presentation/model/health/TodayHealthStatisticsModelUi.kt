package com.alenniboris.personalmanager.presentation.model.health

import com.alenniboris.personalmanager.domain.model.TodayHealthStatisticsModelDomain
import kotlin.math.roundToInt


data class TodayHealthStatisticsModelUi(
    private val domainModel: TodayHealthStatisticsModelDomain? = null
) {

    val currentWeightText: String =
        domainModel?.currentWeight.toString()

    val heartRateText: String =
        (domainModel?.averageHeartRate?.roundToInt() ?: 0).toString()

    val consumedCalories: Double = domainModel?.consumedCalories ?: 0.0
    val caloriesIntake: Double = domainModel?.caloriesIntake ?: 0.0
    val caloriesText: String = consumedCalories.roundToInt().toString()
    val consumedCaloriesPercent: Double = consumedCalories / caloriesIntake
    val caloriesStatisticsText: String =
        "${consumedCalories.roundToInt()}/${caloriesIntake.roundToInt()}"

    val consumedProteins: Double = domainModel?.consumedProteins ?: 0.0
    val proteinsIntake: Double = domainModel?.proteinsIntake ?: 0.0
    val consumedProteinsPercent: Double = consumedProteins / proteinsIntake
    val proteinsStatisticsText: String =
        "${consumedProteins}/${proteinsIntake.roundToInt()}"

    val consumedFats: Double = domainModel?.consumedFats ?: 0.0
    val fatsIntake: Double = domainModel?.fatsIntake ?: 0.0
    val consumedFatsPercent: Double = consumedFats / fatsIntake
    val fatsStatisticsText: String =
        "${consumedFats}/${fatsIntake.roundToInt()}"

    val consumedCarbs: Double = domainModel?.consumedCarbohydrates ?: 0.0
    val carbsIntake: Double = domainModel?.carbohydratesIntake ?: 0.0
    val consumedCarbsPercent: Double = consumedCarbs / carbsIntake
    val carbohydratesStatisticsText: String =
        "${consumedCarbs}/${carbsIntake.roundToInt()}"
}

fun TodayHealthStatisticsModelDomain.toModelUi(): TodayHealthStatisticsModelUi =
    TodayHealthStatisticsModelUi(
        domainModel = this
    )