package com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.weather.DayWeatherForecastModelDomain

interface IGetWeekWeatherForecastUseCase {
    suspend fun invoke(
        lat: Double,
        lon: Double
    ): CustomResultModelDomain<List<DayWeatherForecastModelDomain>, CommonExceptionModelDomain>
}