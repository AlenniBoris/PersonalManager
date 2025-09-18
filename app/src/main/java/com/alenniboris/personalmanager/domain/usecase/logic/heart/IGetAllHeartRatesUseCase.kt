package com.alenniboris.personalmanager.domain.usecase.logic.heart

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain

interface IGetAllHeartRatesUseCase {

    suspend fun invoke(): CustomResultModelDomain<List<HeartRateModelDomain>, CommonExceptionModelDomain>
}