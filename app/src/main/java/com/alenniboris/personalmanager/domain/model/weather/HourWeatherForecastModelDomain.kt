package com.alenniboris.personalmanager.domain.model.weather

import java.util.Date

data class HourWeatherForecastModelDomain(
    val windSpeed: Int,
    val temperature: String,
    val precipitationProbability: Int,
    val precipitation: Int,
    val feltTemperature: Int,
    val isDayLight: Boolean,
    val uvIndex: Int,
    val relativeHumidity: Int,
    val windDirection: WindDirection,
    val hourTime: Date
)
