package com.alenniboris.personalmanager.domain.usecase.logic.weight

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import java.util.Date

interface IGetWeightsByDateUseCase {

    suspend fun invoke(
        date: Date
    ): CustomResultModelDomain<List<WeightModelDomain>, CommonExceptionModelDomain>
}