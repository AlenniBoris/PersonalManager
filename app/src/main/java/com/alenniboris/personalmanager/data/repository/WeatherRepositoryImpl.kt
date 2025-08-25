package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.mapper.toCommonException
import com.alenniboris.personalmanager.data.model.weather.makeCurrentWeatherForecastModelData
import com.alenniboris.personalmanager.data.model.weather.toListOfDataModels
import com.alenniboris.personalmanager.data.model.weather.toListOfHourDataModels
import com.alenniboris.personalmanager.data.model.weather.toModelDomain
import com.alenniboris.personalmanager.data.model.weather.toResponseModelData
import com.alenniboris.personalmanager.data.source.remote.service.weather.IWeatherMeteoblueApiService
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.model.weather.CurrentWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.DayWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.repository.IWeatherRepository
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherMeteoblueApiService: IWeatherMeteoblueApiService,
    private val dispatchers: IAppDispatchers
) : IWeatherRepository {

    override suspend fun getCurrentWeatherForecast(
        lat: Double,
        lon: Double
    ): CustomResultModelDomain<CurrentWeatherForecastModelDomain, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val _currentResponse = async {
                    weatherMeteoblueApiService.getCurrentWeatherForecast(
                        lat = lat.toString(),
                        lon = lon.toString()
                    )
                }
                val _cloudsResponse = async {
                    weatherMeteoblueApiService.getCloudsForecast(
                        lat = lat.toString(),
                        lon = lon.toString()
                    )
                }
                val _sunMoonResponse = async {
                    weatherMeteoblueApiService.getSunMoonForecast(
                        lat = lat.toString(),
                        lon = lon.toString()
                    )
                }
                val _basicDayResponse = async {
                    weatherMeteoblueApiService.getMultipleDaysWeatherForecast(
                        lat = lat.toString(),
                        lon = lon.toString(),
                        forecastDays = "1"
                    )
                }

                val currentResponse = _currentResponse.await()
                val cloudsResponse = _cloudsResponse.await()
                val sunMoonResponse = _sunMoonResponse.await()
                val basicDayResponse = _basicDayResponse.await()

                val dataModel = makeCurrentWeatherForecastModelData(
                    currentWeatherForecastResponseModel = currentResponse,
                    cloudsWeatherForecastResponseModel = cloudsResponse,
                    sunMoonWeatherForecastResponseModel = sunMoonResponse,
                    multipleDaysWeatherForecastResponseModel = basicDayResponse
                )

                dataModel.toModelDomain()?.let {
                    return@withContext CustomResultModelDomain.Success(it)
                }

                return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.ErrorGettingData
                )
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!",
                    message = """
                        WeatherRepositoryImpl, getCurrentWeatherForecast
                        
                        ${exception.stackTraceToString()}
                    """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }

    override suspend fun getTodayHourWeatherForecast(
        lat: Double,
        lon: Double
    ): CustomResultModelDomain<List<HourWeatherForecastModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val response = weatherMeteoblueApiService.getDayHourWeatherForecast(
                    lat = lat.toString(),
                    lon = lon.toString()
                )

                response.toResponseModelData()?.let { responseDataModel ->
                    return@withContext CustomResultModelDomain.Success(
                        responseDataModel
                            .toListOfHourDataModels()
                            .map { it.toModelDomain() }
                    )
                }

                return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.ErrorGettingData
                )
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!",
                    message = """
                        WeatherRepositoryImpl, getTodayHourWeatherForecast
                        
                        ${exception.stackTraceToString()}
                    """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }

    override suspend fun getDayWeatherForecastForWeek(
        lat: Double,
        lon: Double
    ): CustomResultModelDomain<List<DayWeatherForecastModelDomain>, CommonExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {

                val response = weatherMeteoblueApiService.getMultipleDaysWeatherForecast(
                    lat = lat.toString(),
                    lon = lon.toString()
                )

                response.toResponseModelData()?.let { responseModelData ->
                    return@withContext CustomResultModelDomain.Success(
                        responseModelData
                            .toListOfDataModels()
                            .map { it.toModelDomain() }
                    )
                }

                return@withContext CustomResultModelDomain.Error(
                    CommonExceptionModelDomain.ErrorGettingData
                )
            }.getOrElse { exception ->
                LogPrinter.printLog(
                    tag = "!!!",
                    message = """
                        WeatherRepositoryImpl, getDayWeatherForecastForWeek
                        
                        ${exception.stackTraceToString()}
                    """.trimIndent()
                )
                return@withContext CustomResultModelDomain.Error(
                    exception.toCommonException()
                )
            }
        }
}