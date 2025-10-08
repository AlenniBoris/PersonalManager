package com.alenniboris.personalmanager.presentation.model.food

import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.domain.utils.stripTime
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import java.util.Calendar
import java.util.Date

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