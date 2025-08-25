package com.alenniboris.personalmanager.domain.model.weather

data class CurrentWeatherForecastModelDomain(
    val temperature: Double,
    val windSpeed: Double,
    val windDirection: WindDirection,
    val visibilityDistance: Double,
    val sunsetTime: String,
    val sunriseTime: String,
    val uvIndex: Double,
    val relativeHumidity: Double,
    val precipitation: Double,
    val precipitationProbability: Double,
    val place: String
)
