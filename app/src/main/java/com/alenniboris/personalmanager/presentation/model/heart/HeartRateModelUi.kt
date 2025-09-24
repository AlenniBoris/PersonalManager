package com.alenniboris.personalmanager.presentation.model.heart

import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.presentation.uikit.model.IPlotModelUi
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class HeartRateModelUi(
    val domainModel: HeartRateModelDomain
) : IPlotModelUi{
    val heartRateValue: Int = domainModel.heartRate
    val heartRateText: String = "${domainModel.heartRate} bpm"
    val measurementDateText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(domainModel.markingDate)

    val measurementTimeText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_HOUR_PATTERN, Locale.getDefault()
        ).format(domainModel.markingTime)

    val fullTimeText: String = "$measurementDateText/$measurementTimeText"

    override val time: Date = domainModel.markingTime
    override val modelValue: Double = heartRateValue.toDouble()
    override val valueText: String = heartRateText
    override val timeText: String = fullTimeText
}

fun HeartRateModelDomain.toModelUi(): HeartRateModelUi =
    HeartRateModelUi(
        domainModel = this
    )