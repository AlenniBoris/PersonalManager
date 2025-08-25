package com.alenniboris.personalmanager.data.mapper

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.toCommonException(): CommonExceptionModelDomain = when (this) {
    is CommonExceptionModelDomain -> this
    is UnknownHostException, is ConnectException -> CommonExceptionModelDomain.InternetException
    else -> CommonExceptionModelDomain.UnknownException
}