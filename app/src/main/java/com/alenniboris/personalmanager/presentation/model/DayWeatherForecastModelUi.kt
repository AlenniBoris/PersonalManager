package com.alenniboris.personalmanager.presentation.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.weather.DayWeatherForecastModelDomain
import com.alenniboris.personalmanager.presentation.mapper.toDayType
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.uikit.values.toUiString
import java.text.SimpleDateFormat
import java.util.Locale

data class DayWeatherForecastModelUi(
    private val domainModel: DayWeatherForecastModelDomain? = null
) {

    val timeText = domainModel?.dayDate?.let {
        SimpleDateFormat(
            "dd.MM", Locale.getDefault()
        ).format(it)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val dayName = domainModel?.dayDate?.toDayType()?.toUiString() ?: R.string.no_text_placeholder
    val temperatureMinText = domainModel?.temperatureMin?.toInt()?.let { "$it°" }
    val temperatureMaxText = domainModel?.temperatureMax?.toInt()?.let { "$it°" }
    val weatherPictureId = domainModel?.let {
        if (it.precipitationProbability > 0) R.drawable.raining_weather_icon
        else R.drawable.sunny_weather_icon
    } ?: R.drawable.cloudy_weather_icon
    val precipitationProbabilityText = domainModel?.precipitationProbability?.let { "$it%" }
    val precipitationText = domainModel?.precipitation?.let { "$it mm" } ?: "0 mm"
    val feltTemperatureMinText = domainModel?.feltTemperatureMin?.toInt()?.let { "$it°" }
    val feltTemperatureMaxText = domainModel?.feltTemperatureMax?.toInt()?.let { "$it°" }
    val uvIndexText = domainModel?.uvIndex.toString()
    val humidityText = domainModel?.relativeHumidity?.let { "$it%" }
    val windDirectionTextId = domainModel?.windDirection?.toUiString()
    val windSpeedText = domainModel?.windSpeed?.let { "$it m/s" }
}

fun DayWeatherForecastModelDomain.toModelUi(): DayWeatherForecastModelUi =
    DayWeatherForecastModelUi(
        domainModel = this
    )