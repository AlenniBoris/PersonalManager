package com.alenniboris.personalmanager.domain.model.weather

import java.util.Date

data class HourWeatherForecastModelDomain(
    val windSpeed: Double,
    val temperature: Double,
    val precipitationProbability: Double,
    val precipitation: Double,
    val feltTemperature: Double,
    val isDayLight: Boolean,
    val uvIndex: Double,
    val relativeHumidity: Double,
    val windDirection: WindDirection,
    val hourTime: Date
)
