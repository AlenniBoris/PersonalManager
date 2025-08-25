package com.alenniboris.personalmanager.data.model.weather

import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.WindDirection
import java.sql.Date

data class HourWeatherForecastModelData(
    val windSpeed: String,
    val temperature: String,
    val precipitationProbability: String,
    val precipitation: String,
    val feltTemperature: String,
    val isDayLight: String,
    val uvIndex: String,
    val relativeHumidity: String,
    val windDirection: String,
    val hourTime: String
)

fun HourWeatherForecastModelData.toModelDomain(): HourWeatherForecastModelDomain =
    HourWeatherForecastModelDomain(
        windSpeed = this.windSpeed.toDouble(),
        temperature = this.temperature.toDouble(),
        precipitationProbability = this.precipitationProbability.toDouble(),
        precipitation = this.precipitation.toDouble(),
        feltTemperature = this.feltTemperature.toDouble(),
        isDayLight = this.isDayLight.toBoolean(),
        uvIndex = this.uvIndex.toDouble(),
        relativeHumidity = this.relativeHumidity.toDouble(),
        windDirection = when (this.windDirection?.toInt()!!) {
            0 -> WindDirection.East
            in 1..89 -> WindDirection.EastNorth
            90 -> WindDirection.North
            in 91..179 -> WindDirection.NorthWest
            180 -> WindDirection.West
            in 181..269 -> WindDirection.WestSouth
            270 -> WindDirection.South
            in 271..359 -> WindDirection.SouthEast
            360 -> WindDirection.East
            else -> WindDirection.Unknown
        },
        hourTime = Date(this.hourTime.toLong())
    )



