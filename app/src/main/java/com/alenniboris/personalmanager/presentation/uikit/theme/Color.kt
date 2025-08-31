package com.alenniboris.personalmanager.presentation.uikit.theme

import androidx.compose.ui.graphics.Color

private val AppColorLight = Color(0xFFFFFFFF)
private val AppColorDark = Color(0xFF000000)

val appColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppColorLight
        true -> AppColorDark
    }

private val AppMainTextColorLight = Color(0xFF000000)
private val AppMainTextColorDark = Color(0xFFFFFFFF)
val appMainTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> AppMainTextColorLight
        true -> AppMainTextColorDark
    }

val appSubtleTextColor = Color(0xFF858585)

private val TopBarColorLight = Color(0xFF000000)
private val TopBarColorDark = Color(0xFFFFFFFF)
val topBarColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> TopBarColorLight
        true -> TopBarColorDark
    }

private val TopBarTextColorLight = Color(0xFFFFFFFF)
private val TopBarTextColorDark = Color(0xFF000000)
val topBarTextColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> TopBarTextColorLight
        true -> TopBarTextColorDark
    }

private val BottomBarActiveButtonColorLight = Color(0xFFD0D0D0)
private val BottomBarActiveButtonColorDark = Color(0xFF212121)
val bottomBarActiveButtonColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> BottomBarActiveButtonColorLight
        true -> BottomBarActiveButtonColorDark
    }

private val EnterTextFieldColorLight = Color(0xFFD0D0D0)
private val EnterTextFieldColorDark = Color(0xFF212121)
val enterTextFieldColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> EnterTextFieldColorLight
        true -> EnterTextFieldColorDark
    }

private val ButtonRowBackgroundColorLight = Color(0xFFD3D3D3)
private val ButtonRowBackgroundColorDark = Color(0xFF444444)
val buttonRowBackgroundColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> ButtonRowBackgroundColorLight
        true -> ButtonRowBackgroundColorDark
    }

private val ButtonRowActiveButtonColorLight = Color(0xFFEEEEEE)
private val ButtonRowActiveButtonColorDark = Color(0xFF313131)
val buttonRowActiveButtonColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> ButtonRowActiveButtonColorLight
        true -> ButtonRowActiveButtonColorDark
    }

private val LogRegScreenBackgroundColorLight = Color(0xFFD9D9D9)
private val LogRegScreenBackgroundColorDark = Color(0xFF252525)
val logRegScreenBackgroundColor
    get() = when (currentThemeMode.value.isThemeDark) {
        false -> LogRegScreenBackgroundColorLight
        true ->LogRegScreenBackgroundColorDark
    }

val weatherScreenCardColor = Color(0xFF013AE5)
val weatherScreenWaterColor = Color(0xFF0168E5)
val weatherScreenHumidityColor = Color(0xFF1AAD02)
val weatherScreenWindDirectionColor = Color(0xFF8303B2)
val weatherScreenWindColor = Color(0xFF73E501)
val weatherScreenWindGrayColor = Color(0xFF6B6B6B)
val weatherScreenVisibilityColor = Color(0xFF6945AF)
val weatherScreenUvIndexColor = Color(0xFFE0AB44)
val weatherScreenSunriseColor = Color(0xFFEC8F09)
val weatherScreenSunsetColor = Color(0xFFEC5809)
val weatherScreenCardTextColor = Color(0xFFFFFFFF)

