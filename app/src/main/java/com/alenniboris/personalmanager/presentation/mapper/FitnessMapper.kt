package com.alenniboris.personalmanager.presentation.mapper

import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.user.FitnessGoal

fun FitnessGoal.toUiString(): Int = when(this){
    FitnessGoal.Make -> R.string.make_weight_text
    FitnessGoal.Support -> R.string.maintain_weight_text
    FitnessGoal.Lose -> R.string.lose_weight_text
}