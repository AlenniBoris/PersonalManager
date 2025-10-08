package com.alenniboris.personalmanager.presentation.uikit.utils

import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.utils.stripTime
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

object ScreensCommonUtils {

    const val SIMPLE_DATE_PATTERN = "dd.MM.yyyy"
    const val SIMPLE_HOUR_PATTERN = "HH:mm"
    const val COMBINED_DATE_PATTERN = "$SIMPLE_DATE_PATTERN/$SIMPLE_HOUR_PATTERN"
    const val POINT_PATTERN = "\\d+(\\.\\d*)?"
    const val COMMA_PATTERN = "\\d+(\\,\\d*)?"

    fun getDateWeekAgo(): Date =
        Date(
            Calendar.getInstance().time.stripTime().time - 604800000
        )

    fun getCaloriesCount(
        proteins: Double,
        fats: Double,
        carbs: Double
    ): Double = (4 * proteins + 9 * fats + 4 * carbs).roundToInt().toDouble()

    const val DOUBLE_NUMBER_REGEX = "^\\d+.\\d+"
    const val ZERO_DOUBLE_VALUE = 0.0

    fun sanitizeCaloriesInputForTextField(input: String): String {
        if (input.isBlank()) return "0"

        var cleaned = input.replace(',', '.')

        val firstDotIndex = cleaned.indexOf('.')
        if (firstDotIndex != -1) {
            cleaned = cleaned.substring(0, firstDotIndex + 1) +
                    cleaned.substring(firstDotIndex + 1).replace(".", "")
        }

        if (cleaned.startsWith(".")) {
            cleaned = "0$cleaned"
        }

        cleaned = cleaned.replaceFirst("^0+(?!$|\\.)".toRegex(), "0")

        return cleaned
    }

    fun getTimeStats(creationDate: Date): TimeCount {
        val nowMillis = Calendar.getInstance().time.time
        val diffMillis = nowMillis - creationDate.time

        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
        val days = TimeUnit.MILLISECONDS.toDays(diffMillis)

        val years = (days / 365).toInt()
        val months = ((days % 365) / 30).toInt()
        val remainingDays = ((days % 365) % 30).toInt()
        val remainingHours = (hours % 24).toInt()
        val remainingMinutes = (minutes % 60).toInt()

        return TimeCount(
            years = years,
            months = months,
            days = remainingDays,
            hours = remainingHours,
            minutes = remainingMinutes
        )
    }
}

data class TimeCount(
    val years: Int,
    val months: Int,
    val days: Int,
    val hours: Int,
    val minutes: Int
)

enum class TimeMeasurementUnit {
    Year,
    Month,
    Day,
    Hour,
    Minute,
    Recent
}

fun TimeMeasurementUnit.toUiString(): Int = when (this) {
    TimeMeasurementUnit.Year -> R.string.year_unit_text
    TimeMeasurementUnit.Month -> R.string.months_unit_text
    TimeMeasurementUnit.Day -> R.string.days_unit_text
    TimeMeasurementUnit.Hour -> R.string.hours_unit_text
    TimeMeasurementUnit.Minute -> R.string.minutes_unit_text
    TimeMeasurementUnit.Recent -> R.string.recent_time_unit_text
}