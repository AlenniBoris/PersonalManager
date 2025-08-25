package com.alenniboris.personalmanager.domain.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.weather.CurrentWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.DayWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain

interface IWeatherRepository {

    suspend fun getCurrentWeatherForecast(
        lat: Double,
        lon: Double
    ): CustomResultModelDomain<CurrentWeatherForecastModelDomain, CommonExceptionModelDomain>

    suspend fun getTodayHourWeatherForecast(
        lat: Double,
        lon: Double
    ): CustomResultModelDomain<List<HourWeatherForecastModelDomain>, CommonExceptionModelDomain>

    suspend fun getDayWeatherForecastForWeek(
        lat: Double,
        lon: Double
    ): CustomResultModelDomain<List<DayWeatherForecastModelDomain>, CommonExceptionModelDomain>
}