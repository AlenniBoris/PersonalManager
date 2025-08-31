package com.alenniboris.personalmanager.presentation.screens.weather.views

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.weather.DayWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.HourWeatherForecastModelDomain
import com.alenniboris.personalmanager.domain.model.weather.WindDirection
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.presentation.model.DayWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.HourWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.UserModelUi
import com.alenniboris.personalmanager.presentation.screens.weather.IWeatherScreenEvent
import com.alenniboris.personalmanager.presentation.screens.weather.IWeatherScreenIntent
import com.alenniboris.personalmanager.presentation.screens.weather.WeatherScreenOptions
import com.alenniboris.personalmanager.presentation.screens.weather.WeatherScreenState
import com.alenniboris.personalmanager.presentation.screens.weather.WeatherScreenViewModel
import com.alenniboris.personalmanager.presentation.screens.weather.toUiString
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.currentThemeMode
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockShape
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCardColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenColumnInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWeatherCardInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.values.WeatherScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.views.AppBottomSheet
import com.alenniboris.personalmanager.presentation.uikit.views.AppButtonRow
import com.alenniboris.personalmanager.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Destination(route = WeatherScreenRoute)
@Composable
@RequiresApi(Build.VERSION_CODES.O)
fun WeatherScreen(
    navigator: DestinationsNavigator
) {

    val viewModel: WeatherScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IWeatherScreenEvent.ShowToast>().collect { coming ->
                LogPrinter.printLog("!!!", context.getString(coming.messageId))
            }
        }
    }

    WeatherScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun WeatherScreenUi(
    state: WeatherScreenState = WeatherScreenState(),
    proceedIntent: (IWeatherScreenIntent) -> Unit = {}
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor),
    ) {

        AppTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appMainTextColor)
                .padding(topBarInnerPadding),
            textColor = appColor,
            headerTextString = stringResource(R.string.app_name),
            subtleText = stringResource(R.string.hello_text) + state.user?.name,
            secondButtonPainter = painterResource(R.drawable.settings_icon),
            onSecondClicked = {},
            thirdButtonPainter = painterResource(R.drawable.person_icon),
            onThirdClicked = {}
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(weatherScreenColumnInnerPadding)
        ) {

            CurrentWeatherCard(
                modifier = Modifier
                    .padding(weatherScreenBlockOuterPadding)
                    .clip(weatherScreenBlockShape)
                    .fillMaxWidth()
                    .background(weatherScreenCardColor)
                    .padding(weatherScreenWeatherCardInnerPadding),
                locationText = state.currentWeatherForecast.locationText
                    ?: stringResource(R.string.no_text_placeholder),
                temperatureText = state.currentWeatherForecast.temperatureText
                    ?: stringResource(R.string.no_text_placeholder),
                weatherPicture = painterResource(
                    state.currentWeatherForecast.weatherPictureId
                ),
                isLoading = state.isCurrentForecastLoading
            )

            AppButtonRow(
                modifier = Modifier
                    .padding(weatherScreenBlockOuterPadding)
                    .clip(weatherScreenBlockShape)
                    .fillMaxWidth()
                    .background(buttonRowBackgroundColor)
                    .padding(appButtonRowInnerPadding),
                currentElement = ClickableElement(
                    text = stringResource(state.currentOption.toUiString()),
                    onClick = {}
                ),
                listOfElements = state.listOfOptions.map {
                    ClickableElement(
                        text = stringResource(it.toUiString()),
                        onClick = {
                            proceedIntent(
                                IWeatherScreenIntent.ChangeViewedOption(it)
                            )
                        }
                    )
                }
            )

            when (state.currentOption) {
                WeatherScreenOptions.Hourly -> {
                    Column(
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {

                        HourForecastUi(
                            hourForecastList = state.hourWeatherForecast,
                            isHourForecastLoading = state.isHourForecastLoading,
                            currentForecast = state.currentWeatherForecast,
                            isCurrentForecastLoading = state.isCurrentForecastLoading,
                            proceedIntent = proceedIntent
                        )
                    }
                }

                WeatherScreenOptions.Weekly -> {
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
                        isLoading = state.isWeekForecastLoading,
                        weekForecastList = state.weekWeatherForecast,
                        proceedIntent = proceedIntent
                    )
                }
            }
        }

        state.selectedHourForecast?.let {
            AppBottomSheet(
                onDismiss = {
                    proceedIntent(
                        IWeatherScreenIntent.UpdateSelectedHour(null)
                    )
                },
                content = {
                    HourForecastSheetUi(
                        modifier = Modifier
                            .padding(weatherScreenColumnInnerPadding),
                        hourForecast = it
                    )
                }
            )
        }

        state.selectedDayForecast?.let {
            AppBottomSheet(
                onDismiss = {
                    proceedIntent(
                        IWeatherScreenIntent.UpdateSelectedDay(null)
                    )
                },
                content = {
                    DayForecastSheetUi(
                        modifier = Modifier
                            .padding(weatherScreenColumnInnerPadding),
                        dayForecast = it
                    )
                }
            )
        }
    }
}

@Composable
@Preview
@RequiresApi(Build.VERSION_CODES.O)
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            WeatherScreenUi(
                state = WeatherScreenState(
                    user = UserModelUi(name = "bor"),
                    hourWeatherForecast = listOf(
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
                    weekWeatherForecast = listOf(
                        DayWeatherForecastModelUi(
                            domainModel = DayWeatherForecastModelDomain(
                                dayDate = java.util.Calendar.getInstance().time,
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
                        )
                    ),
                    currentOption = WeatherScreenOptions.Weekly
                ),
                proceedIntent = {}
            )
        }
    }
}

@Composable
@Preview
@RequiresApi(Build.VERSION_CODES.O)
private fun DarkTheme() {
    PersonalManagerTheme(
        darkTheme = true
    ) {
        Surface {
            WeatherScreenUi(
                state = WeatherScreenState(
                    user = UserModelUi(name = "bor"),
                    hourWeatherForecast = listOf(
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
                    weekWeatherForecast = listOf(
                        DayWeatherForecastModelUi(
                            domainModel = DayWeatherForecastModelDomain(
                                dayDate = java.util.Calendar.getInstance().time,
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
                        )
                    ),
                    currentOption = WeatherScreenOptions.Hourly
                ),
                proceedIntent = {}
            )
        }
    }
}