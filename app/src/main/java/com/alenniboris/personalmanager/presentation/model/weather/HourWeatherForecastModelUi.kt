package com.alenniboris.personalmanager.presentation.model.weather

import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import java.text.SimpleDateFormat
import java.util.Locale

data class HourWeatherForecastModelUi(
    private val domainModel: HourWeatherForecastModelDomain? = null
) {

    val timeText = domainModel?.hourTime?.let {
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_HOUR_PATTERN, Locale.getDefault()
        ).format(it)
    }
    val temperatureText = domainModel?.temperature?.toInt()?.let { "$it°" }
    val weatherPictureId: Int = domainModel?.let {
        if (!it.isDayLight) R.drawable.cloudy_weather_icon
        else if (it.precipitationProbability > 0) R.drawable.raining_weather_icon
        else R.drawable.sunny_weather_icon
    } ?: R.drawable.cloudy_weather_icon
    val precipitationProbabilityText =
        domainModel?.precipitationProbability?.let { if (it > 0) "$it%" else null }
    val precipitationText = domainModel?.precipitation?.let { "$it mm" }
    val feltTemperatureText = domainModel?.feltTemperature?.toInt()?.let { "$it°" }
    val uvIndexText = domainModel?.uvIndex.toString()
    val humidityText = domainModel?.relativeHumidity?.let { "$it%" }
    val windDirectionTextId = domainModel?.windDirection?.toUiString()
    val windSpeedText = domainModel?.windSpeed?.let { "$it m/s" }
}

fun HourWeatherForecastModelDomain.toModelUi(): HourWeatherForecastModelUi =
    HourWeatherForecastModelUi(
        domainModel = this
    )