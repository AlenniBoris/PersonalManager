package com.alenniboris.personalmanager.data.source.remote.model.weather

import com.google.gson.annotations.SerializedName

data class SunMoonWeatherForecastResponseModel(
    @SerializedName("data_day")
    val dayData: DayData?,
    val metadata: Metadata?,
    val units: Units?
) {
    data class DayData(
        val moonage: List<String?>?,
        @SerializedName("")
        val moonIlluminatedFraction: List<String?>?,
        @SerializedName("moonphaseangle")
        val moonPhaseAngle: List<String?>?,
        @SerializedName("moonphasename")
        val moonPhaseName: List<String?>?,
        @SerializedName("moonphasetransittime")
        val moonPhaseTransitTime: List<String?>?,
        val moonrise: List<String?>?,
        val moonset: List<String?>?,
        val sunrise: List<String?>?,
        val sunset: List<String?>?,
        val time: List<String?>?
    )

    data class Metadata(
        @SerializedName("generation_time_ms")
        val generationTimeMs: String?,
        val height: String?,
        val latitude: String?,
        @SerializedName("")
        val longitude: String?,
        @SerializedName("modelrun_updatetime_utc")
        val modelRunUpdateTimeUtc: String?,
        @SerializedName("modelrun_utc")
        val modelRunUtc: String?,
        val name: String?,
        @SerializedName("timezone_abbrevation")
        val timezoneAbbrevation: String?,
        @SerializedName("utc_timeoffset")
        val utcTimeOffset: String?
    )

    data class Units(
        val time: String?
    )
}