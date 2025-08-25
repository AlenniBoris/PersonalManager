package com.alenniboris.personalmanager.data.source.remote.model.weather

import com.google.gson.annotations.SerializedName

data class MultipleDaysWeatherForecastResponseModel(
    @SerializedName("data_day")
    val daysData: DaysData?,
    val metadata: Metadata?,
    val units: Units?
) {
    data class DaysData(
        @SerializedName("convective_precipitation")
        val convectivePrecipitation: List<String?>?,
        @SerializedName("felttemperature_max")
        val feltTemperatureMax: List<String?>?,
        @SerializedName("felttemperature_mean")
        val feltTemperatureMean: List<String?>?,
        @SerializedName("felttemperature_min")
        val feltTemperatureMin: List<String?>?,
        @SerializedName("humiditygreater90_hours")
        val humidityGreater90Hours: List<String?>?,
        @SerializedName("pictocode")
        val picToCode: List<String?>?,
        val precipitation: List<String?>?,
        @SerializedName("precipitation_hours")
        val precipitationHours: List<String?>?,
        @SerializedName("precipitation_probability")
        val precipitationProbability: List<String?>?,
        val predictability: List<String?>?,
        @SerializedName("predictability_class")
        val predictabilityClass: List<String?>?,
        @SerializedName("rainspot")
        val rainSpot: List<String?>?,
        @SerializedName("relativehumidity_max")
        val relativeHumidityMax: List<String?>?,
        @SerializedName("relativehumidity_mean")
        val relativeHumidityMean: List<String?>?,
        @SerializedName("relativehumidity_min")
        val relativeHumidityMin: List<String?>?,
        @SerializedName("sealevelpressure_max")
        val seaLevelPressureMax: List<String?>?,
        @SerializedName("sealevelpressure_mean")
        val seaLevelPressureMean: List<String?>?,
        @SerializedName("sealevelpressure_min")
        val seaLevelPressureMin: List<String?>?,
        @SerializedName("snowfraction")
        val snowFraction: List<String?>?,
        @SerializedName("temperature_instant")
        val temperatureInstant: List<String?>?,
        @SerializedName("temperature_max")
        val temperatureMax: List<String?>?,
        @SerializedName("temperature_mean")
        val temperatureMean: List<String?>?,
        @SerializedName("temperature_min")
        val temperatureMin: List<String?>?,
        val time: List<String?>?,
        @SerializedName("uvindex")
        val uvIndex: List<String?>?,
        @SerializedName("winddirection")
        val windDirection: List<String?>?,
        @SerializedName("windspeed_max")
        val windSpeedMmax: List<String?>?,
        @SerializedName("windspeed_mean")
        val windSpeedMean: List<String?>?,
        @SerializedName("windspeed_min")
        val windSpeedMin: List<String?>?
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
        val predictability: String?,
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