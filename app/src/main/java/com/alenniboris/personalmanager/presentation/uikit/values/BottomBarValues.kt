package com.alenniboris.personalmanager.presentation.uikit.values

import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.healthButtonPicture
import com.alenniboris.personalmanager.presentation.uikit.theme.homeButtonPicture
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksButtonPicture
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherButtonPicture

data class BottomBarModelUi(
    val onClick: () -> Unit,
    val iconId: Int,
    val textId: Int,
    val route: String
)

enum class BottomBarValues {
    Home,
    Weather,
    Tasks,
    Health
}

fun BottomBarValues.toModelUi(
    onClick: () -> Unit = {}
): BottomBarModelUi = when (this) {
    BottomBarValues.Home ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = homeButtonPicture,
            textId = R.string.home_button_text,
            route = HomeScreenRoute
        )

    BottomBarValues.Weather ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = weatherButtonPicture,
            textId = R.string.weather_button_text,
            route = WeatherScreenRoute
        )

    BottomBarValues.Tasks ->
        BottomBarModelUi(
            onClick = onClick,
            iconId = tasksButtonPicture,
            textId = R.string.tasks_button_text,
            route = TasksScreenRoute
        )

    BottomBarValues.Health -> BottomBarModelUi(
        onClick = onClick,
        iconId = healthButtonPicture,
        textId = R.string.health_button_text,
        route = HealthScreenRoute
    )
}
