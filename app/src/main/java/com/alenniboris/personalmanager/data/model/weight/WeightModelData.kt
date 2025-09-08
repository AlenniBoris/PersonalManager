package com.alenniboris.personalmanager.data.model.weight

import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import java.util.Date

data class WeightModelData(
    val id: String? = null,
    val userId: String? = null,
    val weight: String? = null,
    val markingDate: String? = null,
    val markingTime: String? = null
)

fun WeightModelData.toModelDomain(): WeightModelDomain? = runCatching {
    WeightModelDomain(
        id = this.id!!,
        userId = this.userId!!,
        weight = this.weight!!.toDouble(),
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

fun WeightModelDomain.toModelData(): WeightModelData =
    WeightModelData(
        id = this.id,
        userId = this.userId,
        weight = this.weight.toString(),
        markingDate = this.markingDate.time.toString(),
        markingTime = this.markingTime.time.toString()
    )