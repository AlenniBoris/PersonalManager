package com.alenniboris.personalmanager.domain.usecase.logic

import com.alenniboris.personalmanager.domain.model.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.UserModelDomain

interface IRegisterUserUseCase {

    suspend fun invoke(
        user: UserModelDomain,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}