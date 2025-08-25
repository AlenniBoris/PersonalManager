package com.alenniboris.personalmanager.domain.usecase.logic.user

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.user.UserModelDomain

interface IRegisterUserUseCase {

    suspend fun invoke(
        user: UserModelDomain,
        password: String,
        passwordCheck: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}