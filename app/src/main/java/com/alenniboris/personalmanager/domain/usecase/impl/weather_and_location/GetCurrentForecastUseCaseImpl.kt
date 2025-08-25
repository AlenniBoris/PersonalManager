package com.alenniboris.personalmanager.domain.usecase.impl.weather_and_location

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.weather.CurrentWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.repository.ILocationRepository
import com.alenniboris.personalmanager.domain.repository.IWeatherRepository
import com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location.IGetCurrentForecastUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentForecastUseCaseImpl @Inject constructor(
    private val weatherRepository: IWeatherRepository,
    private val locationRepository: ILocationRepository,
    private val dispatchers: IAppDispatchers
) : IGetCurrentForecastUseCase {

    override suspend fun invoke(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<CurrentWeatherForecastModelDomain, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {

            val _locationResult = async {
                locationRepository.getCurrentLocation(
                    lat = lat,
                    lon = lon
                )
            }
            val _weatherResult = async {
                weatherRepository.getCurrentWeatherForecast(
                    lat = lat,
                    lon = lon
                )
            }

            val locationResult = _locationResult.await()
            val weatherResult = _weatherResult.await()

            if (locationResult is CustomResultModelDomain.Error) {
                return@withContext CustomResultModelDomain.Error(
                    locationResult.exception
                )
            }

            if (weatherResult is CustomResultModelDomain.Error) {
                return@withContext CustomResultModelDomain.Error(
                    weatherResult.exception
                )
            }

            return@withContext CustomResultModelDomain.Success(
                weatherResult.result!!.copy(
                    place = locationResult.result!!
                )
            )
        }
}