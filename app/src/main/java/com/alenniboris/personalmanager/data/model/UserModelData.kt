package com.alenniboris.personalmanager.data.model

import android.util.Log
import com.alenniboris.personalmanager.domain.model.UserModelDomain
import kotlin.String

data class UserModelData(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val age: String? = null,
    val phone: String? = null,
    val height: String? = null,
    val address: String? = null
) {

    val hasSomeValueMissing: Boolean
        get() = id == null
                || name == null
                || email == null
                || password == null
                || age == null
                || phone == null
                || height == null
                || address == null

    fun toUpdatesMap(): Map<String, String?> =
        mapOf(
            "id" to id,
            "name" to name,
            "email" to email,
            "password" to password,
            "age" to age,
            "phone" to phone,
            "height" to height,
            "address" to address
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
            height = this.height!!.toInt(),
            address = this.address!!
        )
    }.getOrElse {
        Log.e(
            "!!!!",
            """
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
        address = this.address
    )