package com.alenniboris.personalmanager.presentation.screens.health_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.screens.health_screen.IHealthScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDateFilterInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenPlotHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenWeightChangeTextStartPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weightChangeLossColor
import com.alenniboris.personalmanager.presentation.uikit.views.AppAnimatedPlot
import com.alenniboris.personalmanager.presentation.uikit.views.AppDatePicker
import com.alenniboris.personalmanager.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppRefreshIndicator
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreenWeightUi(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    weights: List<WeightModelUi>,
    weightChartStartDateText: String,
    weightChartStartDateDayText: Int,
    weightChartEndDateText: String,
    weightChartEndDateDayText: Int,
    currentWeightText: String,
    weightChangeText: String,
    weightChangeIcon: Int,
    weightChangeColor: Color,
    isWeightChartStartDatePickerVisible: Boolean,
    isWeightChartEndDatePickerVisible: Boolean,
    isRefreshing: Boolean,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {

    if (isWeightChartStartDatePickerVisible) {
        AppDatePicker(
            onDismiss = {
                proceedIntent(
                    IHealthScreenIntent.UpdateWeightChartStartDatePickerVisibility
                )
            },
            onSelected = { date ->
                proceedIntent(
                    IHealthScreenIntent.UpdateWeightChartStartDate(date)
                )
            }
        )
    }
    if (isWeightChartEndDatePickerVisible) {
        AppDatePicker(
            onDismiss = {
                proceedIntent(
                    IHealthScreenIntent.UpdateWeightChartStartDatePickerVisibility
                )
            },
            onSelected = { date ->
                proceedIntent(
                    IHealthScreenIntent.UpdateWeightChartEndDate(date)
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
                IHealthScreenIntent.RefreshWeightUiData
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
                weights = weights,
                weightChartStartDateText = weightChartStartDateText,
                weightChartStartDateDayText = weightChartStartDateDayText,
                weightChartEndDateText = weightChartEndDateText,
                weightChartEndDateDayText = weightChartEndDateDayText,
                currentWeightText = currentWeightText,
                weightChangeText = weightChangeText,
                weightChangeIcon = weightChangeIcon,
                weightChangeColor = weightChangeColor,
                proceedIntent = proceedIntent
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    isLoading: Boolean,
    weights: List<WeightModelUi>,
    weightChartStartDateText: String,
    weightChartStartDateDayText: Int,
    weightChartEndDateText: String,
    weightChartEndDateDayText: Int,
    currentWeightText: String,
    weightChangeText: String,
    weightChangeIcon: Int,
    weightChangeColor: Color,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        HealthScreenDoubleDateSelector(
            modifier = Modifier
                .fillMaxWidth()
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(appDateFilterInnerPadding),
            selectedFirstFilterDateText = weightChartStartDateText,
            selectedFirstFilterDateDayTextId = weightChartStartDateDayText,
            selectedSecondFilterDateText = weightChartEndDateText,
            selectedSecondFilterDateDayTextId = weightChartEndDateDayText,
            onFirstPickerVisibilityChange = {
                proceedIntent(
                    IHealthScreenIntent.UpdateWeightChartStartDatePickerVisibility
                )
            },
            onFirstCancel = {
                proceedIntent(
                    IHealthScreenIntent.UpdateWeightChartStartDateToDefault
                )
            },
            onSecondPickerVisibilityChange = {
                proceedIntent(
                    IHealthScreenIntent.UpdateWeightChartEndDatePickerVisibility
                )
            },
            onSecondCancel = {
                proceedIntent(
                    IHealthScreenIntent.UpdateWeightChartEndDateToDefault
                )
            }
        )


        WeightPlotSection(
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
            weights = weights,
            isLoading = isLoading
        )

        WeightChangeSection(
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
            currentWeightText = currentWeightText,
            weightChangeText = weightChangeText,
            weightChangeIcon = weightChangeIcon,
            weightChangeColor = weightChangeColor,
            isLoading = isLoading
        )
    }
}

@Composable
fun WeightChangeSection(
    modifier: Modifier = Modifier,
    currentWeightText: String,
    weightChangeText: String,
    weightChangeIcon: Int,
    weightChangeColor: Color,
    isLoading: Boolean
) {

    if (isLoading) {
        AppProgressAnimation(
            modifier = modifier
        )
    } else {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.current_weight_section),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            Text(
                modifier = Modifier.padding(healthScreenContentItemTopPadding),
                text = currentWeightText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSizeMedium,
                    fontWeight = FontWeight.Bold
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(weightChangeIcon),
                    tint = weightChangeColor,
                    contentDescription = stringResource(R.string.picture_description)
                )

                Text(
                    modifier = Modifier.padding(healthScreenWeightChangeTextStartPadding),
                    text = weightChangeText,
                    style = bodyStyle.copy(
                        color = weightChangeColor,
                        fontSize = appTextSize
                    )
                )
            }
        }
    }
}

@Composable
private fun WeightPlotSection(
    modifier: Modifier = Modifier,
    weights: List<WeightModelUi>,
    isLoading: Boolean
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.weight_trend_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize
            )
        )

        Text(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding),
            text = stringResource(R.string.weight_trend_description),
            style = bodyStyle.copy(
                color = appSubtleTextColor,
                fontSize = appTextSizeSmall
            )
        )

        if (weights.isEmpty()) {
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
                weights = weights,
                isLoading = isLoading
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
                    .background(appColor)
                    .padding(horizontal = 15.dp)
            ) {
                HealthScreenWeightUi(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = false,
                    weights = listOf(
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 12.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 32.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    weightChartStartDateText = "12/12/1222",
                    weightChartStartDateDayText = R.string.time_monday_text,
                    weightChartEndDateText = "12/12/2004",
                    weightChartEndDateDayText = R.string.time_monday_text,
                    weightChangeText = "-12 kg",
                    weightChangeIcon = R.drawable.no_growing_icon,
                    weightChangeColor = weightChangeLossColor,
                    currentWeightText = "70kg",
                    isWeightChartStartDatePickerVisible = false,
                    isWeightChartEndDatePickerVisible = false,
                    isRefreshing = false,
                    proceedIntent = {}
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
                    .background(appColor)
                    .padding(horizontal = 15.dp)
            ) {
                HealthScreenWeightUi(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = false,
                    weights = listOf(
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 12.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 32.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    weightChartStartDateText = "12/12/1222",
                    weightChartStartDateDayText = R.string.time_monday_text,
                    weightChartEndDateText = "12/12/2004",
                    weightChartEndDateDayText = R.string.time_monday_text,
                    weightChangeText = "-12 kg",
                    weightChangeIcon = R.drawable.no_growing_icon,
                    weightChangeColor = weightChangeLossColor,
                    currentWeightText = "70 kg",
                    isWeightChartStartDatePickerVisible = false,
                    isWeightChartEndDatePickerVisible = false,
                    isRefreshing = false,
                    proceedIntent = {}
                )
            }
        }
    }
}