package com.alenniboris.personalmanager.domain.model.food

import java.util.Date

data class FoodIntakeModelDomain(
    val id: String,
    val userId: String,
    val title: String,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val markingDate: Date,
    val markingTime: Date,
    val calories: Double
)
