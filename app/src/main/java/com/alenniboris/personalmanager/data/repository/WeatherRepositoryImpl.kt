package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.weather.CurrentWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.DayWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.repository.IWeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor() : IWeatherRepository {

    override suspend fun getCurrentWeatherForecast(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<CurrentWeatherForecastModelDomain, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun getTodayHourWeatherForecast(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<List<HourWeatherForecastModelDomain>, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }

    override suspend fun getDayWeatherForecastForWeek(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<List<DayWeatherForecastModelDomain>, CommonExceptionModelDomain> {
        TODO("Not yet implemented")
    }
}