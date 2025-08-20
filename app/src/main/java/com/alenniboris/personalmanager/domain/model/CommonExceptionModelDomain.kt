package com.alenniboris.personalmanager.domain.model

import com.google.android.gms.common.internal.service.Common

sealed class CommonExceptionModelDomain : Throwable() {

    data object InternetException : CommonExceptionModelDomain()

    data object UnknownException : CommonExceptionModelDomain()

    data object NotAllFieldsFilled : CommonExceptionModelDomain()

    data object PasswordAndCheckNotMatch : CommonExceptionModelDomain()

    data object WrongPassword : CommonExceptionModelDomain()

    data object NoSuchUser : CommonExceptionModelDomain()

    data object ErrorGettingData : CommonExceptionModelDomain()

    data object UserAlreadyExists : CommonExceptionModelDomain()

    data object EmailIsWrongType : CommonExceptionModelDomain()

    data object WeakPassword: CommonExceptionModelDomain()
}