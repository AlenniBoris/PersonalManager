package com.alenniboris.personalmanager.data.model.food

import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import java.util.Date
import kotlin.String
import kotlin.text.toDouble
import kotlin.text.toLong

data class FoodIntakeModelData(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val proteins: String? = null,
    val fats: String? = null,
    val carbohydrates: String? = null,
    val markingDate: String? = null,
    val markingTime: String? = null,
    val calories: String? = null
) {

    fun toUpdatesMap(): Map<String, String?> =
        mapOf(
            "id" to id,
            "userId" to userId,
            "title" to title,
            "proteins" to proteins,
            "fats" to fats,
            "carbohydrates" to carbohydrates,
            "markingDate" to markingDate,
            "markingTime" to markingTime,
            "calories" to calories
        )
}

fun FoodIntakeModelData.toModelDomain(): FoodIntakeModelDomain? = runCatching {
    FoodIntakeModelDomain(
        id = this.id!!,
        userId = this.userId!!,
        title = this.title!!,
        proteins = this.proteins!!.toDouble(),
        fats = this.fats!!.toDouble(),
        carbohydrates = this.carbohydrates!!.toDouble(),
        markingDate = Date(this.markingDate!!.toLong()),
        markingTime = Date(this.markingTime!!.toLong()),
        calories = this.calories!!.toDouble()
    )
}.getOrElse { exception ->
    LogPrinter.printLog(
        tag = "!!!",
        message = """
            FoodIntakeModelData.toModelDomain, 
            
            ${exception.stackTraceToString()}
        """.trimIndent()
    )
    null
}

fun FoodIntakeModelDomain.toModelData(): FoodIntakeModelData =
    FoodIntakeModelData(
        id = this.id,
        userId = this.userId,
        title = this.title,
        proteins = this.proteins.toString(),
        fats = this.fats.toString(),
        carbohydrates = this.carbohydrates.toString(),
        markingDate = this.markingDate.time.toString(),
        markingTime = this.markingTime.time.toString(),
        calories = this.calories.toString()
    )