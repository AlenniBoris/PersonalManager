package com.alenniboris.personalmanager.presentation.model.health

import com.alenniboris.personalmanager.domain.model.TodayHealthStatisticsModelDomain
import kotlin.math.roundToInt


data class TodayHealthStatisticsModelUi(
    private val domainModel: TodayHealthStatisticsModelDomain? = null
) {

    val currentWeightText: String =
        "${domainModel?.currentWeight} kg"

    val heartRateText: String =
        "${domainModel?.averageHeartRate?.roundToInt() ?: 0} bpm"

    val consumedCalories: Double = domainModel?.consumedCalories ?: 0.0
    val caloriesIntake: Double = domainModel?.caloriesIntake ?: 0.0
    val caloriesText: String = consumedCalories.roundToInt().toString()
    val consumedCaloriesProcent: Double = consumedCalories / caloriesIntake
    val caloriesStatisticsText: String =
        "${consumedCalories.roundToInt()}/${caloriesIntake.roundToInt()}"

    val consumedProteins: Double = domainModel?.consumedProteins ?: 0.0
    val proteinsIntake: Double = domainModel?.proteinsIntake ?: 0.0
    val consumedProteinsProcent: Double = consumedProteins / proteinsIntake
    val proteinsStatisticsText: String =
        "${consumedProteins}g/${proteinsIntake.roundToInt()}g"

    val consumedFats: Double = domainModel?.consumedFats ?: 0.0
    val fatsIntake: Double = domainModel?.fatsIntake ?: 0.0
    val consumedFatsProcent: Double = consumedFats / fatsIntake
    val fatsStatisticsText: String =
        "${consumedFats}g/${fatsIntake.roundToInt()}g"

    val consumedCarbs: Double = domainModel?.consumedCarbohydrates ?: 0.0
    val carbsIntake: Double = domainModel?.carbohydratesIntake ?: 0.0
    val consumedCarbsProcent: Double = consumedCarbs / carbsIntake
    val carbohydratesStatisticsText: String =
        "${consumedCarbs}/${carbsIntake.roundToInt()}g"
}

fun TodayHealthStatisticsModelDomain.toModelUi(): TodayHealthStatisticsModelUi =
    TodayHealthStatisticsModelUi(
        domainModel = this
    )