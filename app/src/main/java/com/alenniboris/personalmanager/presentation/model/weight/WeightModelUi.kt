package com.alenniboris.personalmanager.presentation.model.weight

import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.presentation.uikit.model.IPlotModelUi
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class WeightModelUi(
    val domainModel: WeightModelDomain
) : IPlotModelUi {

    val weightValue: Double = domainModel.weight
    val weightText: String = domainModel.weight.toString()
    val measurementDateText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(domainModel.markingDate)

    val measurementTime: Date = domainModel.markingTime
    val measurementTimeText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_HOUR_PATTERN, Locale.getDefault()
        ).format(measurementTime)

    val fullTimeText: String = "$measurementDateText/$measurementTimeText"

    override val time: Date = measurementTime
    override val modelValue: Double = weightValue
    override val valueText: String = weightText
    override val timeText: String = fullTimeText
}

fun WeightModelDomain.toModelUi(): WeightModelUi =
    WeightModelUi(
        domainModel = this
    )