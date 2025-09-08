package com.alenniboris.personalmanager.domain.model

data class TodayHealthStatistics(
    val currentWeight: Double,
    val averageHeartRate: Double,
    val consumedCalories: Double,
    val caloriesIntake: Double,
    val consumedProteins: Double,
    val proteinsIntake: Double,
    val consumedFats: Double,
    val fatsIntake: Double,
    val consumedCarbohydrates: Double,
    val carbohydratesIntake: Double
)