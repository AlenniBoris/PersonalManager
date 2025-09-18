package com.alenniboris.personalmanager.domain.model

import java.util.Date

data class ActivityModelDomain(
    val id: String,
    val userId: String,
    val title: String,
    val duration: Int,
    val calories: Double,
    val markingDate: Date,
    val markingTime: Date
)
