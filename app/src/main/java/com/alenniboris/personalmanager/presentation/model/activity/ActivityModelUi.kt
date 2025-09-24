package com.alenniboris.personalmanager.presentation.model.activity

import com.alenniboris.personalmanager.domain.model.activity.ActivityModelDomain
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ActivityModelUi(
    val domainModel: ActivityModelDomain
) {
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

    val name = domainModel.title

    val time = domainModel.duration.toString()
    val calories = domainModel.calories.toString()
}

fun ActivityModelDomain.toModelUi() =
    ActivityModelUi(domainModel = this)
