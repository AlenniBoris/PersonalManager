package com.alenniboris.personalmanager.presentation.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.activity.ActivityModelDomain
import com.alenniboris.personalmanager.presentation.model.activity.ActivityModelUi
import com.alenniboris.personalmanager.presentation.model.user.UserModelUi
import com.alenniboris.personalmanager.presentation.screens.home.HomeScreenQuickActions
import com.alenniboris.personalmanager.presentation.screens.home.HomeScreenState
import com.alenniboris.personalmanager.presentation.screens.home.IHomeScreenIntent
import com.alenniboris.personalmanager.presentation.screens.home.toUiPicture
import com.alenniboris.personalmanager.presentation.screens.home.toUiString
import com.alenniboris.personalmanager.presentation.screens.tasks.views.TasksScreenTimePicker
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appFlowRowHorizontalSpacing
import com.alenniboris.personalmanager.presentation.uikit.theme.appFlowRowVerticalSpacing
import com.alenniboris.personalmanager.presentation.uikit.theme.appInfoBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appInfoBlockMinHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenContentItemVerticalPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenHeartRateIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenInfoTextStartPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenQuickActionButtonsHorizontalArrangement
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenQuickActionButtonsVerticalArrangement
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenQuickActionsColor
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenStatisticsSectionInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenTasksIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenTemperatureIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenTimeTextInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.homeScreenWeightIconColor
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenContentItemVerticalPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.utils.toUiString
import com.alenniboris.personalmanager.presentation.uikit.views.AppDataProgressSection
import com.alenniboris.personalmanager.presentation.uikit.views.AppDatePicker
import com.alenniboris.personalmanager.presentation.uikit.views.AppDetailsInfoBlock
import com.alenniboris.personalmanager.presentation.uikit.views.AppFoodAddingDialog
import com.alenniboris.personalmanager.presentation.uikit.views.AppHeartRateAddingDialog
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppRefreshIndicator
import com.alenniboris.personalmanager.presentation.uikit.views.AppSettingsDialog
import com.alenniboris.personalmanager.presentation.uikit.views.AppTaskAddingDialog
import com.alenniboris.personalmanager.presentation.uikit.views.AppTopBar
import com.alenniboris.personalmanager.presentation.uikit.views.AppWeightAddingDialog
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUi(
    state: HomeScreenState,
    proceedIntent: (IHomeScreenIntent) -> Unit
) {

    if (state.isHeartRateAddDialogVisible) {
        AppHeartRateAddingDialog(
            addingHeartRate = state.addingHeartRate,
            isUploading = state.isHeartRateUploading,
            onDismiss = {
                proceedIntent(
                    IHomeScreenIntent.UpdateHeartRatesAddDialogVisibility
                )
            },
            onHeartRate = {
                proceedIntent(
                    IHomeScreenIntent.UpdateHeartRateAddModelValue(it)
                )
            },
            onAdd = {
                proceedIntent(
                    IHomeScreenIntent.AddHeartRate
                )
            }
        )
    }

    if (state.isWeightAddDialogVisible) {
        AppWeightAddingDialog(
            addingWeight = state.addingWeight,
            isUploading = state.isWeightUploading,
            onDismiss = {
                proceedIntent(
                    IHomeScreenIntent.UpdateWeightsAddDialogVisibility
                )
            },
            onWeightChange = {
                proceedIntent(
                    IHomeScreenIntent.UpdateWeightAddModelWeight(it)
                )
            },
            onAdd = {
                proceedIntent(
                    IHomeScreenIntent.AddWeight
                )
            }
        )
    }

    if (state.isFoodIntakeAddDialogVisible) {
        AppFoodAddingDialog(
            addModel = state.foodIntakeAddModel,
            isFoodUploading = state.isFoodIntakeAddProceeding,
            onDismiss = {
                proceedIntent(
                    IHomeScreenIntent.UpdateFoodIntakeAddDialogVisibility
                )
            },
            onTitleChange = {
                proceedIntent(
                    IHomeScreenIntent.UpdateFoodIntakeAddModelTitle(
                        newValue = it
                    )
                )
            },
            onCaloriesChange = {
                proceedIntent(
                    IHomeScreenIntent.UpdateFoodIntakeAddModelCalories(
                        newValue = it
                    )
                )
            },
            onProteinsChange = {
                proceedIntent(
                    IHomeScreenIntent.UpdateFoodIntakeAddModelProteins(
                        newValue = it
                    )
                )
            },
            onFatsChange = {
                proceedIntent(
                    IHomeScreenIntent.UpdateFoodIntakeAddModelFats(
                        newValue = it
                    )
                )
            },
            onCarbsChange = {
                proceedIntent(
                    IHomeScreenIntent.UpdateFoodIntakeAddModelCarbs(
                        newValue = it
                    )
                )
            },
            onAdd = {
                proceedIntent(
                    IHomeScreenIntent.ProceedFoodIntakeAdd
                )
            }
        )
    }

    if (state.isSettingsVisible) {
        AppSettingsDialog(
            onDismiss = {
                proceedIntent(
                    IHomeScreenIntent.ChangeSettingsDialogVisibility
                )
            }
        )
    }

    if (state.isAddTaskDialogVisible) {
        AppTaskAddingDialog(
            addTaskData = state.addTaskData,
            isTaskUploading = state.isTaskUploading,
            onDismiss = {
                proceedIntent(
                    IHomeScreenIntent.ChangeTaskAddDialogVisibility
                )
            },
            onTitleChange = {
                proceedIntent(
                    IHomeScreenIntent.UpdateAddTaskTitle(
                        newValue = it
                    )
                )
            },
            onDescriptionChange = {
                proceedIntent(
                    IHomeScreenIntent.UpdateAddTaskDescription(
                        newValue = it
                    )
                )
            },
            onPriorityChange = {
                proceedIntent(
                    IHomeScreenIntent.UpdateAddTaskPriority(it)
                )
            },
            onDueDatePickerVisibility = {
                proceedIntent(
                    IHomeScreenIntent.UpdateDatePickerVisibility
                )
            },
            onDueTimePickerVisibility = {
                proceedIntent(
                    IHomeScreenIntent.UpdateTimePickerVisibility
                )
            },
            onAdd = {
                proceedIntent(
                    IHomeScreenIntent.AddNewTask
                )
            }
        )
    }
    if (state.isDatePickerVisible) {
        AppDatePicker(
            onDismiss = {
                proceedIntent(
                    IHomeScreenIntent.UpdateDatePickerVisibility
                )
            },
            onSelected = { date ->
                proceedIntent(
                    IHomeScreenIntent.UpdateAddTaskDate(date)
                )
            }
        )
    }

    if (state.isTimePickerVisible) {
        TasksScreenTimePicker(
            onDismiss = {
                proceedIntent(
                    IHomeScreenIntent.UpdateTimePickerVisibility
                )
            },
            onSelected = { date ->
                proceedIntent(
                    IHomeScreenIntent.UpdateAddTaskTime(date)
                )
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appMainTextColor)
                .padding(topBarInnerPadding),
            textColor = appColor,
            headerTextString = stringResource(R.string.app_name),
            subtleText = stringResource(R.string.hello_text) + state.user.name,
            secondButtonPainter = painterResource(R.drawable.settings_icon),
            onSecondClicked = {
                proceedIntent(
                    IHomeScreenIntent.ChangeSettingsDialogVisibility
                )
            },
            thirdButtonPainter = painterResource(R.drawable.person_icon),
            onThirdClicked = {
                proceedIntent(
                    IHomeScreenIntent.OpenPersonalScreen
                )
            }
        )

        val refreshState = rememberPullToRefreshState()
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = state.isRefreshing,
            onRefresh = {
                proceedIntent(
                    IHomeScreenIntent.RefreshData
                )
            },
            state = refreshState,
            indicator = {
                AppRefreshIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    state = refreshState,
                    isRefreshing = state.isRefreshing
                )
            },
            content = {
                ScreenContent(
                    state = state,
                    proceedIntent = proceedIntent
                )
            }
        )
    }
}

