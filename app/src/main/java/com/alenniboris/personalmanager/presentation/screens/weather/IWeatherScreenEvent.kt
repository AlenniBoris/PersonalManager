package com.alenniboris.personalmanager.presentation.screens.weather

interface IWeatherScreenEvent {
    data class ShowToast(val messageId: Int) : IWeatherScreenEvent
    data object OpenPersonalScreen : IWeatherScreenEvent
}