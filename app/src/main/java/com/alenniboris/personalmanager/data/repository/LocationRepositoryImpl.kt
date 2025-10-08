package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.mapper.toCommonException
import com.alenniboris.personalmanager.data.mapper.toLocationString
import com.alenniboris.personalmanager.data.source.remote.service.location.ILocationGeoapifyApiService
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.repository.ILocationRepository
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationGeoapifyApiService: ILocationGeoapifyApiService,
    private val dispatchers: IAppDispatchers
) : ILocationRepository {

    override suspend fun getCurrentLocation(
        lat: Double,
        lon: Double
    ): CustomResultModelDomain<String, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext runCatching {

                val response = locationGeoapifyApiService.getCurrentLocationOfPhone(
                    lat = lat.toString(),
                    lon = lon.toString()
                )

                return@withContext CustomResultModelDomain.Success(
                    response.toLocationString()
                )
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!",
                    message = """
                        LocationRepositoryImpl, getCurrentLocation
                        
                        ${exception.stackTraceToString()}
                    """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }
}