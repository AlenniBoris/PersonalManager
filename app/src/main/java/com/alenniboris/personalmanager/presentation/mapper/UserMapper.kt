package com.alenniboris.personalmanager.presentation.mapper

import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.user.UserGender

fun UserGender.toUiString(): Int = when(this){
    UserGender.Male -> R.string.male_gender_text
    UserGender.Female -> R.string.female_gender_text
}