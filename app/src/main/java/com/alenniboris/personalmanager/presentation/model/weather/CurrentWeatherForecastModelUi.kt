package com.alenniboris.personalmanager.presentation.model.weather

import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.weather.CurrentWeatherForecastModelDomain

data class CurrentWeatherForecastModelUi(
    private val domainModel: CurrentWeatherForecastModelDomain? = null
) {

    val locationText = domainModel?.place
    val temperatureText = domainModel?.temperature?.toInt()?.let { "$it°" }
    val humidityText = domainModel?.relativeHumidity?.let { "$it%" }
    val windSpeedText = domainModel?.windSpeed?.let { "${it.toString().subSequence(0,3)} m/s" }
    val visibilityText = domainModel?.visibilityDistance?.let { "${it.toString().subSequence(0,3)} m" }
    val uvIndexText = domainModel?.uvIndex.toString()
    val sunriseText = domainModel?.sunriseTime
    val sunsetText = domainModel?.sunsetTime
    val weatherPictureId = domainModel?.let {
        when {
            it.precipitationProbability > 0 -> R.drawable.raining_weather_icon
            else -> R.drawable.sunny_weather_icon
        }
    } ?: R.drawable.cloudy_weather_icon
}

fun CurrentWeatherForecastModelDomain.toModelUi(): CurrentWeatherForecastModelUi =
    CurrentWeatherForecastModelUi(domainModel = this)