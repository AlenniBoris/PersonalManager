package com.alenniboris.personalmanager.domain.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import java.util.Date

interface IWeightRepository {

    suspend fun getWeightsListByDateRange(
        startDate: Date,
        endDate: Date,
        userId: String
    ): CustomResultModelDomain<List<WeightModelDomain>, CommonExceptionModelDomain>

    suspend fun addWeight(
        weight: WeightModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun deleteWeight(
        weight: WeightModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun getAllWeights(
        userId: String
    ): CustomResultModelDomain<List<WeightModelDomain>, CommonExceptionModelDomain>
}