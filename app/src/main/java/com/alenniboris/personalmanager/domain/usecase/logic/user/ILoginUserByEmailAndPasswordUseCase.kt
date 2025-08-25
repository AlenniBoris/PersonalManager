package com.alenniboris.personalmanager.domain.usecase.logic.user

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain

interface ILoginUserByEmailAndPasswordUseCase {

    suspend fun invoke(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}