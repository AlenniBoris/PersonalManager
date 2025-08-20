package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.bottomBarActiveButtonColor
import com.alenniboris.personalmanager.presentation.uikit.theme.bottomBarButtonInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.bottomBarButtonShape
import com.alenniboris.personalmanager.presentation.uikit.theme.bottomBarButtonTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.bottomBarInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.values.BottomBarModelUi
import com.alenniboris.personalmanager.presentation.uikit.values.BottomBarValues
import com.alenniboris.personalmanager.presentation.uikit.values.HealthScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.values.HomeScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.values.TasksScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.values.WeatherScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.values.toModelUi

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    items: List<BottomBarModelUi>,
    currentRoute: String
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        items.forEach { item ->

            val backgroundColor by animateColorAsState(
                targetValue = if (item.route == currentRoute) bottomBarActiveButtonColor else appColor,
                label = "bottom bar background color",
                animationSpec = tween(500)
            )

            BottomBarItem(
                modifier = Modifier
                    .clip(bottomBarButtonShape)
                    .background(backgroundColor)
                    .padding(bottomBarButtonInnerPadding)
                    .clickable { item::onClick },
                item = item,
                currentRoute = currentRoute
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    modifier: Modifier = Modifier,
    item: BottomBarModelUi,
    currentRoute: String
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(item.iconId),
            tint = if (currentRoute == item.route) appMainTextColor else appSubtleTextColor,
            contentDescription = stringResource(item.textId)
        )

        Text(
            modifier = Modifier.padding(bottomBarButtonTextPadding),
            text = stringResource(item.textId),
            color = if (currentRoute == item.route) appMainTextColor else appSubtleTextColor
        )
    }
}

@Composable
@Preview
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {

                AppBottomBar(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .background(appColor)
                        .padding(bottomBarInnerPadding),
                    items = BottomBarValues.entries.toList().map { it.toModelUi() },
                    currentRoute = HomeScreenRoute
                )

                AppBottomBar(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .background(appColor)
                        .padding(bottomBarInnerPadding),
                    items = BottomBarValues.entries.toList().map { it.toModelUi() },
                    currentRoute = WeatherScreenRoute
                )

                AppBottomBar(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .background(appColor)
                        .padding(bottomBarInnerPadding),
                    items = BottomBarValues.entries.toList().map { it.toModelUi() },
                    currentRoute = TasksScreenRoute
                )

                AppBottomBar(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .background(appColor)
                        .padding(bottomBarInnerPadding),
                    items = BottomBarValues.entries.toList().map { it.toModelUi() },
                    currentRoute = HealthScreenRoute
                )
            }
        }
    }
}

@Composable
@Preview
private fun DarkTheme() {
    PersonalManagerTheme(
        darkTheme = true
    ) {
        Surface() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {

                AppBottomBar(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .background(appColor)
                        .padding(bottomBarInnerPadding),
                    items = BottomBarValues.entries.toList().map { it.toModelUi() },
                    currentRoute = HomeScreenRoute
                )
            }
        }
    }
}