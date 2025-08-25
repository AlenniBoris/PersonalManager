package com.alenniboris.personalmanager.data.model.weather

import com.alenniboris.personalmanager.domain.model.weather.DayWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.WindDirection
import java.sql.Date

data class DayWeatherForecastModelData(
    val dayDate: String,
    val temperatureMin: String,
    val temperatureMax: String,
    val feltTemperatureMin: String,
    val feltTemperatureMax: String,
    val relativeHumidity: String,
    val uvIndex: String,
    val precipitationProbability: String,
    val precipitation: String,
    val windSpeed: String,
    val windDirection: String
)

fun DayWeatherForecastModelData.toModelDomain(): DayWeatherForecastModelDomain =
    DayWeatherForecastModelDomain(
        dayDate = Date(this.dayDate.toLong()),
        temperatureMin = this.temperatureMin.toDouble(),
        temperatureMax = this.temperatureMax.toDouble(),
        feltTemperatureMin = this.feltTemperatureMin.toDouble(),
        feltTemperatureMax = this.feltTemperatureMax.toDouble(),
        relativeHumidity = this.relativeHumidity.toDouble(),
        uvIndex = this.uvIndex.toDouble(),
        precipitationProbability = this.precipitationProbability.toDouble(),
        precipitation = this.precipitation.toDouble(),
        windSpeed = this.windSpeed.toDouble(),
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
        }
    )