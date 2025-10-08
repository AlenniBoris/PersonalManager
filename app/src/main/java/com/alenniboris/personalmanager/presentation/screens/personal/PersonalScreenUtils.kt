package com.alenniboris.personalmanager.presentation.screens.personal

import com.alenniboris.personalmanager.domain.model.user.FitnessGoal
import com.alenniboris.personalmanager.domain.model.user.UserGender
import com.alenniboris.personalmanager.presentation.model.food.NutritionResult
import kotlin.math.roundToInt

object PersonalScreenUtils {

    const val ACTIVITY_COEFFICIENT = 1.5
    const val DEFICIT_COEFFICIENT = 0.8
    const val PROFICIT_COEFFICIENT = 1.2
    const val MALE_PROTEIN_COEFFICIENT = 1.8
    const val FEMALE_PROTEIN_COEFFICIENT = 0.9
    const val MALE_ADD_CALORIES = 5
    const val FEMALE_ADD_CALORIES = 161
    const val PROTEIN_CARBS_CALORIES_VALUE = 4
    const val FAT_CALORIES_VALUE = 9

    fun getBaseCalorage(
        weight: Double,
        height: Double,
        age: Int
    ): Double = 10 * weight + 6.25 * height - 5 * age

    fun calculateNutrition(
        age: Int,
        height: Double,
        weight: Double,
        gender: UserGender,
        fitnessGoal: FitnessGoal,
        activity: Double = ACTIVITY_COEFFICIENT
    ): NutritionResult {

        val bmr = when (gender) {
            UserGender.Male -> getBaseCalorage(
                weight = weight,
                height = height,
                age = age
            ) + MALE_ADD_CALORIES

            UserGender.Female -> getBaseCalorage(
                weight = weight,
                height = height,
                age = age
            ) - FEMALE_ADD_CALORIES
        }

        var calories = bmr * activity

        calories = when (fitnessGoal) {
            FitnessGoal.Lose -> calories * DEFICIT_COEFFICIENT
            FitnessGoal.Support -> calories
            FitnessGoal.Make -> calories * PROFICIT_COEFFICIENT
        }

        val proteinGrams = (weight * MALE_PROTEIN_COEFFICIENT).toInt()
        val fatGrams = (weight * FEMALE_PROTEIN_COEFFICIENT).toInt()

        val proteinCalories = proteinGrams * PROTEIN_CARBS_CALORIES_VALUE
        val fatCalories = fatGrams * FAT_CALORIES_VALUE

        val carbsCalories = calories - (proteinCalories + fatCalories)
        val carbsGrams = (carbsCalories / PROTEIN_CARBS_CALORIES_VALUE).toInt()

        return NutritionResult(
            calories = calories.roundToInt().toDouble(),
            protein = proteinGrams.toDouble(),
            fat = fatGrams.toDouble(),
            carbs = carbsGrams.toDouble()
        )
    }
}