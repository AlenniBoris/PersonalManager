package com.alenniboris.personalmanager.presentation.model.activity

import com.alenniboris.personalmanager.domain.model.activity.ActivityModelDomain
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import com.alenniboris.personalmanager.presentation.uikit.utils.TimeMeasurementUnit
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

    val timeStats = ScreensCommonUtils.getTimeStats(creationDate = domainModel.markingTime)
    val timeText: String = when {
        timeStats.years > 0 -> timeStats.years.toString()
        timeStats.months > 0 -> timeStats.months.toString()
        timeStats.days > 0 -> timeStats.days.toString()
        timeStats.hours > 0 -> timeStats.hours.toString()
        timeStats.minutes > 0 -> timeStats.minutes.toString()
        else -> ""
    }
    val timeUnit: TimeMeasurementUnit = when {
        timeStats.years > 0 -> TimeMeasurementUnit.Year
        timeStats.months > 0 -> TimeMeasurementUnit.Month
        timeStats.days > 0 -> TimeMeasurementUnit.Day
        timeStats.hours > 0 -> TimeMeasurementUnit.Hour
        timeStats.minutes > 0 -> TimeMeasurementUnit.Minute
        else -> TimeMeasurementUnit.Recent
    }
}

fun ActivityModelDomain.toModelUi() =
    ActivityModelUi(domainModel = this)
