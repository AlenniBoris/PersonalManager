package com.alenniboris.personalmanager.domain.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain

interface ILocationRepository {

    suspend fun getCurrentLocation(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<String, CommonExceptionModelDomain>
}