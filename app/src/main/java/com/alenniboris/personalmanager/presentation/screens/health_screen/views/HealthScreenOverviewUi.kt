package com.alenniboris.personalmanager.presentation.screens.health_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.TodayHealthStatisticsModelDomain
import com.alenniboris.personalmanager.presentation.model.health.TodayHealthStatisticsModelUi
import com.alenniboris.personalmanager.presentation.screens.health_screen.IHealthScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockLeftPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockRightPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appIconPlaceholderShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenInfoIconInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenInfoItemInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenInfoTextColumnPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenInfoTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenOverviewCaloriesBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenOverviewCaloriesIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenOverviewHeartRateBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenOverviewHeartRateIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenOverviewStatisticsSectionInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenOverviewWeightBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenOverviewWeightIconColor
import com.alenniboris.personalmanager.presentation.uikit.views.AppDataProgressSection
import com.alenniboris.personalmanager.presentation.uikit.views.AppIconPlaceholder
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppRefreshIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreenOverviewUi(
    modifier: Modifier = Modifier,
    todayStatistics: TodayHealthStatisticsModelUi,
    isLoading: Boolean,
    isRefreshing: Boolean,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {

    val refreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = {
            proceedIntent(
                IHealthScreenIntent.RefreshOverviewUiData
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
                todayStatistics = todayStatistics,
                isLoading = isLoading
            )
        }
    )
}

@Composable
private fun ScreenContent(
    todayStatistics: TodayHealthStatisticsModelUi,
    isLoading: Boolean
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            IconizedInformation(
                modifier = Modifier
                    .padding(appDetailsInfoBlockRightPadding)
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .weight(1f)
                    .padding(healthScreenInfoItemInnerPadding),
                icon = painterResource(R.drawable.weight_scale),
                iconTint = healthScreenOverviewWeightIconColor,
                iconBackground = healthScreenOverviewWeightBackgroundColor,
                header = stringResource(R.string.current_weight_section),
                value = todayStatistics.currentWeightText + " " + stringResource(R.string.kg_text),
                isLoading = isLoading
            )

            IconizedInformation(
                modifier = Modifier
                    .padding(appDetailsInfoBlockLeftPadding)
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .weight(1f)
                    .padding(healthScreenInfoItemInnerPadding),
                icon = painterResource(R.drawable.heart_rate_icon),
                iconTint = healthScreenOverviewHeartRateIconColor,
                iconBackground = healthScreenOverviewHeartRateBackgroundColor,
                header = stringResource(R.string.heart_rate_section),
                value = todayStatistics.heartRateText + " " + stringResource(R.string.bpm_text),
                isLoading = isLoading
            )
        }

        IconizedInformation(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(healthScreenInfoItemInnerPadding),
            icon = painterResource(R.drawable.calories_section_icon),
            iconTint = healthScreenOverviewCaloriesIconColor,
            iconBackground = healthScreenOverviewCaloriesBackgroundColor,
            header = stringResource(R.string.calories_section),
            value = todayStatistics.caloriesText,
            isLoading = isLoading
        )

        TodayStatisticsSection(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(healthScreenOverviewStatisticsSectionInnerPadding),
            statistics = todayStatistics,
            isLoading = isLoading
        )
    }
}

