package com.alenniboris.personalmanager.presentation.model

import com.alenniboris.personalmanager.domain.model.user.FitnessGoal
import com.alenniboris.personalmanager.domain.model.user.UserModelDomain

data class UserModelUi(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val age: Int = 0,
    val phoneNumber: String = "",
    val height: Double = 0.0,
    val address: String = "",
    val weight: Double = 0.0,
    val caloriesIntake: Double = 0.0,
    val fitnessGoal: FitnessGoal = FitnessGoal.Support,
    val neededProteins: Double = 0.0,
    val neededFats: Double = 0.0,
    val neededCarbohydrates: Double = 0.0
)

fun UserModelUi.toDomainModel() =
    UserModelDomain(
        id = this.id,
        name = this.name,
        email = this.email,
        age = this.age,
        phoneNumber = this.phoneNumber,
        height = this.height,
        address = this.address,
        weight = this.weight,
        caloriesIntake = this.caloriesIntake,
        fitnessGoal = this.fitnessGoal,
        neededProteins = this.neededProteins,
        neededFats = this.neededFats,
        neededCarbohydrates = this.neededCarbohydrates
    )

fun UserModelDomain.toModelUi() =
    UserModelUi(
        id = this.id,
        name = this.name,
        email = this.email,
        age = this.age,
        phoneNumber = this.phoneNumber,
        height = this.height,
        address = this.address
    )