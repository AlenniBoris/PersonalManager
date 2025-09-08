package com.alenniboris.personalmanager.domain.usecase.logic.heart

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import java.util.Date

interface IGetHeartRatesByDateUseCase {

    suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<List<HeartRateModelDomain>, CommonExceptionModelDomain>
}