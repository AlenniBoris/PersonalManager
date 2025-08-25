package com.alenniboris.personalmanager.domain.usecase.logic.user

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain

interface ISignOutUserUseCase {

    suspend fun invoke(): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}