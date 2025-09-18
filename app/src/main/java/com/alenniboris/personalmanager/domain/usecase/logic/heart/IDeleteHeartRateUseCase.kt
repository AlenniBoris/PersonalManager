package com.alenniboris.personalmanager.domain.usecase.logic.heart

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain

interface IDeleteHeartRateUseCase {

    suspend fun invoke(
        heartRate: HeartRateModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>
}