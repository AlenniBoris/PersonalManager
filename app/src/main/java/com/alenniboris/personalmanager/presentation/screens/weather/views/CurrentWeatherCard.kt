package com.alenniboris.personalmanager.presentation.screens.weather.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockShape
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCardColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCardTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenIconSizeBig
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWeatherCardInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWeatherCardTextPaddingValues
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWeatherCardTextRowPaddingValues
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenTextSizeBig
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation


@Composable
fun CurrentWeatherCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    locationText: String,
    temperatureText: String,
    weatherPicture: Painter
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        if (isLoading) {
            AppProgressAnimation(
                modifier = Modifier.size(weatherScreenIconSizeBig),
                color = weatherScreenCardTextColor
            )
        } else {
            Text(
                text = locationText,
                style = bodyStyle.copy(
                    color = weatherScreenCardTextColor,
                    fontSize = weatherScreenTextSize
                )
            )

            Row(
                modifier = Modifier.padding(weatherScreenWeatherCardTextRowPaddingValues),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    modifier = Modifier.size(weatherScreenIconSizeBig),
                    painter = weatherPicture,
                    tint = weatherScreenCardTextColor,
                    contentDescription = stringResource(R.string.picture_decription)
                )

                Text(
                    modifier = Modifier.padding(weatherScreenWeatherCardTextPaddingValues),
                    text = temperatureText,
                    style = bodyStyle.copy(
                        color = weatherScreenCardTextColor,
                        fontSize = weatherScreenTextSizeBig
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .background(appColor)
            ) {
                CurrentWeatherCard(
                    modifier = Modifier
                        .padding(weatherScreenBlockOuterPadding)
                        .clip(weatherScreenBlockShape)
                        .fillMaxWidth()
                        .background(weatherScreenCardColor)
                        .padding(weatherScreenWeatherCardInnerPadding),
                    locationText = "Minsk",
                    temperatureText = "232",
                    weatherPicture = painterResource(R.drawable.sunny_weather_icon),
                    isLoading = false
                )

                CurrentWeatherCard(
                    modifier = Modifier
                        .padding(weatherScreenBlockOuterPadding)
                        .clip(weatherScreenBlockShape)
                        .fillMaxWidth()
                        .background(weatherScreenCardColor)
                        .padding(weatherScreenWeatherCardInnerPadding),
                    locationText = "Minsk",
                    temperatureText = "232",
                    weatherPicture = painterResource(R.drawable.sunny_weather_icon),
                    isLoading = true
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
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(horizontal = 20.dp)
            ) {
                CurrentWeatherCard(
                    modifier = Modifier
                        .padding(weatherScreenBlockOuterPadding)
                        .clip(weatherScreenBlockShape)
                        .fillMaxWidth()
                        .background(weatherScreenCardColor)
                        .padding(weatherScreenWeatherCardInnerPadding),
                    locationText = "Minsk",
                    temperatureText = "232",
                    weatherPicture = painterResource(R.drawable.sunny_weather_icon),
                    isLoading = false
                )

                CurrentWeatherCard(
                    modifier = Modifier
                        .padding(weatherScreenBlockOuterPadding)
                        .clip(weatherScreenBlockShape)
                        .fillMaxWidth()
                        .background(weatherScreenCardColor)
                        .padding(weatherScreenWeatherCardInnerPadding),
                    locationText = "Minsk",
                    temperatureText = "232",
                    weatherPicture = painterResource(R.drawable.sunny_weather_icon),
                    isLoading = true
                )
            }
        }
    }
}