package com.alenniboris.personalmanager.data.model.weather

import com.alenniboris.personalmanager.data.source.remote.model.weather.DayHourWeatherForecastResponseModel
import com.alenniboris.personalmanager.domain.utils.LogPrinter

data class DayHourWeatherForecastResponseModelData(
    val timeList: List<String>,
    val windSpeedList: List<String>,
    val temperatureList: List<String>,
    val precipitationProbabilityList: List<String>,
    val feltTemperatureList: List<String>,
    val isDayLightList: List<String>,
    val uvIndexList: List<String>,
    val relativeHumidityList: List<String>,
    val windDirectionList: List<String>,
    val precipitationList: List<String>
)

fun DayHourWeatherForecastResponseModel.toResponseModelData(): DayHourWeatherForecastResponseModelData? =
    runCatching {

        DayHourWeatherForecastResponseModelData(
            timeList = this.dataInHour?.time?.map { it!! }!!,
            windSpeedList = this.dataInHour.windSpeed?.map { it!! }!!,
            temperatureList = this.dataInHour.temperature?.map { it!! }!!,
            precipitationProbabilityList = this.dataInHour.precipitationProbability?.map { it!! }!!,
            feltTemperatureList = this.dataInHour.feltTemperature?.map { it!! }!!,
            isDayLightList = this.dataInHour.isDaylight?.map { it!! }!!,
            uvIndexList = this.dataInHour.uvIndex?.map { it!! }!!,
            relativeHumidityList = this.dataInHour.relativeHumidity?.map { it!! }!!,
            windDirectionList = this.dataInHour.windDirection?.map { it!! }!!,
            precipitationList = this.dataInHour.precipitation?.map { it!! }!!,
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

fun DayHourWeatherForecastResponseModelData.toListOfHourDataModels(): List<HourWeatherForecastModelData> =
    this.timeList.mapIndexed { ind, time ->
        HourWeatherForecastModelData(
            windSpeed = this.windSpeedList[ind],
            temperature = this.temperatureList[ind],
            precipitationProbability = this.precipitationProbabilityList[ind],
            precipitation = this.precipitationList[ind],
            feltTemperature = this.feltTemperatureList[ind],
            isDayLight = this.isDayLightList[ind],
            uvIndex = this.uvIndexList[ind],
            relativeHumidity = this.relativeHumidityList[ind],
            windDirection = this.windDirectionList[ind],
            hourTime = time
        )
    }