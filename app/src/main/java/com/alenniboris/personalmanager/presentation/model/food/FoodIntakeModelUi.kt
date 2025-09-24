package com.alenniboris.personalmanager.presentation.model.food

import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

data class FoodIntakeModelUi(
    val domainModel: FoodIntakeModelDomain
) {

    val title: String = domainModel.title
    val caloriesValue: Int = domainModel.calories.roundToInt()
    val caloriesText: String = caloriesValue.toString()
    val proteinsValue = domainModel.proteins
    val proteinsText = "${proteinsValue}g"
    val proteinsAsString = proteinsValue.toString()
    val fatsValue = domainModel.fats
    val fatsText = "${fatsValue}g"
    val fatsAsString = fatsValue.toString()
    val carbsValue = domainModel.carbohydrates
    val carbsText = "${carbsValue}g"
    val carbsAsString = carbsValue.toString()

    val dateText: String =
        SimpleDateFormat(
            ScreensCommonUtils.COMBINED_DATE_PATTERN,
            Locale.getDefault()
        ).format(domainModel.markingTime)
}

fun FoodIntakeModelDomain.toModelUi(): FoodIntakeModelUi =
    FoodIntakeModelUi(
        domainModel = this
    )