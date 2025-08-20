package com.alenniboris.personalmanager.presentation.model

import com.alenniboris.personalmanager.domain.model.UserModelDomain

data class UserModelUi(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val age: Int = 0,
    val phoneNumber: String = "",
    val height: Int = 0,
    val address: String = ""
)

fun UserModelUi.toDomainModel() =
    UserModelDomain(
        id = this.id,
        name = this.name,
        email = this.email,
        age = this.age,
        phoneNumber = this.phoneNumber,
        height = this.height,
        address = this.address
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