package com.alenniboris.personalmanager.data.mapper

import com.alenniboris.personalmanager.data.source.remote.model.location.CurrentLocationResponseModel
import com.alenniboris.personalmanager.domain.utils.LogPrinter

fun CurrentLocationResponseModel.toLocationString(): String =
    runCatching {

        val results = this.features?.firstOrNull()
        results?.let { results ->
            val properties = results.properties

            properties
                ?.city
                ?: properties?.county
                ?: properties?.district
                ?: properties?.country
                ?: properties?.name
                ?: ""
        } ?: ""
    }.getOrElse { exception ->
        LogPrinter.printLog(
            tag = "!!!",
            message = """
                CurrentLocationResponseModel.toLocationString, 
                
                ${exception.stackTraceToString()}
            """.trimIndent()
        )
        ""
    }