package com.alenniboris.personalmanager.data.source.remote.model.weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherForecastResponseModel(
    @SerializedName("data_current")
    val currentData: DataCurrent?,
    val metadata: Metadata?,
    val units: Units?
) {
    data class DataCurrent(
        @SerializedName("isdaylight")
        val isDaylight: String?,
        @SerializedName("isobserveddata")
        val isObservedData: String?,
        @SerializedName("metarid")
        val metarId: String?,
        @SerializedName("pictocode")
        val picToCode: String?,
        @SerializedName("pictocode_detailed")
        val picToCodeDetailed: String?,
        val temperature: String?,
        val time: String?,
        @SerializedName("windspeed")
        val windSpeed: String?,
        @SerializedName("zenithangle")
        val zenithAngle: String?
    )

    data class Metadata(
        @SerializedName("generation_time_ms")
        val generationTimeMs: String?,
        val height: String?,
        val latitude: String?,
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
        val temperature: String?,
        val time: String?,
        @SerializedName("windspeed")
        val windSpeed: String?
    )
}