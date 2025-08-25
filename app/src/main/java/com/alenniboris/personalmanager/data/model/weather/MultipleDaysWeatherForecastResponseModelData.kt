package com.alenniboris.personalmanager.data.model.weather

import com.alenniboris.personalmanager.data.source.remote.model.weather.MultipleDaysWeatherForecastResponseModel
import com.alenniboris.personalmanager.domain.utils.LogPrinter

data class MultipleDaysWeatherForecastResponseModelData(
    val timeList: List<String>,
    val temperatureMinList: List<String>,
    val temperatureMaxList: List<String>,
    val feltTemperatureMinList: List<String>,
    val feltTemperatureMaxList: List<String>,
    val relativeHumidityList: List<String>,
    val uvIndexList: List<String>,
    val precipitationList: List<String>,
    val precipitationProbabilityList: List<String>,
    val windSpeedList: List<String>,
    val windDirectionList: List<String>
)

fun MultipleDaysWeatherForecastResponseModel.toResponseModelData(): MultipleDaysWeatherForecastResponseModelData? =
    runCatching {

        MultipleDaysWeatherForecastResponseModelData(
            timeList = this.daysData?.time?.map { it!! }!!,
            temperatureMinList = this.daysData?.temperatureMin?.map { it!! }!!,
            temperatureMaxList = this.daysData?.temperatureMax?.map { it!! }!!,
            feltTemperatureMinList = this.daysData?.feltTemperatureMin?.map { it!! }!!,
            feltTemperatureMaxList = this.daysData?.feltTemperatureMax?.map { it!! }!!,
            relativeHumidityList = this.daysData?.relativeHumidityMean?.map { it!! }!!,
            uvIndexList = this.daysData?.uvIndex?.map { it!! }!!,
            precipitationList = this.daysData?.precipitation?.map { it!! }!!,
            precipitationProbabilityList = this.daysData?.precipitationProbability?.map { it!! }!!,
            windSpeedList = this.daysData?.windSpeedMean?.map { it!! }!!,
            windDirectionList = this.daysData?.windDirection?.map { it!! }!!
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

fun MultipleDaysWeatherForecastResponseModelData.toListOfDataModels(): List<DayWeatherForecastModelData> =
    this.timeList.mapIndexed { index, time ->
        DayWeatherForecastModelData(
            dayDate = time,
            temperatureMin = this.temperatureMinList[index],
            temperatureMax = this.temperatureMaxList[index],
            feltTemperatureMin = this.feltTemperatureMinList[index],
            feltTemperatureMax = this.feltTemperatureMaxList[index],
            relativeHumidity = this.relativeHumidityList[index],
            uvIndex = this.uvIndexList[index],
            precipitationProbability = this.precipitationProbabilityList[index],
            precipitation = this.precipitationList[index],
            windSpeed = this.windSpeedList[index],
            windDirection = this.windDirectionList[index]
        )
    }