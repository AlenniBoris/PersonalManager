package com.alenniboris.personalmanager.domain.model.user

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
    this.id.isEmpty() || this.name.isEmpty() || this.email.isEmpty()

fun UserModelDomain.checkEmailType(): Boolean =
    "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex().matches(this.email)