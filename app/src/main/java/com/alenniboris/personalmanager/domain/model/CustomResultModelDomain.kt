package com.alenniboris.personalmanager.domain.model

sealed class CustomResultModelDomain<out R, out E>(
    open val result: R? = null,
    open val exception: E? = null
) {

    data class Success<out R, out E>(override val result: R) : CustomResultModelDomain<R, E>()

    data class Error<out R, out E>(override val exception: E) : CustomResultModelDomain<R, E>()
}