package com.alenniboris.personalmanager.data.mapper

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toCommonException(): CommonExceptionModelDomain = when (this) {
    is CommonExceptionModelDomain -> this
    is FirebaseNetworkException, is UnknownHostException, is ConnectException ->
        CommonExceptionModelDomain.InternetException

    is FirebaseAuthUserCollisionException -> CommonExceptionModelDomain.UserAlreadyExists
    is FirebaseAuthInvalidUserException -> CommonExceptionModelDomain.NoSuchUser
    is FirebaseAuthInvalidCredentialsException -> CommonExceptionModelDomain.WrongEnteredData

    else -> CommonExceptionModelDomain.UnknownException
}