package com.alenniboris.personalmanager.domain.utils

import java.util.Calendar
import java.util.Date

fun Date.stripTime(): Date {
    val cal = Calendar.getInstance().apply {
        time = this@stripTime
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return cal.time
}