package com.alenniboris.personalmanager.presentation.screens.weather.views

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.weather.CurrentWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.WindDirection
import com.alenniboris.personalmanager.presentation.model.CurrentWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.HourWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.screens.weather.IWeatherScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockShape
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenColumnInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockLeftPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockRightPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenHourForecastItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenHourForecastPrecipitationProbPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenHourForecastTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenSunriseColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenSunsetColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenUvIndexColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenVisibilityColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWaterColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenIconSizeBig
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWindColor
import com.alenniboris.personalmanager.presentation.uikit.theme.zeroPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation

@Composable
fun HourForecastUi(
    hourForecastList: List<HourWeatherForecastModelUi>,
    isHourForecastLoading: Boolean,
    currentForecast: CurrentWeatherForecastModelUi,
    isCurrentForecastLoading: Boolean,
    proceedIntent: (IWeatherScreenIntent) -> Unit
) {

    HourForecastListUi(
        modifier = Modifier
            .padding(weatherScreenBlockOuterPadding)
            .clip(weatherScreenBlockShape)
            .fillMaxWidth()
            .border(
                width = weatherScreenBlockBorderWidth,
                color = appSubtleTextColor,
                shape = weatherScreenBlockShape
            )
            .padding(weatherScreenBlockInnerPadding),
        hourForecastList = hourForecastList,
        isHourForecastLoading = isHourForecastLoading,
        proceedIntent = proceedIntent
    )

    Row(
        modifier = Modifier
            .padding(weatherScreenBlockOuterPadding)
            .fillMaxWidth()
    ) {

        ForecastDetailsInfoBlock(
            modifier = Modifier
                .padding(weatherScreenCurrentForecastBlockLeftPadding)
                .clip(weatherScreenBlockShape)
                .border(
                    width = weatherScreenBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = weatherScreenBlockShape
                )
                .weight(1f)
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            sectionIcon = painterResource(R.drawable.water_drop_icon),
            iconTint = weatherScreenWaterColor,
            sectionHeader = stringResource(R.string.humidity_section_text),
            sectionValue = currentForecast.humidityText
                ?: stringResource(R.string.no_text_placeholder),
            isLoading = isCurrentForecastLoading
        )

        ForecastDetailsInfoBlock(
            modifier = Modifier
                .padding(weatherScreenCurrentForecastBlockRightPadding)
                .clip(weatherScreenBlockShape)
                .border(
                    width = weatherScreenBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = weatherScreenBlockShape
                )
                .weight(1f)
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            sectionIcon = painterResource(R.drawable.wind_speed_icon),
            iconTint = weatherScreenWindColor,
            sectionHeader = stringResource(R.string.wind_speed_section_text),
            sectionValue = currentForecast.windSpeedText
                ?: stringResource(R.string.no_text_placeholder),
            isLoading = isCurrentForecastLoading
        )
    }

    Row(
        modifier = Modifier
            .padding(weatherScreenBlockOuterPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        ForecastDetailsInfoBlock(
            modifier = Modifier
                .padding(weatherScreenCurrentForecastBlockLeftPadding)
                .clip(weatherScreenBlockShape)
                .border(
                    width = weatherScreenBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = weatherScreenBlockShape
                )
                .weight(1f)
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            sectionIcon = painterResource(R.drawable.visivility_icon),
            iconTint = weatherScreenVisibilityColor,
            sectionHeader = stringResource(R.string.visibility_section_text),
            sectionValue = currentForecast.visibilityText
                ?: stringResource(R.string.no_text_placeholder),
            isLoading = isCurrentForecastLoading
        )

        ForecastDetailsInfoBlock(
            modifier = Modifier
                .padding(weatherScreenCurrentForecastBlockRightPadding)
                .clip(weatherScreenBlockShape)
                .border(
                    width = weatherScreenBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = weatherScreenBlockShape
                )
                .weight(1f)
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            sectionIcon = painterResource(R.drawable.sunny_weather_icon),
            iconTint = weatherScreenUvIndexColor,
            sectionHeader = stringResource(R.string.uv_index_section_text),
            sectionValue = currentForecast.uvIndexText,
            isLoading = isCurrentForecastLoading
        )
    }

    Row(
        modifier = Modifier
            .padding(weatherScreenBlockOuterPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        ForecastDetailsInfoBlock(
            modifier = Modifier
                .padding(weatherScreenCurrentForecastBlockLeftPadding)
                .clip(weatherScreenBlockShape)
                .border(
                    width = weatherScreenBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = weatherScreenBlockShape
                )
                .weight(1f)
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            sectionIcon = painterResource(R.drawable.sunrise_icon),
            iconTint = weatherScreenSunriseColor,
            sectionHeader = stringResource(R.string.sunrise_section_text),
            sectionValue = currentForecast.sunriseText
                ?: stringResource(R.string.no_text_placeholder),
            isLoading = isCurrentForecastLoading
        )

        ForecastDetailsInfoBlock(
            modifier = Modifier
                .padding(weatherScreenCurrentForecastBlockRightPadding)
                .clip(weatherScreenBlockShape)
                .border(
                    width = weatherScreenBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = weatherScreenBlockShape
                )
                .weight(1f)
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            sectionIcon = painterResource(R.drawable.sunset_icon),
            iconTint = weatherScreenSunsetColor,
            sectionHeader = stringResource(R.string.sunset_section_text),
            sectionValue = currentForecast.sunsetText
                ?: stringResource(R.string.no_text_placeholder),
            isLoading = isCurrentForecastLoading
        )
    }
}

@Composable
private fun HourForecastListUi(
    modifier: Modifier = Modifier,
    hourForecastList: List<HourWeatherForecastModelUi>,
    isHourForecastLoading: Boolean,
    proceedIntent: (IWeatherScreenIntent) -> Unit
) {

    if (isHourForecastLoading) {
        Box(modifier = modifier) {
            AppProgressAnimation(
                modifier = Modifier
                    .size(weatherScreenIconSizeBig)
                    .align(Alignment.Center),
                color = appMainTextColor
            )
        }
    } else {
        LazyRow(
            modifier = modifier
        ) {
            itemsIndexed(hourForecastList) { index, forecast ->
                HourForecastItem(
                    modifier = Modifier
                        .padding(
                            if (index != 0) {
                                weatherScreenHourForecastItemPadding
                            } else {
                                zeroPadding
                            }
                        )
                        .clickable {
                            proceedIntent(
                                IWeatherScreenIntent.UpdateSelectedHour(forecast)
                            )
                        },
                    forecast = forecast
                )
            }
        }
    }
}

@Composable
private fun HourForecastItem(
    modifier: Modifier = Modifier,
    forecast: HourWeatherForecastModelUi
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = forecast.timeText ?: stringResource(R.string.no_text_placeholder),
            style = bodyStyle.copy(
                color = appSubtleTextColor,
                fontSize = weatherScreenTextSize
            )
        )

        Icon(
            modifier = Modifier.padding(weatherScreenHourForecastTextPadding),
            painter = painterResource(forecast.weatherPictureId),
            tint = appSubtleTextColor,
            contentDescription = stringResource(R.string.picture_description)
        )

        Text(
            modifier = Modifier.padding(weatherScreenHourForecastTextPadding),
            text = forecast.temperatureText ?: stringResource(R.string.no_text_placeholder),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = weatherScreenTextSize
            )
        )

        forecast.precipitationProbabilityText?.let { probability ->
            Row(
                modifier = Modifier.padding(weatherScreenHourForecastTextPadding)
            ) {

                Icon(
                    painter = painterResource(R.drawable.water_drop_icon),
                    tint = weatherScreenWaterColor,
                    contentDescription = stringResource(R.string.picture_description)
                )

                Text(
                    modifier = Modifier.padding(
                        weatherScreenHourForecastPrecipitationProbPadding
                    ),
                    text = probability,
                    style = bodyStyle.copy(
                        fontSize = weatherScreenTextSize,
                        color = weatherScreenWaterColor
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
                    .background(appColor)
                    .padding(weatherScreenColumnInnerPadding)
            ) {
                HourForecastUi(
                    hourForecastList = listOf(
                        HourWeatherForecastModelUi(
                            domainModel = HourWeatherForecastModelDomain(
                                windSpeed = 1.2,
                                temperature = 12.0,
                                precipitationProbability = 3.0,
                                precipitation = 12.1,
                                feltTemperature = 12.3,
                                isDayLight = true,
                                uvIndex = 15.1,
                                relativeHumidity = 43.1,
                                windDirection = WindDirection.SouthEast,
                                hourTime = Calendar.getInstance().time
                            )
                        ),
                        HourWeatherForecastModelUi(
                            domainModel = HourWeatherForecastModelDomain(
                                windSpeed = 1.2,
                                temperature = 12.0,
                                precipitationProbability = 3.0,
                                precipitation = 12.1,
                                feltTemperature = 12.3,
                                isDayLight = false,
                                uvIndex = 15.1,
                                relativeHumidity = 43.1,
                                windDirection = WindDirection.North,
                                hourTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    currentForecast = CurrentWeatherForecastModelUi(
                        domainModel = CurrentWeatherForecastModelDomain(
                            temperature = 10.0,
                            windSpeed = 12.2,
                            windDirection = WindDirection.SouthEast,
                            visibilityDistance = 1323.2,
                            sunsetTime = "12",
                            sunriseTime = "19",
                            uvIndex = 1.0,
                            relativeHumidity = 12.4,
                            precipitation = 1.0,
                            precipitationProbability = 60.0,
                            place = "minsd"
                        )
                    ),
                    isCurrentForecastLoading = false,
                    proceedIntent = {},
                    isHourForecastLoading = false
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
                    .padding(weatherScreenColumnInnerPadding)
            ) {
                HourForecastUi(
                    hourForecastList = listOf(
                        HourWeatherForecastModelUi(
                            domainModel = HourWeatherForecastModelDomain(
                                windSpeed = 1.2,
                                temperature = 12.0,
                                precipitationProbability = 3.0,
                                precipitation = 12.1,
                                feltTemperature = 12.3,
                                isDayLight = true,
                                uvIndex = 15.1,
                                relativeHumidity = 43.1,
                                windDirection = WindDirection.SouthEast,
                                hourTime = Calendar.getInstance().time
                            )
                        ),
                        HourWeatherForecastModelUi(
                            domainModel = HourWeatherForecastModelDomain(
                                windSpeed = 1.2,
                                temperature = 12.0,
                                precipitationProbability = 3.0,
                                precipitation = 12.1,
                                feltTemperature = 12.3,
                                isDayLight = false,
                                uvIndex = 15.1,
                                relativeHumidity = 43.1,
                                windDirection = WindDirection.North,
                                hourTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    currentForecast = CurrentWeatherForecastModelUi(
                        domainModel = CurrentWeatherForecastModelDomain(
                            temperature = 10.0,
                            windSpeed = 12.2,
                            windDirection = WindDirection.SouthEast,
                            visibilityDistance = 1323.2,
                            sunsetTime = "12",
                            sunriseTime = "19",
                            uvIndex = 1.0,
                            relativeHumidity = 12.4,
                            precipitation = 1.0,
                            precipitationProbability = 60.0,
                            place = "minsd"
                        )
                    ),
                    isCurrentForecastLoading = false,
                    proceedIntent = {},
                    isHourForecastLoading = true
                )
            }
        }
    }
}