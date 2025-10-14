package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.model.weather.CurrentWeatherForecastModelData
import com.alenniboris.personalmanager.data.model.weather.toModelDomain
import com.alenniboris.personalmanager.data.source.remote.model.weather.CloudsWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.CurrentWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.DayHourWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.MultipleDaysWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.SunMoonWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.service.weather.IWeatherMeteoblueApiService
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.utils.GsonUtil.fromJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class WeatherRepositoryImplTest {

    private val WEATHER_FORECAST_WEEK = """
        {
            "metadata": {
                "modelrun_updatetime_utc": 1760433444000,
                "name": "",
                "height": 206,
                "timezone_abbrevation": "GMT+03",
                "latitude": 53.906,
                "modelrun_utc": 1760433444000,
                "longitude": 27.575,
                "utc_timeoffset": 3.0,
                "generation_time_ms": 7.2540045
            },
            "units": {
                "predictability": "percent",
                "precipitation": "mm",
                "windspeed": "ms-1",
                "precipitation_probability": "percent",
                "relativehumidity": "percent",
                "time": "milliseconds since 1 January 1970 00:00 UTC",
                "temperature": "C",
                "pressure": "hPa",
                "winddirection": "degree"
            },
            "data_day": {
                "time": [1760410800000, 1760497200000, 1760583600000, 1760670000000, 1760756400000, 1760842800000, 1760929200000],
                "temperature_instant": [4.2, 2.67, 4.73, 7.17, 6.13, 1.98, 2.2],
                "precipitation": [0.0, 0.0, 0.8, 3.0, 5.4, 0.0, 0.0],
                "predictability": [80, 83, 67, 42, 33, 59, 61],
                "temperature_max": [6.17, 6.59, 8.85, 9.02, 7.71, 5.89, 6.48],
                "sealevelpressure_mean": [1016, 1018, 1018, 1014, 1013, 1018, 1021],
                "windspeed_mean": [1.99, 2.11, 1.99, 2.18, 2.26, 2.27, 1.94],
                "precipitation_hours": [0.0, 0.0, 1.0, 3.0, 4.0, 0.0, 0.0],
                "sealevelpressure_min": [1013, 1017, 1016, 1013, 1011, 1015, 1020],
                "pictocode": [3, 3, 16, 16, 12, 3, 3],
                "snowfraction": [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0],
                "humiditygreater90_hours": [0.08, 0.08, 0.21, 0.92, 0.21, 0.25, 0.17],
                "convective_precipitation": [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0],
                "relativehumidity_max": [100, 100, 100, 100, 100, 100, 100],
                "temperature_min": [2.52, 2.89, 4.73, 6.13, 1.98, 0.06, 0.67],
                "winddirection": [315, 270, 225, 225, 315, 0, 270],
                "felttemperature_max": [2.75, 3.45, 6.69, 6.93, 4.82, 2.14, 2.73],
                "relativehumidity_min": [76, 86, 93, 93, 82, 75, 67],
                "felttemperature_mean": [1.22, 1.88, 4.41, 5.16, 2.5, -0.7, -0.27],
                "windspeed_min": [1.6, 1.23, 1.34, 1.6, 1.87, 1.75, 1.31],
                "felttemperature_min": [-0.33, -0.04, 2.5, 3.95, -1.32, -3.55, -2.6],
                "precipitation_probability": [14, 19, 30, 63, 64, 9, 14],
                "uvindex": [1, 1, 0, 1, 1, 2, 1],
                "rainspot": ["0000009000009990009999090990999900099990009999000", "0000000000000000000000000000000000000000000000000", "1011119001100001110001111000111111111111111111111", "3312223332232233232223322232333233233332323333332", "2332223332222322322232223223332221222333232233223", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000"],
                "temperature_mean": [4.23, 4.77, 6.67, 7.4, 5.38, 2.79, 3.09],
                "sealevelpressure_max": [1018, 1020, 1019, 1016, 1015, 1021, 1021],
                "relativehumidity_mean": [92, 95, 99, 98, 94, 90, 85],
                "predictability_class": [5.0, 5.0, 4.0, 3.0, 2.0, 3.0, 4.0],
                "windspeed_max": [2.46, 2.7, 2.56, 2.55, 2.76, 2.8, 2.76]
            }
        }
    """.trimIndent().fromJson<MultipleDaysWeatherForecastResponseModel>()
    private val WEATHER_RESPONSE_CURRENT = """
        {
            "metadata": {
                "modelrun_updatetime_utc": 1760433723000,
                "name": "",
                "height": 206,
                "timezone_abbrevation": "GMT+03",
                "latitude": 53.906,
                "modelrun_utc": 1760433723000,
                "longitude": 27.575,
                "utc_timeoffset": 3.0,
                "generation_time_ms": 12.995005
            },
            "units": {
                "temperature": "C",
                "time": "milliseconds since 1 January 1970 00:00 UTC",
                "windspeed": "ms-1"
            },
            "data_current": {
                "time": 1760432400000,
                "isobserveddata": 1,
                "metarid": null,
                "isdaylight": 1,
                "windspeed": null,
                "zenithangle": 62.224358,
                "pictocode_detailed": 22,
                "pictocode": 4,
                "temperature": 5.288739
            }
        }
    """.trimIndent().fromJson<CurrentWeatherForecastResponseModel>()
    private val WEATHER_RESPONSE_CLOUDS_DAY = """
        {
            "metadata": {
                "modelrun_updatetime_utc": 1760433723000,
                "name": "",
                "height": 206,
                "timezone_abbrevation": "GMT+03",
                "latitude": 53.906,
                "modelrun_utc": 1760433723000,
                "longitude": 27.575,
                "utc_timeoffset": 3.0,
                "generation_time_ms": 7.254958
            },
            "units": {
                "cloudcover": "percent",
                "time": "milliseconds since 1 January 1970 00:00 UTC",
                "probability": "percent",
                "visibility": "m",
                "sunshinetime": "minutes"
            },
            "data_day": {
                "time": [1760410800000, 1760497200000, 1760583600000, 1760670000000, 1760756400000, 1760842800000, 1760929200000],
                "fog_probability": [79, 5, 10, 80, 9, 3, 10],
                "totalcloudcover_max": [100, 100, 100, 95, 100, 94, 89],
                "lowclouds_max": [97, 90, 100, 92, 97, 94, 89],
                "midclouds_mean": [62, 72, 70, 84, 90, 41, 7],
                "highclouds_max": [98, 88, 9, 55, 100, 2, 57],
                "lowclouds_mean": [75, 77, 81, 79, 83, 64, 41],
                "visibility_min": [420, 9420, 8620, 400, 8620, 9820, 8800],
                "totalcloudcover_mean": [79, 81, 90, 84, 93, 64, 42],
                "totalcloudcover_min": [6, 71, 67, 70, 77, 1, 0],
                "midclouds_max": [100, 100, 100, 95, 100, 72, 78],
                "highclouds_mean": [13, 17, 0, 9, 18, 0, 10],
                "midclouds_min": [6, 41, 23, 68, 71, 0, 0],
                "visibility_max": [17620, 16020, 10820, 12600, 20600, 26800, 14800],
                "sunshine_time": [55, 60, 19, 58, 0, 137, 230],
                "highclouds_min": [0, 0, 0, 0, 0, 0, 0],
                "visibility_mean": [10990, 12063, 9748, 6673, 13500, 15300, 11927],
                "lowclouds_min": [6, 68, 55, 66, 69, 1, 0]
            }
        }
    """.trimIndent().fromJson<CloudsWeatherForecastResponseModel>()
    private val WEATHER_RESPONSE_SUNMOON = """
        {
            "metadata": {
                "modelrun_updatetime_utc": 1760433723000,
                "name": "",
                "height": 206,
                "timezone_abbrevation": "GMT+03",
                "latitude": 53.906,
                "modelrun_utc": 1760433723000,
                "longitude": 27.575,
                "utc_timeoffset": 3.0,
                "generation_time_ms": 5.4090023
            },
            "units": {
                "time": "milliseconds since 1 January 1970 00:00 UTC"
            },
            "data_day": {
                "time": [1760410800000, 1760497200000, 1760583600000, 1760670000000, 1760756400000, 1760842800000, 1760929200000],
                "moonrise": ["23:54", "----", "01:20", "02:42", "04:01", "05:17", "06:32"],
                "moonset": ["16:21", "16:38", "16:49", "16:58", "17:06", "17:13", "17:20"],
                "moonphaseangle": [279.12, 291.33, 303.25, 314.92, 326.37, 337.59, 348.51],
                "sunset": ["18:17", "18:14", "18:12", "18:10", "18:08", "18:05", "18:03"],
                "moonilluminatedfraction": [42.07, 31.81, 22.58, 14.69, 8.37, 3.77, 1.0],
                "moonphasename": ["waning crescent", "waning crescent", "waning crescent", "waning crescent", "waning crescent", "waning crescent", "waning crescent"],
                "moonphasetransittime": ["", "", "", "", "", "", ""],
                "sunrise": ["07:34", "07:36", "07:37", "07:39", "07:41", "07:43", "07:45"],
                "moonage": [22.9, 23.9, 24.88, 25.83, 26.77, 27.69, 28.59]
            }
        }
    """.trimIndent().fromJson<SunMoonWeatherForecastResponseModel>()
    private val WEATHER_RESPONSE_BASIC_DAY = """
       {
            "metadata": {
                "modelrun_updatetime_utc": 1760433723000,
                "name": "",
                "height": 206,
                "timezone_abbrevation": "GMT+03",
                "latitude": 53.906,
                "modelrun_utc": 1760433723000,
                "longitude": 27.575,
                "utc_timeoffset": 3.0,
                "generation_time_ms": 8.900046
            },
            "units": {
                "predictability": "percent",
                "precipitation": "mm",
                "windspeed": "ms-1",
                "precipitation_probability": "percent",
                "relativehumidity": "percent",
                "time": "milliseconds since 1 January 1970 00:00 UTC",
                "temperature": "C",
                "pressure": "hPa",
                "winddirection": "degree"
            },
            "data_day": {
                "time": [1760410800000],
                "temperature_instant": [4.2],
                "precipitation": [0.0],
                "predictability": [80],
                "temperature_max": [6.17],
                "sealevelpressure_mean": [1016],
                "windspeed_mean": [1.99],
                "precipitation_hours": [0.0],
                "sealevelpressure_min": [1013],
                "pictocode": [3],
                "snowfraction": [0.0],
                "humiditygreater90_hours": [0.08],
                "convective_precipitation": [0.0],
                "relativehumidity_max": [100],
                "temperature_min": [2.52],
                "winddirection": [315],
                "felttemperature_max": [2.75],
                "relativehumidity_min": [76],
                "felttemperature_mean": [1.22],
                "windspeed_min": [1.6],
                "felttemperature_min": [-0.33],
                "precipitation_probability": [14],
                "uvindex": [1],
                "rainspot": ["0000009000009990009999090990999900099990009999000"],
                "temperature_mean": [4.23],
                "sealevelpressure_max": [1018],
                "relativehumidity_mean": [92],
                "predictability_class": [5.0],
                "windspeed_max": [2.46]
            }
        } 
    """.trimIndent().fromJson<MultipleDaysWeatherForecastResponseModel>()
    private val WEATHER_FORECAST_TODAY = """
        {
            "metadata": {
                "modelrun_updatetime_utc": 1760433972000,
                "name": "",
                "height": 206,
                "timezone_abbrevation": "GMT+03",
                "latitude": 53.906,
                "modelrun_utc": 1760433972000,
                "longitude": 27.575,
                "utc_timeoffset": 3.0,
                "generation_time_ms": 6.949067
            },
            "units": {
                "precipitation": "mm",
                "windspeed": "ms-1",
                "precipitation_probability": "percent",
                "relativehumidity": "percent",
                "temperature": "C",
                "time": "milliseconds since 1 January 1970 00:00 UTC",
                "pressure": "hPa",
                "winddirection": "degree"
            },
            "data_1h": {
                "time": [1760389200000, 1760392800000, 1760396400000, 1760400000000, 1760403600000, 1760407200000, 1760410800000, 1760414400000, 1760418000000, 1760421600000, 1760425200000, 1760428800000, 1760432400000, 1760436000000, 1760439600000, 1760443200000, 1760446800000, 1760450400000, 1760454000000, 1760457600000, 1760461200000, 1760464800000, 1760468400000, 1760472000000, 1760475600000],
                "snowfraction": [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0],
                "windspeed": [2.0, 1.93, 1.84, 1.85, 1.83, 1.89, 1.95, 1.97, 1.96, 2.02, 2.1, 2.11, 2.14, 2.25, 2.36, 2.46, 2.45, 2.35, 2.14, 1.87, 1.78, 1.7, 1.64, 1.6, 1.67],
                "temperature": [4.2, 4.0, 3.93, 3.89, 3.72, 3.89, 3.97, 3.96, 3.86, 3.8, 4.17, 4.74, 5.29, 5.76, 6.1, 6.17, 5.94, 5.43, 4.79, 4.09, 3.38, 2.81, 2.52, 2.52, 2.67],
                "precipitation_probability": [15, 0, 7, 7, 0, 0, 5, 0, 7, 6, 6, 6, 8, 6, 6, 14, 6, 6, 7, 6, 0, 0, 0, 0, 0],
                "convective_precipitation": [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0],
                "rainspot": ["0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000099900009990000000000", "0000009000000900009990000990000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000009999000", "0000000000000000000000090000009000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000000000000000000", "0000000000000000000000000000000000090000009000000", "0000000000000090000009000000900000000000000000000"],
                "pictocode": [22, 22, 22, 22, 7, 19, 4, 19, 13, 19, 22, 22, 22, 22, 22, 22, 9, 22, 19, 7, 19, 19, 19, 19, 7],
                "felttemperature": [1.34, 1.21, 1.21, 1.1, 0.89, 1.06, 1.12, 1.1, 0.98, 0.84, 1.14, 1.67, 2.18, 2.53, 2.75, 2.73, 2.49, 2.06, 1.61, 1.11, 0.46, -0.07, -0.33, -0.3, -0.17],
                "precipitation": [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0],
                "isdaylight": [0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0],
                "uvindex": [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0],
                "relativehumidity": [97, 99, 100, 98, 97, 97, 98, 98, 97, 97, 93, 89, 85, 81, 77, 76, 77, 81, 85, 90, 95, 98, 100, 100, 100],
                "sealevelpressure": [1013.52, 1013.69, 1013.86, 1014.03, 1014.26, 1014.48, 1014.71, 1014.99, 1015.27, 1015.55, 1015.84, 1016.14, 1016.43, 1016.57, 1016.7, 1016.84, 1016.96, 1017.07, 1017.19, 1017.45, 1017.71, 1017.97, 1017.98, 1017.99, 1018.0],
                "winddirection": [302, 301, 303, 304, 305, 312, 315, 318, 317, 326, 330, 327, 318, 309, 307, 312, 309, 305, 299, 296, 291, 287, 280, 279, 279]
            }
        }
    """.trimIndent().fromJson<DayHourWeatherForecastResponseModel>()

    private val testScheduler = TestCoroutineScheduler()
    private val dispatchers: IAppDispatchers = object : IAppDispatchers {
        override val IO: CoroutineDispatcher = StandardTestDispatcher(testScheduler)
        override val DEFAULT: CoroutineDispatcher = StandardTestDispatcher(testScheduler)
        override val MAIN: CoroutineDispatcher = StandardTestDispatcher(testScheduler)
    }
    private val weatherApiService: IWeatherMeteoblueApiService =
        object : IWeatherMeteoblueApiService {
            override suspend fun getCurrentWeatherForecast(
                lat: String,
                lon: String,
                temperatureFormat: String,
                timeFormat: String,
                apiKey: String
            ): CurrentWeatherForecastResponseModel = WEATHER_RESPONSE_CURRENT

            override suspend fun getSunMoonForecast(
                lat: String,
                lon: String,
                forecastDays: String,
                timeFormat: String,
                apiKey: String
            ): SunMoonWeatherForecastResponseModel = WEATHER_RESPONSE_SUNMOON

            override suspend fun getCloudsForecast(
                lat: String,
                lon: String,
                forecastDays: String,
                timeFormat: String,
                apiKey: String
            ): CloudsWeatherForecastResponseModel = WEATHER_RESPONSE_CLOUDS_DAY

            override suspend fun getDayHourWeatherForecast(
                lat: String,
                lon: String,
                forecastDays: String,
                temperatureFormat: String,
                timeFormat: String,
                apiKey: String
            ): DayHourWeatherForecastResponseModel = WEATHER_FORECAST_TODAY

            override suspend fun getMultipleDaysWeatherForecast(
                lat: String,
                lon: String,
                forecastDays: String,
                temperatureFormat: String,
                timeFormat: String,
                apiKey: String
            ): MultipleDaysWeatherForecastResponseModel = WEATHER_FORECAST_WEEK
        }

    private val weatherRepository: WeatherRepositoryImpl = WeatherRepositoryImpl(
        weatherMeteoblueApiService = weatherApiService,
        dispatchers = dispatchers
    )

    @Test
    fun `current weather forecast returns correct model`() = runTest(testScheduler) {
        val res = weatherRepository.getCurrentWeatherForecast(
            lat = 53.906, lon = 27.575
        )
        val model = CurrentWeatherForecastModelData(
            temperature = WEATHER_RESPONSE_CURRENT.currentData?.temperature,
            windSpeed = WEATHER_RESPONSE_CURRENT.currentData?.windSpeed
                ?: WEATHER_RESPONSE_BASIC_DAY.daysData?.windSpeedMean?.firstOrNull(),
            windDirection = WEATHER_RESPONSE_BASIC_DAY.daysData?.windDirection?.firstOrNull(),
            visibilityDistance = WEATHER_RESPONSE_CLOUDS_DAY.dayData?.visibilityMean?.firstOrNull(),
            sunsetTime = WEATHER_RESPONSE_SUNMOON.dayData?.sunset?.firstOrNull(),
            sunriseTime = WEATHER_RESPONSE_SUNMOON.dayData?.sunrise?.firstOrNull(),
            uvIndex = WEATHER_RESPONSE_BASIC_DAY.daysData?.uvIndex?.firstOrNull(),
            relativeHumidity = WEATHER_RESPONSE_BASIC_DAY.daysData?.relativeHumidityMean?.firstOrNull(),
            precipitation = WEATHER_RESPONSE_BASIC_DAY.daysData?.precipitation?.firstOrNull(),
            precipitationProbability = WEATHER_RESPONSE_BASIC_DAY.daysData?.precipitationProbability?.firstOrNull(),
            place = ""
        ).toModelDomain()
        assert(res is CustomResultModelDomain.Success)
        assertEquals(res.result, model)
    }
}