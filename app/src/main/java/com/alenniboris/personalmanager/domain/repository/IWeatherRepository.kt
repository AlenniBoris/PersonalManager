package com.alenniboris.personalmanager.domain.repository

import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.weather.CurrentWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.DayWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain

interface IWeatherRepository {

    suspend fun getCurrentWeatherForecast(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<CurrentWeatherForecastModelDomain, CommonExceptionModelDomain>

    suspend fun getTodayHourWeatherForecast(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<List<HourWeatherForecastModelDomain>, CommonExceptionModelDomain>

    suspend fun getDayWeatherForecastForWeek(
        lat: Long,
        lon: Long
    ): CustomResultModelDomain<List<DayWeatherForecastModelDomain>, CommonExceptionModelDomain>
}