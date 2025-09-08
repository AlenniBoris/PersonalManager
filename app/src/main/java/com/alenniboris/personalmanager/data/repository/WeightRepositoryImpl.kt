package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.repository.IWeightRepository
import java.util.Date

class WeightRepositoryImpl : IWeightRepository {

    override suspend fun getWeightsListByDateRange(
        startDate: Date,
        endDate: Date,
        userId: String
    ): CustomResultModelDomain<List<WeightModelDomain>, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}