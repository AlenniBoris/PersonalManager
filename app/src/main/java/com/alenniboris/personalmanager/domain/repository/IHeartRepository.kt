package com.alenniboris.personalmanager.domain.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import java.util.Date

interface IHeartRepository {

    suspend fun getListOfRatesByDate(
        date: Date,
        userId: String
    ): CustomResultModelDomain<List<HeartRateModelDomain>, CommonExceptionModelDomain>

    suspend fun addHeartRate(
        heartRate: HeartRateModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun deleteHeartRate(
        heartRate: HeartRateModelDomain
    ): CustomResultModelDomain<Unit, CommonExceptionModelDomain>

    suspend fun getAllHeartRates(
        userId: String
    ): CustomResultModelDomain<List<HeartRateModelDomain>, CommonExceptionModelDomain>
}