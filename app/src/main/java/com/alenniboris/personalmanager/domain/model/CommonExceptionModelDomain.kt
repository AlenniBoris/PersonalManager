package com.alenniboris.personalmanager.domain.model

sealed class CommonExceptionModelDomain {

    data object InternetException : CommonExceptionModelDomain()

    data object UnknownException : CommonExceptionModelDomain()

    data object NotAllFieldsFilled : CommonExceptionModelDomain()

    data object PasswordAndCheckNotMatch : CommonExceptionModelDomain()

    data object WrongPassword : CommonExceptionModelDomain()

    data object NoSuchUser : CommonExceptionModelDomain()
}