package com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain

interface IGetTodayWeatherForecastUseCase {
    suspend fun invoke(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<List<HourWeatherForecastModelDomain>, CommonExceptionModelDomain>
}