package com.alenniboris.personalmanager.presentation.screens.weather

import com.alenniboris.personalmanager.presentation.model.weather.DayWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.weather.HourWeatherForecastModelUi

sealed interface IWeatherScreenIntent {
    data class ChangeViewedOption(val option: WeatherScreenOptions) : IWeatherScreenIntent
    data class UpdateSelectedHour(val selected: HourWeatherForecastModelUi?) : IWeatherScreenIntent
    data class UpdateSelectedDay(val selected: DayWeatherForecastModelUi?) : IWeatherScreenIntent
    data object OpenPersonalScreen : IWeatherScreenIntent
    data object ChangeSettingsDialogVisibility: IWeatherScreenIntent
}