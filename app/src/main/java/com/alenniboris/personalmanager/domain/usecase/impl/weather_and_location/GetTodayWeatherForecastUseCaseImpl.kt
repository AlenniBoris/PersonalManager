package com.alenniboris.personalmanager.domain.usecase.impl.weather_and_location

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.repository.IWeatherRepository
import com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location.IGetTodayWeatherForecastUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTodayWeatherForecastUseCaseImpl @Inject constructor(
    private val weatherRepository: IWeatherRepository,
    private val dispatchers: IAppDispatchers
) : IGetTodayWeatherForecastUseCase {

    override suspend fun invoke(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<List<HourWeatherForecastModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext weatherRepository.getTodayHourWeatherForecast(
                lat = lat,
                lon = lon
            )
        }
}