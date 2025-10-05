package com.alenniboris.personalmanager.presentation.screens.tasks.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.presentation.screens.destinations.PersonalScreenDestination
import com.alenniboris.personalmanager.presentation.screens.tasks.ITasksScreenEvent
import com.alenniboris.personalmanager.presentation.screens.tasks.ITasksScreenIntent
import com.alenniboris.personalmanager.presentation.screens.tasks.TasksScreenOption
import com.alenniboris.personalmanager.presentation.screens.tasks.TasksScreenState
import com.alenniboris.personalmanager.presentation.screens.tasks.TasksScreenViewModel
import com.alenniboris.personalmanager.presentation.screens.tasks.toUiString
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDateFilterInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.taskScreenComponentOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.values.TasksScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.views.AppBottomSheet
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppDatePicker
import com.alenniboris.personalmanager.presentation.uikit.views.AppLazyButtonRow
import com.alenniboris.personalmanager.presentation.uikit.views.AppSettingsDialog
import com.alenniboris.personalmanager.presentation.uikit.views.AppSingleLineDateFilter
import com.alenniboris.personalmanager.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@Composable
@Destination(route = TasksScreenRoute)
@RequiresApi(Build.VERSION_CODES.O)
fun TasksScreen(
    navigator: DestinationsNavigator
) {

    val viewModel: TasksScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val event by remember { mutableStateOf(viewModel.event) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<ITasksScreenEvent.ShowToast>().collect { coming ->
                LogPrinter.printLog(
                    tag = "!!!",
                    message = context.getString(coming.messageId)
                )
            }
        }
        launch {
            event.filterIsInstance<ITasksScreenEvent.OpenPersonalScreen>().collect {
                navigator.navigate(PersonalScreenDestination)
            }
        }
    }

    TasksScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TasksScreenUi(
    state: TasksScreenState,
    proceedIntent: (ITasksScreenIntent) -> Unit
) {

    if (state.isSettingsVisible) {
        AppSettingsDialog(
            onDismiss = {
                proceedIntent(
                    ITasksScreenIntent.ChangeSettingsDialogVisibility
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
            subtleText = stringResource(R.string.hello_text) + state.user?.name,
            secondButtonPainter = painterResource(R.drawable.settings_icon),
            onSecondClicked = {
                proceedIntent(
                    ITasksScreenIntent.ChangeSettingsDialogVisibility
                )
            },
            thirdButtonPainter = painterResource(R.drawable.person_icon),
            onThirdClicked = {
                proceedIntent(
                    ITasksScreenIntent.OpenPersonalScreen
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(tasksScreenContentPadding)
        ) {

            TasksScreenHeader(
                modifier = Modifier
                    .padding(taskScreenComponentOuterPadding)
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                numberOfTodayTasks = state.numberOfTodayTasks,
                numberOfCompletedTodayTasks = state.numberOfCompletedTodayTasks,
                numberOfUpcomingTasks = state.numberOfTodayUpcomingTasks,
                numberOfSkippedTasks = state.numberOfTodaySkippedTasks,
                isLoading = state.isLoading
            )

            AppLazyButtonRow(
                modifier = Modifier
                    .padding(taskScreenComponentOuterPadding)
                    .clip(appRoundedShape)
                    .background(buttonRowBackgroundColor)
                    .padding(appButtonRowInnerPadding),
                listOfElements = state.screenOptions.map { option ->
                    ClickableElement(
                        text = stringResource(option.toUiString()),
                        onClick = {
                            proceedIntent(
                                ITasksScreenIntent.UpdateScreenOption(option)
                            )
                        }
                    )
                },
                currentElement = ClickableElement(
                    text = stringResource(state.currentScreenOption.toUiString()),
                    onClick = {}
                )
            )

            AppCustomButton(
                modifier = Modifier
                    .padding(taskScreenComponentOuterPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        ITasksScreenIntent.UpdateAddTaskDialogVisibility
                    )
                },
                text = stringResource(R.string.add_new_task_text),
                icon = painterResource(R.drawable.add_icon)
            )

            when (state.currentScreenOption) {
                TasksScreenOption.Today -> {
                    TasksScreenPieChart(
                        modifier = Modifier
                            .padding(taskScreenComponentOuterPadding)
                            .fillMaxWidth(),
                        listOfPieSegments = state.listOfTodayPieSegments
                    )
                }

                TasksScreenOption.All_tasks, TasksScreenOption.Upcoming, TasksScreenOption.Skipped -> {
                    AppSingleLineDateFilter(
                        modifier = Modifier
                            .padding(taskScreenComponentOuterPadding)
                            .fillMaxWidth()
                            .clip(appRoundedShape)
                            .border(
                                width = appDetailsInfoBlockBorderWidth,
                                color = appSubtleTextColor,
                                shape = appRoundedShape
                            )
                            .padding(appDateFilterInnerPadding),
                        selectedFilterDateText = state.selectedFilterDateText,
                        selectedFilterDateDayTextId = state.selectedFilterDateDayText,
                        onPickerVisibilityChange = {
                            proceedIntent(
                                ITasksScreenIntent.UpdateDateFilterPickerVisibility
                            )
                        },
                        onCancel = {
                            proceedIntent(
                                ITasksScreenIntent.UpdateSelectedFilterDate(null)
                            )
                        }
                    )
                }
            }

            TasksScreenContentListSection(
                modifier = Modifier
                    .padding(taskScreenComponentOuterPadding)
                    .fillMaxWidth()
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .padding(tasksScreenContentPadding),
                tasks = state.tasks,
                tasksScreenOption = state.currentScreenOption,
                isLoading = state.isLoading,
                proceedIntent = proceedIntent
            )

            if (state.isAddTaskDialogVisible) {
                TasksScreenAddTaskDialog(
                    addTaskData = state.addTaskData,
                    isTaskUploading = state.isTaskUploading,
                    proceedIntent = proceedIntent
                )
            }

            if (state.isDatePickerVisible) {
                AppDatePicker(
                    onDismiss = {
                        proceedIntent(
                            ITasksScreenIntent.UpdateDatePickerVisibility()
                        )
                    },
                    onSelected = { date ->
                        proceedIntent(
                            if (state.isEditor) ITasksScreenIntent.UpdateEditedTaskDate(date)
                            else ITasksScreenIntent.UpdateAddTaskDate(date)
                        )
                    }
                )
            }

            if (state.isDateFilterPickerVisible) {
                AppDatePicker(
                    onDismiss = {
                        proceedIntent(
                            ITasksScreenIntent.UpdateDateFilterPickerVisibility
                        )
                    },
                    onSelected = { date ->
                        proceedIntent(
                            ITasksScreenIntent.UpdateSelectedFilterDate(date)
                        )
                    }
                )
            }

            if (state.isTimePickerVisible) {
                TasksScreenTimePicker(
                    onDismiss = {
                        proceedIntent(
                            ITasksScreenIntent.UpdateTimePickerVisibility()
                        )
                    },
                    onSelected = { date ->
                        proceedIntent(
                            if (state.isEditor) ITasksScreenIntent.UpdateEditedTaskTime(date)
                            else ITasksScreenIntent.UpdateAddTaskTime(date)
                        )
                    }
                )
            }

            state.editedTask?.let {
                AppBottomSheet(
                    onDismiss = {
                        proceedIntent(
                            ITasksScreenIntent.SelectEditedTask(null)
                        )
                    },
                    content = {
                        TasksScreenTaskEditor(
                            modifier = Modifier
                                .padding(taskScreenComponentOuterPadding)
                                .fillMaxWidth()
                                .padding(tasksScreenContentPadding),
                            task = it,
                            isTaskUploading = state.isTaskUploading,
                            proceedIntent = proceedIntent
                        )
                    }
                )
            }

            state.selectedTask?.let {
                AppBottomSheet(
                    onDismiss = {
                        proceedIntent(
                            ITasksScreenIntent.UpdateSelectedTask(null)
                        )
                    },
                    content = {
                        TasksScreenTaskDetails(
                            modifier = Modifier
                                .padding(taskScreenComponentOuterPadding)
                                .fillMaxWidth()
                                .padding(tasksScreenContentPadding),
                            task = it
                        )
                    }
                )
            }
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
            TasksScreenUi(
                state = TasksScreenState(),
                proceedIntent = {}
            )
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
            TasksScreenUi(
                state = TasksScreenState(),
                proceedIntent = {}
            )
        }
    }
}