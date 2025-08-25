package com.alenniboris.personalmanager.domain.model.weather

import java.util.Date

data class CurrentWeatherForecastModelDomain(
    val temperature: Int,
    val windSpeed: Int,
    val windDirection: WindDirection,
    val visibilityDistance: Int,
    val sunsetTime: Date,
    val sunriseTime: Date,
    val uvIndex: Int,
    val relativeHumidity: Int,
    val precipitation: Int,
    val precipitationProbability: Int,
    val place: String
)
