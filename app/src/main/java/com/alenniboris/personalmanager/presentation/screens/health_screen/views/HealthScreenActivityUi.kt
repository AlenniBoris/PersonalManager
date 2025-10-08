package com.alenniboris.personalmanager.presentation.screens.health_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.presentation.model.heart.HeartRateModelUi
import com.alenniboris.personalmanager.presentation.screens.health_screen.IHealthScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDateFilterInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenPlotHeight
import com.alenniboris.personalmanager.presentation.uikit.views.AppAnimatedPlot
import com.alenniboris.personalmanager.presentation.uikit.views.AppDatePicker
import com.alenniboris.personalmanager.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.personalmanager.presentation.uikit.views.AppRefreshIndicator
import com.alenniboris.personalmanager.presentation.uikit.views.AppSingleLineDateFilter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreenActivityUi(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isHeartRateDatePickerVisible: Boolean,
    heartRates: List<HeartRateModelUi>,
    heartRateChartDateText: String,
    heartRateChartDateDayText: Int,
    isRefreshing: Boolean,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {

    if (isHeartRateDatePickerVisible) {
        AppDatePicker(
            onDismiss = {
                proceedIntent(
                    IHealthScreenIntent.UpdateHeartRateDatePickerVisibility
                )
            },
            onSelected = { date ->
                proceedIntent(
                    IHealthScreenIntent.UpdateHeartRateChartDate(date)
                )
            }
        )
    }

    val refreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = {
            proceedIntent(
                IHealthScreenIntent.RefreshActivityUiData
            )
        },
        state = refreshState,
        indicator = {
            AppRefreshIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                state = refreshState,
                isRefreshing = isRefreshing
            )
        },
        content = {
            ScreenContent(
                isLoading = isLoading,
                heartRates = heartRates,
                heartRateChartDateText = heartRateChartDateText,
                heartRateChartDateDayText = heartRateChartDateDayText,
                proceedIntent = proceedIntent
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    isLoading: Boolean,
    heartRates: List<HeartRateModelUi>,
    heartRateChartDateText: String,
    heartRateChartDateDayText: Int,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        AppSingleLineDateFilter(
            modifier = Modifier
                .fillMaxWidth()
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(appDateFilterInnerPadding),
            selectedFilterDateText = heartRateChartDateText,
            selectedFilterDateDayTextId = heartRateChartDateDayText,
            onPickerVisibilityChange = {
                proceedIntent(
                    IHealthScreenIntent.UpdateHeartRateDatePickerVisibility
                )
            },
            onCancel = {
                proceedIntent(
                    IHealthScreenIntent.UpdateHeartRateChartDateToDefault
                )
            }
        )

        HeartRatePlotSection(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .fillMaxWidth()
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(appDateFilterInnerPadding),
            heartRates = heartRates,
            isLoading = isLoading
        )
    }
}

@Composable
private fun HeartRatePlotSection(
    modifier: Modifier,
    heartRates: List<HeartRateModelUi>,
    isLoading: Boolean
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.heart_rate_trend_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize
            )
        )

        Text(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding),
            text = stringResource(R.string.heart_rate_trend_description),
            style = bodyStyle.copy(
                color = appSubtleTextColor,
                fontSize = appTextSizeSmall
            )
        )

        if (heartRates.isEmpty()) {
            AppEmptyScreen(
                modifier = Modifier
                    .padding(healthScreenContentItemTopPadding)
                    .fillMaxSize()
                    .height(healthScreenPlotHeight)
            )
        } else {
            AppAnimatedPlot(
                modifier = Modifier
                    .padding(healthScreenContentItemTopPadding)
                    .fillMaxWidth()
                    .height(healthScreenPlotHeight),
                weights = heartRates,
                isLoading = isLoading
            )
        }
    }
}

@Preview
@Composable
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
                HealthScreenActivityUi(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = false,
                    isHeartRateDatePickerVisible = false,
                    heartRates = listOf(
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 122,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 89,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 215,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 90,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    heartRateChartDateText = "12/12/12",
                    heartRateChartDateDayText = R.string.time_monday_text,
                    isRefreshing = false,
                    proceedIntent = {}
                )
            }
        }
    }
}

@Preview
@Composable
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
                HealthScreenActivityUi(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = false,
                    isHeartRateDatePickerVisible = false,
                    heartRates = listOf(
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 122,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 89,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 215,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 90,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    heartRateChartDateText = "12/12/12",
                    heartRateChartDateDayText = R.string.time_monday_text,
                    isRefreshing = false,
                    proceedIntent = {}
                )
            }
        }
    }
}