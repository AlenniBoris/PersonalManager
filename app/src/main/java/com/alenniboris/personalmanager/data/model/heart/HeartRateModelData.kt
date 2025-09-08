package com.alenniboris.personalmanager.data.model.heart

import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import java.util.Date

data class HeartRateModelData(
    val id: String? = null,
    val userId: String? = null,
    val heartRate: String? = null,
    val markingDate: String? = null,
    val markingTime: String? = null
)

fun HeartRateModelData.toModelDomain(): HeartRateModelDomain? = runCatching {
    HeartRateModelDomain(
        id = this.id!!,
        userId = this.userId!!,
        heartRate = this.heartRate!!.toInt(),
        markingDate = Date(this.markingDate!!.toLong()),
        markingTime = Date(this.markingTime!!.toLong())
    )
}.getOrElse { exception ->
    LogPrinter.printLog(
        tag = "!!!",
        message = """
            HeartRateModelData.toModelDomain() 
            
            ${exception.stackTraceToString()}
        """.trimIndent()
    )
    null
}

fun HeartRateModelDomain.toModelData(): HeartRateModelData =
    HeartRateModelData(
        id = this.id,
        userId = this.userId,
        heartRate = this.heartRate.toString(),
        markingDate = this.markingDate.time.toString(),
        markingTime = this.markingTime.time.toString()
    )