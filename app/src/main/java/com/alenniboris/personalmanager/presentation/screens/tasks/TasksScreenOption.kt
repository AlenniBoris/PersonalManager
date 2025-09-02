package com.alenniboris.personalmanager.presentation.screens.tasks

import com.alenniboris.personalmanager.R

enum class TasksScreenOption {
    All_tasks,
    Today,
    Upcoming,
    Skipped
}

fun TasksScreenOption.toUiString(): Int = when (this) {
    TasksScreenOption.All_tasks -> R.string.all_tasks_option
    TasksScreenOption.Today -> R.string.today_option
    TasksScreenOption.Upcoming -> R.string.upcoming_option
    TasksScreenOption.Skipped -> R.string.skipped_option
}

fun TasksScreenOption.toUiIcon(): Int = when (this) {
    TasksScreenOption.All_tasks -> R.drawable.all_tasks_option
    TasksScreenOption.Today -> R.drawable.today_tasks_option
    TasksScreenOption.Upcoming -> R.drawable.upcoming_tasks_option
    TasksScreenOption.Skipped -> R.drawable.skipped_tasks_option
}