package com.alenniboris.personalmanager.presentation.mapper

import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.weather.WindDirection

fun WindDirection.toUiString(): Int = when(this){
    WindDirection.North -> R.string.wind_north_direction
    WindDirection.NorthWest -> R.string.wind_north_west_direction
    WindDirection.West -> R.string.wind_west_direction
    WindDirection.WestSouth -> R.string.wind_west_south_direction
    WindDirection.South -> R.string.wind_south_direction
    WindDirection.SouthEast -> R.string.wind_south_east_direction
    WindDirection.East -> R.string.wind_east_direction
    WindDirection.EastNorth -> R.string.wind_east_north_direction
    WindDirection.Unknown -> R.string.wind_unknown_direction
}
