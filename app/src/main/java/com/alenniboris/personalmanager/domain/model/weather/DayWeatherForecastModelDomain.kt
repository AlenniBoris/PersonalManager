package com.alenniboris.personalmanager.domain.model.weather

import java.util.Date

data class DayWeatherForecastModelDomain(
    val dayDate: Date,
    val temperatureMin: Int,
    val temperatureMax: Int,
    val feltTemperatureMin: Int,
    val feltTemperatureMax: Int,
    val relativeHumidity: Int,
    val uvIndex: Int,
    val precipitationProbability: Int,
    val precipitation: Int,
    val windSpeed: Int,
    val windDirection: WindDirection
)