@Composable
private fun TodayStatisticsSection(
    modifier: Modifier = Modifier,
    statistics: TodayHealthStatisticsModelUi,
    isLoading: Boolean
) {

    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.daily_goals_icon),
                tint = appMainTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )
            Text(
                modifier = Modifier.padding(healthScreenInfoTextColumnPadding),
                text = stringResource(R.string.daily_goals_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        if (isLoading) {
            AppProgressAnimation(
                modifier = Modifier
                    .padding(healthScreenContentItemTopPadding)
                    .fillMaxSize()
            )
        } else {

            AppDataProgressSection(
                modifier = Modifier.padding(healthScreenContentItemTopPadding),
                headerText = stringResource(R.string.calories_section),
                valueText = statistics.caloriesStatisticsText,
                valuePercent = statistics.consumedCaloriesPercent
            )

            AppDataProgressSection(
                modifier = Modifier.padding(healthScreenContentItemTopPadding),
                headerText = stringResource(R.string.proteins_section_text),
                valueText = statistics.proteinsStatisticsText + stringResource(R.string.grams_text),
                valuePercent = statistics.consumedProteinsPercent
            )

            AppDataProgressSection(
                modifier = Modifier.padding(healthScreenContentItemTopPadding),
                headerText = stringResource(R.string.carbs_section_text),
                valueText = statistics.carbohydratesStatisticsText + stringResource(R.string.grams_text),
                valuePercent = statistics.consumedCarbsPercent
            )

            AppDataProgressSection(
                modifier = Modifier.padding(healthScreenContentItemTopPadding),
                headerText = stringResource(R.string.fats_section_text),
                valueText = statistics.fatsStatisticsText + stringResource(R.string.grams_text),
                valuePercent = statistics.consumedFatsPercent
            )
        }
    }
}

@Composable
private fun IconizedInformation(
    modifier: Modifier = Modifier,
    icon: Painter,
    iconTint: Color,
    iconBackground: Color,
    header: String,
    value: String,
    isLoading: Boolean
) {

    if (isLoading) {
        AppProgressAnimation(
            modifier = modifier
        )
    } else {

        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {

            AppIconPlaceholder(
                modifier = Modifier
                    .clip(appIconPlaceholderShape)
                    .background(iconBackground)
                    .padding(healthScreenInfoIconInnerPadding),
                icon = icon,
                iconTint = iconTint
            )

            Column(
                modifier = Modifier.padding(healthScreenInfoTextColumnPadding)
            ) {
                Text(
                    text = header,
                    style = bodyStyle.copy(
                        fontSize = appTextSizeSmall,
                        color = appSubtleTextColor
                    )
                )

                Text(
                    modifier = Modifier.padding(healthScreenInfoTextPadding),
                    text = value,
                    style = bodyStyle.copy(
                        fontSize = appTextSize,
                        color = appMainTextColor,
                        fontWeight = FontWeight.Bold
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
                    .padding(horizontal = 15.dp)
            ) {
                HealthScreenOverviewUi(
                    modifier = Modifier.fillMaxSize(),
                    todayStatistics = TodayHealthStatisticsModelUi(
                        domainModel = TodayHealthStatisticsModelDomain(
                            currentWeight = 78.2,
                            averageHeartRate = 11.2,
                            consumedCalories = 1212.2,
                            caloriesIntake = 2000.1,
                            consumedProteins = 123.2,
                            proteinsIntake = 150.0,
                            consumedFats = 12.2,
                            fatsIntake = 50.3,
                            consumedCarbohydrates = 140.2,
                            carbohydratesIntake = 300.4
                        )
                    ),
                    isLoading = false,
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
                    .fillMaxSize()
                    .background(appColor)
                    .padding(horizontal = 15.dp)
            ) {
                HealthScreenOverviewUi(
                    modifier = Modifier.fillMaxSize(),
                    todayStatistics = TodayHealthStatisticsModelUi(
                        domainModel = TodayHealthStatisticsModelDomain(
                            currentWeight = 78.2,
                            averageHeartRate = 11.2,
                            consumedCalories = 1212.2,
                            caloriesIntake = 2000.1,
                            consumedProteins = 123.2,
                            proteinsIntake = 150.0,
                            consumedFats = 12.2,
                            fatsIntake = 50.3,
                            consumedCarbohydrates = 140.2,
                            carbohydratesIntake = 300.4
                        )
                    ),
                    isLoading = false,
                    isRefreshing = false,
                    proceedIntent = {}
                )
            }
        }
    }
}