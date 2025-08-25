package com.alenniboris.personalmanager.data.source.remote.model.weather

import com.google.gson.annotations.SerializedName

data class DayHourWeatherForecastResponseModel(
    @SerializedName("data_1h")
    val dataInHour: DataInHour?,
    val metadata: Metadata?,
    val units: Units?
) {
    data class DataInHour(
        @SerializedName("convective_precipitation")
        val convectivePrecipitation: List<String?>?,
        @SerializedName("felttemperature")
        val feltTemperature: List<String?>?,
        @SerializedName("isdaylight")
        val isDaylight: List<String?>?,
        @SerializedName("pictocode")
        val picToCode: List<String?>?,
        val precipitation: List<String?>?,
        @SerializedName("precipitation_probability")
        val precipitationProbability: List<String?>?,
        @SerializedName("rainspot")
        val rainSpot: List<String?>?,
        @SerializedName("relativehumidity")
        val relativeHumidity: List<String?>?,
        @SerializedName("sealevelpressure")
        val seaLevelPressure: List<String?>?,
        @SerializedName("snowfraction")
        val snowFraction: List<String?>?,
        val temperature: List<String?>?,
        val time: List<String?>?,
        @SerializedName("uvindex")
        val uvIndex: List<String?>?,
        @SerializedName("winddirection")
        val windDirection: List<String?>?,
        @SerializedName("windspeed")
        val windSpeed: List<String?>?
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
        val precipitation: String?,
        @SerializedName("precipitation_probability")
        val precipitationProbability: String?,
        val pressure: String?,
        @SerializedName("relativehumidity")
        val relativeHumidity: String?,
        val temperature: String?,
        val time: String?,
        @SerializedName("winddirection")
        val windDirection: String?,
        @SerializedName("windspeed")
        val windSpeed: String?
    )
}