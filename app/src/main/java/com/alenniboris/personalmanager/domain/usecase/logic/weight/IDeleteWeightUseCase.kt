package com.alenniboris.personalmanager.domain.usecase.logic.weight

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain

interface IDeleteWeightUseCase {

    suspend fun invoke(
        weight: WeightModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}