@Composable
private fun ScreenContent(
    state: HomeScreenState,
    proceedIntent: (IHomeScreenIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(homeScreenContentPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier
                .padding(homeScreenContentItemTopPadding)
                .fillMaxWidth(),
            text = stringResource(R.string.today_overview_section),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontWeight = FontWeight.Bold,
                fontSize = appTextSizeMedium,
                textAlign = TextAlign.Start
            )
        )

        Text(
            modifier = Modifier
                .padding(homeScreenContentItemVerticalPadding)
                .fillMaxWidth(),
            text = stringResource(R.string.today_overview_section_description),
            style = bodyStyle.copy(
                color = appSubtleTextColor,
                fontSize = appTextSize,
                textAlign = TextAlign.Start
            )
        )

        FlowRow(
            modifier = Modifier
                .padding(homeScreenContentItemVerticalPadding)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(appFlowRowVerticalSpacing),
            horizontalArrangement = Arrangement.spacedBy(appFlowRowHorizontalSpacing)
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
                sectionIcon = painterResource(R.drawable.temperature_icon),
                iconTint = homeScreenTemperatureIconColor,
                sectionHeader = stringResource(R.string.temperature_text),
                sectionValue = state.currentWeatherForecast.temperatureText
                    ?: stringResource(R.string.no_text_placeholder),
                isLoading = state.isTemperatureLoading
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
                sectionIcon = painterResource(R.drawable.tasks_button_icon),
                iconTint = homeScreenTasksIconColor,
                sectionHeader = stringResource(R.string.tasks_text),
                sectionValue = state.tasksStatisticsText,
                isLoading = state.isTasksListLoading
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
                sectionIcon = painterResource(R.drawable.heart_rate_icon),
                iconTint = homeScreenHeartRateIconColor,
                sectionHeader = stringResource(R.string.heart_rate_text),
                sectionValue = state.lastHeartRate?.heartRateText?.let {
                    it + " " + stringResource(R.string.bpm_text)
                } ?: stringResource(R.string.no_text_placeholder),
                isLoading = state.isHeartRatesLoading
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
                sectionIcon = painterResource(R.drawable.weight_scale),
                iconTint = homeScreenWeightIconColor,
                sectionHeader = stringResource(R.string.weight_text),
                sectionValue = state.lastWeight?.weightText?.let {
                    it + " " + stringResource(R.string.kg_text)
                }
                    ?: stringResource(R.string.no_text_placeholder),
                isLoading = state.isWeightsLoading
            )
        }

        DailyProgressSection(
            modifier = Modifier
                .padding(homeScreenContentItemVerticalPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(homeScreenStatisticsSectionInnerPadding),
            tasksStatsText = state.tasksStatisticsText,
            tasksPercentage = state.tasksPercentage,
            caloriesStatsText = state.caloriesStatisticsText,
            caloriesPercentage = state.caloriesPercentage,
            isLoading = state.isTasksListLoading || state.isFoodDataLoading
        )

        QuickActionsSection(
            modifier = Modifier
                .padding(homeScreenContentItemVerticalPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(homeScreenStatisticsSectionInnerPadding),
            quickActions = state.quickActions,
            proceedIntent = proceedIntent
        )

        RecentActivitySection(
            modifier = Modifier
                .padding(homeScreenContentItemVerticalPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(homeScreenStatisticsSectionInnerPadding),
            activities = state.listOfRecentActivities
        )
    }
}

@Composable
private fun RecentActivitySection(
    modifier: Modifier = Modifier,
    activities: List<ActivityModelUi>
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.recent_activity_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        activities.forEach { activity ->
            ActivityInfo(
                modifier = Modifier
                    .padding(homeScreenContentItemTopPadding)
                    .fillMaxWidth(),
                activity = activity
            )
        }
    }
}

