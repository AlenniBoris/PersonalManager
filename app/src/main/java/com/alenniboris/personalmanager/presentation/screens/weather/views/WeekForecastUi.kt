package com.alenniboris.personalmanager.presentation.screens.weather.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.weather.DayWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.WindDirection
import com.alenniboris.personalmanager.presentation.model.DayWeatherForecastModelUi
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
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWeekForecastItemOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWeekForecastItemTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.zeroPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekForecastUi(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    weekForecastList: List<DayWeatherForecastModelUi>,
    proceedIntent: (IWeatherScreenIntent) -> Unit
) {
    if (isLoading) {
        AppProgressAnimation(
            modifier = modifier,
            color = appMainTextColor
        )
    } else {
        LazyColumn(
            modifier = modifier
        ) {
            itemsIndexed(weekForecastList) { index, dayForecast ->
                DayForecastUi(
                    modifier = Modifier
                        .padding(
                            if (index == 0) zeroPadding
                            else weatherScreenWeekForecastItemOuterPadding
                        )
                        .fillMaxWidth()
                        .clickable {
                            proceedIntent(
                                IWeatherScreenIntent.UpdateSelectedDay(dayForecast)
                            )
                        },
                    dayForecast = dayForecast
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DayForecastUi(
    modifier: Modifier = Modifier,
    dayForecast: DayWeatherForecastModelUi
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row {
            Icon(
                painter = painterResource(dayForecast.weatherPictureId),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(weatherScreenWeekForecastItemTextPadding),
                text = dayForecast.timeText ?: stringResource(R.string.no_text_placeholder),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = weatherScreenTextSize
                )
            )

            Text(
                modifier = Modifier.padding(weatherScreenWeekForecastItemTextPadding),
                text = stringResource(dayForecast.dayName),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = weatherScreenTextSize
                )
            )
        }

        Row {
            Text(
                text = dayForecast.temperatureMaxText
                    ?: stringResource(R.string.no_text_placeholder),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = weatherScreenTextSize
                )
            )
            Text(
                modifier = Modifier.padding(weatherScreenWeekForecastItemTextPadding),
                text = dayForecast.temperatureMinText
                    ?: stringResource(R.string.no_text_placeholder),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = weatherScreenTextSize
                )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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

                WeekForecastUi(
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
                    isLoading = false,
                    weekForecastList = listOf(
                        DayWeatherForecastModelUi(
                            domainModel = DayWeatherForecastModelDomain(
                                dayDate = Calendar.getInstance().time,
                                temperatureMin = -12.0,
                                temperatureMax = 12.0,
                                feltTemperatureMin = -10.0,
                                feltTemperatureMax = 15.2,
                                relativeHumidity = 10.2,
                                uvIndex = 3.0,
                                precipitationProbability = 12.0,
                                precipitation = 1.0,
                                windDirection = WindDirection.SouthEast,
                                windSpeed = 123.9
                            )
                        ),
                        DayWeatherForecastModelUi(
                            domainModel = DayWeatherForecastModelDomain(
                                dayDate = Calendar.getInstance().time,
                                temperatureMin = -12.0,
                                temperatureMax = 12.0,
                                feltTemperatureMin = -10.0,
                                feltTemperatureMax = 15.2,
                                relativeHumidity = 10.2,
                                uvIndex = 3.0,
                                precipitationProbability = 0.0,
                                precipitation = 0.0,
                                windDirection = WindDirection.SouthEast,
                                windSpeed = 123.9
                            )
                        ),
                        DayWeatherForecastModelUi(
                            domainModel = DayWeatherForecastModelDomain(
                                dayDate = Calendar.getInstance().time,
                                temperatureMin = -12.0,
                                temperatureMax = 12.0,
                                feltTemperatureMin = -10.0,
                                feltTemperatureMax = 15.2,
                                relativeHumidity = 10.2,
                                uvIndex = 3.0,
                                precipitationProbability = 0.0,
                                precipitation = 0.0,
                                windDirection = WindDirection.SouthEast,
                                windSpeed = 123.9
                            )
                        )
                    ),
                    proceedIntent = {}
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
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

                WeekForecastUi(
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
                    isLoading = false,
                    weekForecastList = listOf(
                        DayWeatherForecastModelUi(
                            domainModel = DayWeatherForecastModelDomain(
                                dayDate = Calendar.getInstance().time,
                                temperatureMin = -12.0,
                                temperatureMax = 12.0,
                                feltTemperatureMin = -10.0,
                                feltTemperatureMax = 15.2,
                                relativeHumidity = 10.2,
                                uvIndex = 3.0,
                                precipitationProbability = 12.0,
                                precipitation = 1.0,
                                windDirection = WindDirection.SouthEast,
                                windSpeed = 123.9
                            )
                        ),
                        DayWeatherForecastModelUi(
                            domainModel = DayWeatherForecastModelDomain(
                                dayDate = Calendar.getInstance().time,
                                temperatureMin = -12.0,
                                temperatureMax = 12.0,
                                feltTemperatureMin = -10.0,
                                feltTemperatureMax = 15.2,
                                relativeHumidity = 10.2,
                                uvIndex = 3.0,
                                precipitationProbability = 0.0,
                                precipitation = 0.0,
                                windDirection = WindDirection.SouthEast,
                                windSpeed = 123.9
                            )
                        ),
                        DayWeatherForecastModelUi(
                            domainModel = DayWeatherForecastModelDomain(
                                dayDate = Calendar.getInstance().time,
                                temperatureMin = -12.0,
                                temperatureMax = 12.0,
                                feltTemperatureMin = -10.0,
                                feltTemperatureMax = 15.2,
                                relativeHumidity = 10.2,
                                uvIndex = 3.0,
                                precipitationProbability = 0.0,
                                precipitation = 0.0,
                                windDirection = WindDirection.SouthEast,
                                windSpeed = 123.9
                            )
                        )
                    ),
                    proceedIntent = {}
                )
            }
        }
    }
}