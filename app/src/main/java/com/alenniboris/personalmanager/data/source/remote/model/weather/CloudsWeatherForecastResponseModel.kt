package com.alenniboris.personalmanager.data.source.remote.model.weather

import com.google.gson.annotations.SerializedName

data class CloudsWeatherForecastResponseModel(
    @SerializedName("data_day")
    val dayData: DayData?,
    val metadata: Metadata?,
    val units: Units?
) {
    data class DayData(
        @SerializedName("fog_probability")
        val fogProbability: List<String?>?,
        @SerializedName("highclouds_max")
        val highCloudsMax: List<String?>?,
        @SerializedName("highclouds_mean")
        val highCloudsMean: List<String?>?,
        @SerializedName("highclouds_min")
        val highCloudsMin: List<String?>?,
        @SerializedName("lowclouds_max")
        val lowCloudsMax: List<String?>?,
        @SerializedName("lowclouds_mean")
        val lowCloudsMean: List<String?>?,
        @SerializedName("lowclouds_min")
        val lowCloudsMin: List<String?>?,
        @SerializedName("midclouds_max")
        val midCloudsMax: List<String?>?,
        @SerializedName("midclouds_mean")
        val midCloudsMean: List<String?>?,
        @SerializedName("midclouds_min")
        val midCloudsMin: List<String?>?,
        @SerializedName("sunshine_time")
        val sunshineTime: List<String?>?,
        val time: List<String?>?,
        @SerializedName("totalcloudcover_max")
        val totalCloudCoverMax: List<String?>?,
        @SerializedName("totalcloudcover_mean")
        val totalCloudCoverMean: List<String?>?,
        @SerializedName("totalcloudcover_min")
        val totalCloudCoverMin: List<String?>?,
        @SerializedName("visibility_max")
        val visibilityMax: List<String?>?,
        @SerializedName("visibility_mean")
        val visibilityMean: List<String?>?,
        @SerializedName("visibility_min")
        val visibilityMin: List<String?>?
    )

    data class Metadata(
        @SerializedName("generation_time_ms")
        val generationTimeMs: String?,
        val height: String?,
        val latitude: String?,
        val longitude: String?,
        @SerializedName("modelrun_updatetime_utc")
        val modelRunUpdatetimeUtc: String?,
        @SerializedName("modelrun_utc")
        val modelRunUtc: String?,
        val name: String?,
        @SerializedName("timezone_abbrevation")
        val timezoneAbbrevation: String?,
        @SerializedName("utc_timeoffset")
        val utcTimeOffset: String?
    )

    data class Units(
        @SerializedName("cloudcover")
        val cloudCover: String?,
        val probability: String?,
        @SerializedName("sunshinetime")
        val sunshineTime: String?,
        val time: String?,
        val visibility: String?
    )
}