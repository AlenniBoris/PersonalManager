package com.alenniboris.personalmanager.data.source.remote.service.weather

import android.app.Application
import com.alenniboris.personalmanager.BuildConfig
import com.alenniboris.personalmanager.data.source.remote.model.weather.CloudsWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.CurrentWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.DayHourWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.MultipleDaysWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.model.weather.SunMoonWeatherForecastResponseModel
import com.alenniboris.personalmanager.data.source.remote.utils.MyRetrofit
import com.alenniboris.personalmanager.data.source.remote.utils.values.MeteoblueApiValues
import com.andretietz.retrofit.ResponseCache
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface IWeatherMeteoblueApiService {

    @GET(MeteoblueApiValues.PACKAGE_CURRENT)
    @ResponseCache(60, TimeUnit.MINUTES)
    suspend fun getCurrentWeatherForecast(
        @Query(MeteoblueApiValues.QUERY_LATITUDE) lat: String,
        @Query(MeteoblueApiValues.QUERY_LONGITUDE) lon: String,
        @Query(MeteoblueApiValues.QUERY_TEMPERATURE) temperatureFormat: String = MeteoblueApiValues.BASE_VALUE_TEMPERATURE_FORMAT,
        @Query(MeteoblueApiValues.QUERY_TIME_FORMAT) timeFormat: String = MeteoblueApiValues.BASE_VALUE_TIME_FORMAT,
        @Query(MeteoblueApiValues.HEADER_APIKEY) apiKey: String = BuildConfig.METEOBLUE_API_KEY
    ): CurrentWeatherForecastResponseModel

    @GET(MeteoblueApiValues.PACKAGE_SUNMOON)
    @ResponseCache(24, TimeUnit.HOURS)
    suspend fun getSunMoonForecast(
        @Query(MeteoblueApiValues.QUERY_LATITUDE) lat: String,
        @Query(MeteoblueApiValues.QUERY_LONGITUDE) lon: String,
        @Query(MeteoblueApiValues.QUERY_FORECAST_DAYS) forecastDays: String = MeteoblueApiValues.BASE_VALUE_FORECAST_DAYS,
        @Query(MeteoblueApiValues.QUERY_TIME_FORMAT) timeFormat: String = MeteoblueApiValues.BASE_VALUE_TIME_FORMAT,
        @Query(MeteoblueApiValues.HEADER_APIKEY) apiKey: String = BuildConfig.METEOBLUE_API_KEY
    ): SunMoonWeatherForecastResponseModel

    @GET(MeteoblueApiValues.PACKAGE_CLOUDS_DAY)
    @ResponseCache(24, TimeUnit.HOURS)
    suspend fun getCloudsForecast(
        @Query(MeteoblueApiValues.QUERY_LATITUDE) lat: String,
        @Query(MeteoblueApiValues.QUERY_LONGITUDE) lon: String,
        @Query(MeteoblueApiValues.QUERY_FORECAST_DAYS) forecastDays: String = MeteoblueApiValues.BASE_VALUE_FORECAST_DAYS,
        @Query(MeteoblueApiValues.QUERY_TIME_FORMAT) timeFormat: String = MeteoblueApiValues.BASE_VALUE_TIME_FORMAT,
        @Query(MeteoblueApiValues.HEADER_APIKEY) apiKey: String = BuildConfig.METEOBLUE_API_KEY
    ): CloudsWeatherForecastResponseModel

    @GET(MeteoblueApiValues.PACKAGE_BASIC_HOUR)
    @ResponseCache(24, TimeUnit.HOURS)
    suspend fun getDayHourWeatherForecast(
        @Query(MeteoblueApiValues.QUERY_LATITUDE) lat: String,
        @Query(MeteoblueApiValues.QUERY_LONGITUDE) lon: String,
        @Query(MeteoblueApiValues.QUERY_FORECAST_DAYS) forecastDays: String = MeteoblueApiValues.BASE_VALUE_FORECAST_DAYS_ONE,
        @Query(MeteoblueApiValues.QUERY_TEMPERATURE) temperatureFormat: String = MeteoblueApiValues.BASE_VALUE_TEMPERATURE_FORMAT,
        @Query(MeteoblueApiValues.QUERY_TIME_FORMAT) timeFormat: String = MeteoblueApiValues.BASE_VALUE_TIME_FORMAT,
        @Query(MeteoblueApiValues.HEADER_APIKEY) apiKey: String = BuildConfig.METEOBLUE_API_KEY
    ): DayHourWeatherForecastResponseModel

    @GET(MeteoblueApiValues.PACKAGE_BASIC_DAY)
    @ResponseCache(24, TimeUnit.HOURS)
    suspend fun getMultipleDaysWeatherForecast(
        @Query(MeteoblueApiValues.QUERY_LATITUDE) lat: String,
        @Query(MeteoblueApiValues.QUERY_LONGITUDE) lon: String,
        @Query(MeteoblueApiValues.QUERY_FORECAST_DAYS) forecastDays: String = MeteoblueApiValues.BASE_VALUE_FORECAST_DAYS,
        @Query(MeteoblueApiValues.QUERY_TEMPERATURE) temperatureFormat: String = MeteoblueApiValues.BASE_VALUE_TEMPERATURE_FORMAT,
        @Query(MeteoblueApiValues.QUERY_TIME_FORMAT) timeFormat: String = MeteoblueApiValues.BASE_VALUE_TIME_FORMAT,
        @Query(MeteoblueApiValues.HEADER_APIKEY) apiKey: String = BuildConfig.METEOBLUE_API_KEY
    ): MultipleDaysWeatherForecastResponseModel

    companion object {
        fun get(
            apl: Application
        ) = MyRetrofit()
            .create<IWeatherMeteoblueApiService>(
                apl = apl,
                url = MeteoblueApiValues.BASE_URL
            )
    }
}