@Composable
private fun ActivityInfo(
    modifier: Modifier = Modifier,
    activity: ActivityModelUi
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = activity.name,
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize
            )
        )

        Text(
            modifier = Modifier
                .padding(homeScreenInfoTextStartPadding)
                .clip(appRoundedShape)
                .background(homeScreenQuickActionsColor)
                .padding(homeScreenTimeTextInnerPadding),
            text = activity.timeText + " " + stringResource(activity.timeUnit.toUiString()),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun QuickActionsSection(
    modifier: Modifier = Modifier,
    quickActions: List<HomeScreenQuickActions>,
    proceedIntent: (IHomeScreenIntent) -> Unit
) {
    Column(
        modifier = modifier
    ) {

        Text(
            modifier = Modifier.padding(personalScreenContentItemVerticalPadding),
            text = stringResource(R.string.quick_actions_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize,
                fontWeight = FontWeight.Bold
            )
        )

        FlowRow(
            modifier = Modifier
                .padding(homeScreenContentItemTopPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                homeScreenQuickActionButtonsHorizontalArrangement
            ),
            verticalArrangement = Arrangement.spacedBy(
                homeScreenQuickActionButtonsVerticalArrangement
            )
        ) {
            quickActions.forEach { action ->
                Column(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .background(homeScreenQuickActionsColor)
                        .weight(1f)
                        .padding(homeScreenStatisticsSectionInnerPadding)
                        .clickable {
                            proceedIntent(
                                IHomeScreenIntent.ProceedQuickAction(
                                    action = action
                                )
                            )
                        },
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(action.toUiPicture()),
                        tint = appMainTextColor,
                        contentDescription = stringResource(R.string.picture_description)
                    )
                    Text(
                        modifier = Modifier
                            .padding(homeScreenContentItemTopPadding)
                            .width(IntrinsicSize.Max),
                        text = stringResource(action.toUiString()),
                        style = bodyStyle.copy(
                            color = appMainTextColor,
                            fontSize = appTextSize,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyProgressSection(
    modifier: Modifier = Modifier,
    tasksStatsText: String,
    tasksPercentage: Double,
    caloriesStatsText: String,
    caloriesPercentage: Double,
    isLoading: Boolean
) {
    Column(
        modifier = modifier
    ) {

        Row(
            modifier = Modifier
                .padding(homeScreenContentItemVerticalPadding)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.daily_goals_icon),
                tint = appMainTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )
            Text(
                modifier = Modifier.padding(homeScreenInfoTextStartPadding),
                text = stringResource(R.string.daily_progress_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize,
                    fontWeight = FontWeight.Bold
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
                headerText = stringResource(R.string.tasks_completed_text),
                valueText = tasksStatsText,
                valuePercent = tasksPercentage
            )

            AppDataProgressSection(
                modifier = Modifier.padding(healthScreenContentItemTopPadding),
                headerText = stringResource(R.string.calories_section),
                valueText = caloriesStatsText,
                valuePercent = caloriesPercentage
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
            HomeScreenUi(
                state = HomeScreenState(
                    user = UserModelUi(name = "aasd"),
                    activities = listOf(
                        ActivityModelUi(
                            domainModel = ActivityModelDomain(
                                id = "12",
                                userId = "1212",
                                title = "ndcsnds",
                                duration = 1222,
                                calories = 120.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        ActivityModelUi(
                            domainModel = ActivityModelDomain(
                                id = "12",
                                userId = "1212",
                                title = "12sdaddsa",
                                duration = 12,
                                calories = 10.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        ActivityModelUi(
                            domainModel = ActivityModelDomain(
                                id = "12",
                                userId = "1212",
                                title = "laaaaa",
                                duration = 5,
                                calories = 480.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    )
                ),
                proceedIntent = {}
            )
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
            HomeScreenUi(
                state = HomeScreenState(
                    user = UserModelUi(name = "aasd"),
                    activities = listOf(
                        ActivityModelUi(
                            domainModel = ActivityModelDomain(
                                id = "12",
                                userId = "1212",
                                title = "ndcsnds",
                                duration = 1222,
                                calories = 120.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        ActivityModelUi(
                            domainModel = ActivityModelDomain(
                                id = "12",
                                userId = "1212",
                                title = "12sdaddsa",
                                duration = 12,
                                calories = 10.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        ActivityModelUi(
                            domainModel = ActivityModelDomain(
                                id = "12",
                                userId = "1212",
                                title = "laaaaa",
                                duration = 5,
                                calories = 480.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}