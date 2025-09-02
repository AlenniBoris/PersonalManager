package com.alenniboris.personalmanager.presentation.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.alenniboris.personalmanager.presentation.uikit.values.DayType
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
fun Date.toDayType(): DayType {
    val today = LocalDate.now()
    val tomorrow = today.plusDays(1)

    val localDate = this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return when (localDate) {
        today -> DayType.TODAY
        tomorrow -> DayType.TOMORROW
        else -> when (localDate.dayOfWeek) {
            DayOfWeek.MONDAY -> DayType.MONDAY
            DayOfWeek.TUESDAY -> DayType.TUESDAY
            DayOfWeek.WEDNESDAY -> DayType.WEDNESDAY
            DayOfWeek.THURSDAY -> DayType.THURSDAY
            DayOfWeek.FRIDAY -> DayType.FRIDAY
            DayOfWeek.SATURDAY -> DayType.SATURDAY
            DayOfWeek.SUNDAY -> DayType.SUNDAY
        }
    }
}

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