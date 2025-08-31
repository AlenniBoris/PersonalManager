package com.alenniboris.personalmanager.presentation.screens.weather

import com.alenniboris.personalmanager.R

enum class WeatherScreenOptions {
    Hourly,
    Weekly
}

fun WeatherScreenOptions.toUiString() = when (this) {
    WeatherScreenOptions.Hourly -> R.string.hourly_option
    WeatherScreenOptions.Weekly -> R.string.weekly_option
}