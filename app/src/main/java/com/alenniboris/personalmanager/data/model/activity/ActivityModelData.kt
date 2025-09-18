package com.alenniboris.personalmanager.data.model.activity

import com.alenniboris.personalmanager.domain.model.activity.ActivityModelDomain
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import java.util.Date

data class ActivityModelData(
    val id: String? = null,
    val userId: String? = null,
    val title: String? = null,
    val duration: String? = null,
    val calories: String? = null,
    val markingDate: String? = null,
    val markingTime: String? = null
)

fun ActivityModelData.toModelDomain(): ActivityModelDomain? = runCatching {
    ActivityModelDomain(
        id = this.id!!,
        userId = this.userId!!,
        title = this.title!!,
        duration = this.duration!!.toInt(),
        markingDate = Date(this.markingDate!!.toLong()),
        markingTime = Date(this.markingTime!!.toLong()),
        calories = this.calories!!.toDouble()
    )
}.getOrElse { exception ->
    LogPrinter.printLog(
        tag = "!!!",
        message = """
            ActivityModelData.toModelDomain, 
            
            ${exception.stackTraceToString()}
        """.trimIndent()
    )
    null
}

fun ActivityModelDomain.toModelData(): ActivityModelData =
    ActivityModelData(
        id = this.id,
        userId = this.userId,
        title = this.title,
        duration = this.duration.toString(),
        markingDate = this.markingDate.time.toString(),
        markingTime = this.markingTime.time.toString(),
        calories = this.calories.toString()
    )
