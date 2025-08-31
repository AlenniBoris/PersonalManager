package com.alenniboris.personalmanager.presentation.screens.weather

import com.alenniboris.personalmanager.presentation.model.CurrentWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.DayWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.HourWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.UserModelUi

data class WeatherScreenState(
    val user: UserModelUi? = UserModelUi(),
    val isCurrentForecastLoading: Boolean = false,
    val currentWeatherForecast: CurrentWeatherForecastModelUi = CurrentWeatherForecastModelUi(),
    val isHourForecastLoading: Boolean = false,
    val hourWeatherForecast: List<HourWeatherForecastModelUi> = emptyList(),
    val selectedHourForecast: HourWeatherForecastModelUi? = null,
    val isWeekForecastLoading: Boolean = false,
    val weekWeatherForecast: List<DayWeatherForecastModelUi> = emptyList(),
    val selectedDayForecast: DayWeatherForecastModelUi? = null,
    val currentOption: WeatherScreenOptions = WeatherScreenOptions.Hourly,
    val listOfOptions: List<WeatherScreenOptions> = WeatherScreenOptions.entries.toList()
)