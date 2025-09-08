package com.alenniboris.personalmanager.domain.model.heart

import java.util.Date

data class HeartRateModelDomain(
    val id: String,
    val userId: String,
    val heartRate: Int,
    val markingDate: Date,
    val markingTime: Date
)