package com.alenniboris.personalmanager.domain.model

data class UserModelDomain(
    val id: String,
    val name: String,
    val email: String,
    val age: Int,
    val phoneNumber: String,
    val height: Int,
    val address: String
)

fun UserModelDomain.checkRegistrationFieldsFilled(): Boolean =
    id.isEmpty() || name.isEmpty() || email.isEmpty()