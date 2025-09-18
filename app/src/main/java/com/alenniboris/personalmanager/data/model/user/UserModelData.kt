package com.alenniboris.personalmanager.data.model.user

import com.alenniboris.personalmanager.domain.model.user.FitnessGoal
import com.alenniboris.personalmanager.domain.model.user.UserModelDomain
import com.alenniboris.personalmanager.domain.utils.LogPrinter

data class UserModelData(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val age: String? = null,
    val phone: String? = null,
    val height: String? = null,
    val address: String? = null,
    val weight: String? = null,
    val caloriesIntake: String? = null,
    val fitnessGoal: String? = null,
    val neededProteins: String? = null,
    val neededFats: String? = null,
    val neededCarbohydrates: String? = null
) {

    fun toUpdatesMap(): Map<String, String?> =
        mapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "password" to password,
            "age" to age,
            "phone" to phone,
            "height" to height,
            "address" to address,
            "weight" to weight,
            "fitnessGoal" to fitnessGoal,
            "caloriesIntake" to caloriesIntake
        )
}

fun UserModelData.toModelDomain(): UserModelDomain? =
    runCatching {

        UserModelDomain(
            id = this.id!!,
            name = this.name!!,
            email = this.email!!,
            age = this.age!!.toInt(),
            phoneNumber = this.phone!!,
            height = this.height!!.toDouble(),
            address = this.address!!,
            weight = this.weight!!.toDouble(),
            caloriesIntake = this.caloriesIntake!!.toDouble(),
            fitnessGoal = when (this.fitnessGoal!!) {
                "Make" -> FitnessGoal.Make
                "Support" -> FitnessGoal.Support
                "Lose" -> FitnessGoal.Lose
                else -> FitnessGoal.Support
            },
            neededProteins = this.neededProteins!!.toDouble(),
            neededFats = this.neededFats!!.toDouble(),
            neededCarbohydrates = this.neededCarbohydrates!!.toDouble()
        )
    }.getOrElse {
        LogPrinter.printLog(
            tag = "!!!",
            message = """
                UserModelData.toModelDomain()
                    ${it.stackTraceToString()}
            """.trimIndent()
        )
        null
    }

fun UserModelDomain.toModelData(): UserModelData =
    UserModelData(
        id = this.id,
        name = this.name,
        email = this.email,
        password = "",
        age = this.age.toString(),
        phone = this.phoneNumber,
        height = this.height.toString(),
        address = this.address,
        weight = this.weight.toString(),
        fitnessGoal = this.fitnessGoal.name,
        caloriesIntake = this.caloriesIntake.toString(),
        neededProteins = this.neededProteins.toString(),
        neededFats = this.neededFats.toString(),
        neededCarbohydrates = this.neededCarbohydrates.toString()
    )