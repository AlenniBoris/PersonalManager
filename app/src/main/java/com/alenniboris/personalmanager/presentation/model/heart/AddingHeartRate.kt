package com.alenniboris.personalmanager.presentation.model.heart

import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.utils.stripTime
import java.util.Calendar
import java.util.Date
import kotlin.math.roundToInt

data class AddingHeartRate(
    val id: String = "",
    val userId: String = "",
    val heartRate: String = "",
    val markingDate: Date = Calendar.getInstance().time.stripTime(),
    val markingTime: Date = Calendar.getInstance().time
) {

    fun toModelDomain(): HeartRateModelDomain =
        HeartRateModelDomain(
            id = this.id,
            userId = this.userId,
            heartRate = this.heartRate.toDouble().roundToInt(),
            markingDate = this.markingDate,
            markingTime = this.markingTime
        )
}