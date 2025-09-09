package com.alenniboris.personalmanager.presentation.model.weight

import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.presentation.uikit.model.IPlotModelUi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class WeightModelUi(
    private val domainModel: WeightModelDomain
): IPlotModelUi {

    val weightValue: Double = domainModel.weight
    val weightText: String = "${domainModel.weight} kg"
    val measurementDateText: String =
        SimpleDateFormat(
            "dd.MM.yyyy", Locale.getDefault()
        ).format(domainModel.markingDate)

    val measurementTime: Date = domainModel.markingTime
    val measurementTimeText: String =
        SimpleDateFormat(
            "HH:mm", Locale.getDefault()
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