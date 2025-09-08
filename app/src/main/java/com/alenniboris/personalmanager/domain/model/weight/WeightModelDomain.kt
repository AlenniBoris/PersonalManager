package com.alenniboris.personalmanager.domain.model.weight

import java.util.Date

data class WeightModelDomain(
    val id: String,
    val userId: String,
    val weight: Double,
    val markingDate: Date,
    val markingTime: Date
)