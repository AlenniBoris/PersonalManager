package com.alenniboris.personalmanager.presentation.screens.home

sealed interface IHomeScreenEvent {
    data object OpenSettings : IHomeScreenEvent
}