package com.alenniboris.personalmanager.presentation.screens.personal

import com.alenniboris.personalmanager.R

enum class PersonalScreenOption {
    Profile,
    Weight,
    Heart,
    Activity
}

fun PersonalScreenOption.toUiString(): Int = when (this) {
    PersonalScreenOption.Profile -> R.string.profile_option
    PersonalScreenOption.Weight -> R.string.weight_option
    PersonalScreenOption.Heart -> R.string.heart_option
    PersonalScreenOption.Activity -> R.string.activity_option
}