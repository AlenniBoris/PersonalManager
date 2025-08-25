package com.alenniboris.personalmanager.presentation.mapper

import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain

fun CommonExceptionModelDomain.toUiString(): Int = when (this) {
    CommonExceptionModelDomain.ErrorGettingData -> R.string.getting_data_exception
    CommonExceptionModelDomain.InternetException -> R.string.internet_exception
    CommonExceptionModelDomain.NoSuchUser -> R.string.no_user_exception
    CommonExceptionModelDomain.NotAllFieldsFilled -> R.string.not_filled_fields_exception
    CommonExceptionModelDomain.PasswordAndCheckNotMatch -> R.string.password_check_exception
    CommonExceptionModelDomain.UnknownException -> R.string.unknown_exception
    CommonExceptionModelDomain.UserAlreadyExists -> R.string.existing_user_exception
    CommonExceptionModelDomain.WrongPassword -> R.string.wrong_password_exception
    CommonExceptionModelDomain.EmailIsWrongType -> R.string.email_wrong_type_exception
    CommonExceptionModelDomain.WeakPassword -> R.string.weak_password_exception
}