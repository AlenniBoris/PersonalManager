package com.alenniboris.personalmanager.presentation.uikit.values

import com.alenniboris.personalmanager.R

enum class DayType {
    TODAY,
    TOMORROW,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

fun DayType.toUiString() : Int =
    when(this){
        DayType.TODAY -> R.string.time_today_text
        DayType.TOMORROW -> R.string.time_tomorrow_text
        DayType.MONDAY -> R.string.time_monday_text
        DayType.TUESDAY -> R.string.time_tuesday_text
        DayType.WEDNESDAY -> R.string.time_wednesday_text
        DayType.THURSDAY -> R.string.time_thursday_text
        DayType.FRIDAY -> R.string.time_friday_text
        DayType.SATURDAY -> R.string.time_saturday_text
        DayType.SUNDAY -> R.string.time_sunday_text
    }