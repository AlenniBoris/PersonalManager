package com.alenniboris.personalmanager.data.model.weather

import com.alenniboris.personalmanager.data.source.remote.model.weather.CloudsWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.CurrentWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.MultipleDaysWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.SunMoonWeatherForecastResponseModel
import com.alenniboris.personalmanager.domain.model.weather.CurrentWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.WindDirection
import com.alenniboris.personalmanager.domain.utils.LogPrinter

data class CurrentWeatherForecastModelData(
    val temperature: String?,
    val windSpeed: String?,
    val windDirection: String?,
    val visibilityDistance: String?,
    val sunsetTime: String?,
    val sunriseTime: String?,
    val uvIndex: String?,
    val relativeHumidity: String?,
    val precipitation: String?,
    val precipitationProbability: String?,
    val place: String?
)

fun makeCurrentWeatherForecastModelData(
    currentWeatherForecastResponseModel: CurrentWeatherForecastResponseModel,
    cloudsWeatherForecastResponseModel: CloudsWeatherForecastResponseModel,
    sunMoonWeatherForecastResponseModel: SunMoonWeatherForecastResponseModel,
    multipleDaysWeatherForecastResponseModel: MultipleDaysWeatherForecastResponseModel
): CurrentWeatherForecastModelData =
    CurrentWeatherForecastModelData(
        temperature = currentWeatherForecastResponseModel.currentData?.temperature,
        windSpeed = currentWeatherForecastResponseModel.currentData?.windSpeed
            ?: multipleDaysWeatherForecastResponseModel.daysData?.windSpeedMean?.firstOrNull(),
        windDirection = multipleDaysWeatherForecastResponseModel.daysData?.windDirection?.firstOrNull(),
        visibilityDistance = cloudsWeatherForecastResponseModel.dayData?.visibilityMean?.firstOrNull(),
        sunsetTime = sunMoonWeatherForecastResponseModel.dayData?.sunset?.firstOrNull(),
        sunriseTime = sunMoonWeatherForecastResponseModel.dayData?.sunrise?.firstOrNull(),
        uvIndex = multipleDaysWeatherForecastResponseModel.daysData?.uvIndex?.firstOrNull(),
        relativeHumidity = multipleDaysWeatherForecastResponseModel.daysData?.relativeHumidityMean?.firstOrNull(),
        precipitation = multipleDaysWeatherForecastResponseModel.daysData?.precipitation?.firstOrNull(),
        precipitationProbability = multipleDaysWeatherForecastResponseModel.daysData?.precipitationProbability?.firstOrNull(),
        place = ""
    )

fun CurrentWeatherForecastModelData.toModelDomain(): CurrentWeatherForecastModelDomain? =
    runCatching {

        CurrentWeatherForecastModelDomain(
            temperature = this.temperature?.toDouble()!!,
            windSpeed = this.windSpeed?.toDouble()!!,
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
            visibilityDistance = this.visibilityDistance?.toDouble()!!,
            sunsetTime = this.sunsetTime!!,
            sunriseTime = this.sunriseTime!!,
            uvIndex = this.uvIndex?.toDouble()!!,
            relativeHumidity = this.relativeHumidity?.toDouble()!!,
            precipitation = this.precipitation?.toDouble()!!,
            precipitationProbability = this.precipitationProbability?.toDouble()!!,
            place = this.place!!
        )
    }.getOrElse { exception ->
        LogPrinter.printLog(
            tag = "!!!",
            message = """
            CurrentWeatherForecastModelData.toModelDomain
            
            ${exception.stackTraceToString()}
        """.trimIndent()
        )
        null
    }
