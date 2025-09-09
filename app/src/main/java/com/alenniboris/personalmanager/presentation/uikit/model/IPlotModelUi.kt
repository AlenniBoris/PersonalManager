package com.alenniboris.personalmanager.presentation.uikit.model

import java.util.Date

interface IPlotModelUi {
    val time: Date
    val modelValue: Double
    val valueText: String
    val timeText: String
}