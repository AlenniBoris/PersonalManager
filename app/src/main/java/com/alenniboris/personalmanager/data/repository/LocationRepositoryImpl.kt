package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.repository.ILocationRepository
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor() : ILocationRepository {

    override suspend fun getCurrentLocation(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<String, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}