package com.alenniboris.personalmanager.presentation.uikit.utils

import com.alenniboris.personalmanager.domain.utils.stripTime
import java.util.Calendar
import java.util.Date
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
}