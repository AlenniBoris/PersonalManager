package com.alenniboris.personalmanager.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherErrorResponse(
    @SerializedName("error")
    val isError: String?,
    @SerializedName("error_message")
    val message: String?
)
