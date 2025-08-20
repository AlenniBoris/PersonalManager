package com.alenniboris.personalmanager.domain.usecase.logic

import com.alenniboris.personalmanager.domain.model.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.CustomResultModelDomain

interface ILoginUserByEmailAndPasswordUseCase {

    suspend fun invoke(
        email: String,
        password: String
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}