package com.alenniboris.personalmanager.presentation.screens.weather.views

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.WindDirection
import com.alenniboris.personalmanager.presentation.model.weather.HourWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appFlowRowHorizontalSpacing
import com.alenniboris.personalmanager.presentation.uikit.theme.appFlowRowVerticalSpacing
import com.alenniboris.personalmanager.presentation.uikit.theme.appInfoBlockMinHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeBig
import com.alenniboris.personalmanager.presentation.uikit.theme.filterSheetTextStartPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.forecastSheetIconVerticalPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.forecastSheetTextDoubleVerticalPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.forecastSheetTextVerticalPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenColumnInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appInfoBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenHumidityColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenIconSizeBig
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenUvIndexColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWaterColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWindDirectionColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWindGrayColor
import com.alenniboris.personalmanager.presentation.uikit.views.AppDetailsInfoBlock

@Composable
fun HourForecastSheetUi(
    modifier: Modifier = Modifier,
    hourForecast: HourWeatherForecastModelUi
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.align(Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(hourForecast.weatherPictureId),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(filterSheetTextStartPadding),
                text = stringResource(R.string.forecast_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Text(
            modifier = Modifier
                .padding(forecastSheetTextVerticalPadding)
                .align(Alignment.Start),
            text =
                stringResource(R.string.detailed_weather_info_text) + " " + hourForecast.timeText,
            style = bodyStyle.copy(
                color = appSubtleTextColor,
                fontSize = appTextSize
            )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                modifier = Modifier
                    .padding(forecastSheetIconVerticalPadding)
                    .size(weatherScreenIconSizeBig),
                painter = painterResource(hourForecast.weatherPictureId),
                tint = appSubtleTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                modifier = Modifier.padding(forecastSheetTextDoubleVerticalPadding),
                text = hourForecast.temperatureText ?: stringResource(R.string.no_text_placeholder),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSizeBig
                )
            )

            Text(
                modifier = Modifier.padding(forecastSheetTextVerticalPadding),
                text = stringResource(R.string.felt_temperature_text) + " " + hourForecast.feltTemperatureText,
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )
        }

        FlowRow(
            modifier = Modifier
                .padding(forecastSheetIconVerticalPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(appFlowRowHorizontalSpacing),
            verticalArrangement = Arrangement.spacedBy(appFlowRowVerticalSpacing)
        ) {

            AppDetailsInfoBlock(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .width(IntrinsicSize.Max)
                    .weight(1f)
                    .heightIn(appInfoBlockMinHeight)
                    .padding(appInfoBlockInnerPadding),
                sectionIcon = painterResource(R.drawable.water_drop_icon),
                iconTint = weatherScreenWaterColor,
                sectionHeader = stringResource(R.string.precipitation_section_text),
                sectionValue = hourForecast.precipitationProbabilityText
                    ?: stringResource(R.string.zero_percent_text)
            )

            AppDetailsInfoBlock(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .width(IntrinsicSize.Max)
                    .weight(1f)
                    .heightIn(appInfoBlockMinHeight)
                    .padding(appInfoBlockInnerPadding),
                sectionIcon = painterResource(R.drawable.water_drop_icon),
                iconTint = weatherScreenHumidityColor,
                sectionHeader = stringResource(R.string.humidity_section_text),
                sectionValue = hourForecast.humidityText
                    ?: stringResource(R.string.no_text_placeholder)
            )

            AppDetailsInfoBlock(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .width(IntrinsicSize.Max)
                    .weight(1f)
                    .heightIn(appInfoBlockMinHeight)
                    .padding(appInfoBlockInnerPadding),
                sectionIcon = painterResource(R.drawable.wind_speed_icon),
                iconTint = weatherScreenWindGrayColor,
                sectionHeader = stringResource(R.string.wind_speed_section_text),
                sectionValue = hourForecast.windSpeedText?.let {
                    it + " " + stringResource(R.string.meters_in_second_text)
                } ?: stringResource(R.string.no_text_placeholder)
            )

            AppDetailsInfoBlock(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .width(IntrinsicSize.Max)
                    .weight(1f)
                    .heightIn(appInfoBlockMinHeight)
                    .padding(appInfoBlockInnerPadding),
                sectionIcon = painterResource(R.drawable.wind_direction_icon),
                iconTint = weatherScreenWindDirectionColor,
                sectionHeader = stringResource(R.string.wind_direction_section_text),
                sectionValue = stringResource(
                    hourForecast.windDirectionTextId ?: R.string.no_text_placeholder
                )
            )

            AppDetailsInfoBlock(
                modifier = Modifier
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .width(IntrinsicSize.Max)
                    .weight(1f)
                    .heightIn(appInfoBlockMinHeight)
                    .padding(appInfoBlockInnerPadding),
                sectionIcon = painterResource(R.drawable.sunny_weather_icon),
                iconTint = weatherScreenUvIndexColor,
                sectionHeader = stringResource(R.string.uv_index_section_text),
                sectionValue = hourForecast.uvIndexText
            )
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
            ) {
                HourForecastSheetUi(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(weatherScreenColumnInnerPadding),
                    hourForecast = HourWeatherForecastModelUi(
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
            ) {
                HourForecastSheetUi(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(weatherScreenColumnInnerPadding),
                    hourForecast = HourWeatherForecastModelUi(
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
                )
            }
        }
    }
}