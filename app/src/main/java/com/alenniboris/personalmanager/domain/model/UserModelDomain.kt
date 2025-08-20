package com.alenniboris.personalmanager.domain.model

data class UserModelDomain(
    val id: String,
    val name: String,
    val email: String
)

fun UserModelDomain.checkFieldsFilled(): Boolean =
    id.isEmpty() || name.isEmpty() || email.isEmpty()