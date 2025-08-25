package com.alenniboris.personalmanager.domain.model.weather

import java.util.Date

data class DayWeatherForecastModelDomain(
    val dayDate: Date,
    val temperatureMin: Double,
    val temperatureMax: Double,
    val feltTemperatureMin: Double,
    val feltTemperatureMax: Double,
    val relativeHumidity: Double,
    val uvIndex: Double,
    val precipitationProbability: Double,
    val precipitation: Double,
    val windSpeed: Double,
    val windDirection: WindDirection
)
