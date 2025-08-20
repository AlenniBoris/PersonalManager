package com.alenniboris.personalmanager.domain.usecase.logic

import com.alenniboris.personalmanager.domain.model.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.CustomResultModelDomain

interface ISignOutUseCase {

    suspend fun invoke(): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}