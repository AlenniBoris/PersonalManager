package com.alenniboris.personalmanager.presentation.model.weight

import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.utils.stripTime
import java.util.Calendar
import java.util.Date

data class AddingWeight(
    val id: String = "",
    val userId: String = "",
    val weight: String = "",
    val markingDate: Date = Calendar.getInstance().time.stripTime(),
    val markingTime: Date = Calendar.getInstance().time
) {

    fun toModelDomain(): WeightModelDomain =
        WeightModelDomain(
            id = this.id,
            userId = this.userId,
            weight = this.weight.toDouble(),
            markingDate = this.markingDate,
            markingTime = this.markingTime
        )
}