package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.repository.IHeartRepository
import java.util.Date

class HeartRepositoryImpl : IHeartRepository {

    override suspend fun getListOfRatesByDate(
        date: Date,
        userId: String
    ): CustomResultModelDomain<List<HeartRateModelDomain>, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}