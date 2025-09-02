package com.alenniboris.personalmanager.presentation.screens.tasks.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.screens.tasks.ITasksScreenIntent
import com.alenniboris.personalmanager.presentation.screens.tasks.TasksScreenState
import com.alenniboris.personalmanager.presentation.screens.tasks.TasksScreenValues
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenAddDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenAddDialogProgressHeight
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppTextField

@Composable
fun TasksScreenAddTaskDialog(
    addTaskData: TasksScreenState.TaskAddingData,
    isTaskUploading: Boolean,
    proceedIntent: (ITasksScreenIntent) -> Unit
) {
    AlertDialog(
        dismissButton = {},
        confirmButton = {},
        onDismissRequest = {
            if (!isTaskUploading) {
                proceedIntent(
                    ITasksScreenIntent.UpdateAddTaskDialogVisibility
                )
            }
        },
        containerColor = appColor,
        shape = appRoundedShape,
        text = {
            AddTaskDialogUi(
                data = addTaskData,
                isTaskUploading = isTaskUploading,
                proceedIntent = proceedIntent
            )
        }
    )
}

@Composable
private fun AddTaskDialogUi(
    data: TasksScreenState.TaskAddingData,
    isTaskUploading: Boolean,
    proceedIntent: (ITasksScreenIntent) -> Unit
) {

    if (isTaskUploading) {
        AppProgressAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .height(tasksScreenAddDialogProgressHeight)
        )
    } else {

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.add_new_task_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSizeMedium,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(tasksScreenAddDialogItemPadding),
                text = stringResource(R.string.create_new_task_text),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            Column(
                modifier = Modifier
                    .padding(tasksScreenAddDialogItemPadding)
            ) {
                Text(
                    text = stringResource(R.string.title_text),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(appRoundedShape)
                        .background(color = enterTextFieldColor),
                    value = data.title,
                    onValueChanged = {
                        proceedIntent(
                            ITasksScreenIntent.UpdateAddTaskTitle(
                                newValue = it
                            )
                        )
                    },
                    maxLines = Int.MAX_VALUE,
                    placeholder = stringResource(R.string.add_title_text)
                )
            }

            Column(
                modifier = Modifier
                    .padding(tasksScreenAddDialogItemPadding)
            ) {
                Text(
                    text = stringResource(R.string.description_text),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )
                AppTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(appRoundedShape)
                        .background(color = enterTextFieldColor),
                    value = data.description,
                    onValueChanged = {
                        proceedIntent(
                            ITasksScreenIntent.UpdateAddTaskDescription(
                                newValue = it
                            )
                        )
                    },
                    maxLines = Int.MAX_VALUE,
                    placeholder = stringResource(R.string.add_description_text)
                )
            }

            Column(
                modifier = Modifier
                    .padding(tasksScreenAddDialogItemPadding)
            ) {
                Text(
                    text = stringResource(R.string.priority_text),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )

                TasksScreenButtonRow(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .background(buttonRowBackgroundColor)
                        .padding(appButtonRowInnerPadding),
                    listOfElements = TasksScreenValues.TaskPriorityUiList.map { priority ->
                        ClickableElement(
                            text = stringResource(priority.toUiString()),
                            onClick = {
                                proceedIntent(
                                    ITasksScreenIntent.UpdateAddTaskPriority(priority)
                                )
                            }
                        )
                    },
                    currentElement = ClickableElement(
                        text = stringResource(data.priority.toUiString()),
                        onClick = {}
                    )
                )
            }

            Column(
                modifier = Modifier
                    .padding(tasksScreenAddDialogItemPadding)
            ) {
                Text(
                    text = stringResource(R.string.due_date_text),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )
                AppTextField(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .background(color = enterTextFieldColor)
                        .clickable {
                            proceedIntent(
                                ITasksScreenIntent.UpdateDatePickerVisibility()
                            )
                        },
                    value = data.selectedDateText,
                    onValueChanged = {},
                    isEnabled = false,
                    icon = painterResource(R.drawable.today_tasks_option)
                )
            }

            Column(
                modifier = Modifier
                    .padding(tasksScreenAddDialogItemPadding)
            ) {
                Text(
                    text = stringResource(R.string.due_time_text),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )
                AppTextField(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .background(color = enterTextFieldColor)
                        .clickable {
                            proceedIntent(
                                ITasksScreenIntent.UpdateTimePickerVisibility()
                            )
                        },
                    value = data.selectedTimeText,
                    onValueChanged = {},
                    isEnabled = false,
                    icon = painterResource(R.drawable.pending_task)
                )
            }

            AppCustomButton(
                modifier = Modifier
                    .padding(tasksScreenAddDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        ITasksScreenIntent.ProceedAddTaskAction
                    )
                },
                text = stringResource(R.string.add_new_task_text),
                icon = painterResource(R.drawable.add_icon)
            )

            AppCustomButton(
                modifier = Modifier
                    .padding(tasksScreenAddDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        ITasksScreenIntent.UpdateAddTaskDialogVisibility
                    )
                },
                text = stringResource(R.string.cancel_text),
                icon = painterResource(R.drawable.cancel_icon)
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
            AddTaskDialogUi(
                data = TasksScreenState.TaskAddingData(),
                isTaskUploading = false,
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
            AddTaskDialogUi(
                data = TasksScreenState.TaskAddingData(),
                isTaskUploading = false,
                proceedIntent = {}
            )
        }
    }
}

