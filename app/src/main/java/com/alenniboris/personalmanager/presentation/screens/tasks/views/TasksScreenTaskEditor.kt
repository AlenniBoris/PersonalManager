package com.alenniboris.personalmanager.presentation.screens.tasks.views

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.task.TaskModelUi
import com.alenniboris.personalmanager.presentation.screens.tasks.ITasksScreenIntent
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
import com.alenniboris.personalmanager.presentation.uikit.theme.taskScreenComponentOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.addDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenAddDialogProgressHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppLazyButtonRow
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppTextField

@Composable
fun TasksScreenTaskEditor(
    modifier: Modifier = Modifier,
    task: TaskModelUi,
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
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.edit_task_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSizeMedium,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(addDialogItemPadding),
                text = stringResource(R.string.edit_task_explanation_text),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            Column(
                modifier = Modifier
                    .padding(addDialogItemPadding)
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
                    value = task.domainModel.title,
                    onValueChanged = {
                        proceedIntent(
                            ITasksScreenIntent.UpdateEditedTaskTitle(
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
                    .padding(addDialogItemPadding)
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
                    value = task.domainModel.description,
                    onValueChanged = {
                        proceedIntent(
                            ITasksScreenIntent.UpdateEditedTaskDescription(
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
                    .padding(addDialogItemPadding)
            ) {
                Text(
                    text = stringResource(R.string.status_text),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )

                AppLazyButtonRow(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .background(buttonRowBackgroundColor)
                        .padding(appButtonRowInnerPadding),
                    listOfElements = TasksScreenValues.TaskStatusUiList.map { priority ->
                        ClickableElement(
                            text = stringResource(priority.toUiString()),
                            onClick = {
                                proceedIntent(
                                    ITasksScreenIntent.UpdateEditedTaskStatus(priority)
                                )
                            }
                        )
                    },
                    currentElement = ClickableElement(
                        text = stringResource(task.domainModel.status.toUiString()),
                        onClick = {}
                    )
                )
            }

            Column(
                modifier = Modifier
                    .padding(addDialogItemPadding)
            ) {
                Text(
                    text = stringResource(R.string.priority_text),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )

                AppLazyButtonRow(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .background(buttonRowBackgroundColor)
                        .padding(appButtonRowInnerPadding),
                    listOfElements = TasksScreenValues.TaskPriorityUiList.map { priority ->
                        ClickableElement(
                            text = stringResource(priority.toUiString()),
                            onClick = {
                                proceedIntent(
                                    ITasksScreenIntent.UpdateEditedTaskPriority(priority)
                                )
                            }
                        )
                    },
                    currentElement = ClickableElement(
                        text = stringResource(task.domainModel.priority.toUiString()),
                        onClick = {}
                    )
                )
            }

            Column(
                modifier = Modifier
                    .padding(addDialogItemPadding)
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
                                ITasksScreenIntent.UpdateDatePickerVisibility(
                                    isEditor = true
                                )
                            )
                        },
                    value = task.dueDateText,
                    onValueChanged = {},
                    isEnabled = false,
                    icon = painterResource(R.drawable.today_tasks_option)
                )
            }

            Column(
                modifier = Modifier
                    .padding(addDialogItemPadding)
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
                                ITasksScreenIntent.UpdateTimePickerVisibility(
                                    isEditor = true
                                )
                            )
                        },
                    value = task.dueTimeText,
                    onValueChanged = {},
                    isEnabled = false,
                    icon = painterResource(R.drawable.pending_task)
                )
            }

            AppCustomButton(
                modifier = Modifier
                    .padding(addDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        ITasksScreenIntent.ProceedUpdateTaskAction
                    )
                },
                text = stringResource(R.string.save_edited_text),
                icon = painterResource(R.drawable.add_icon)
            )

            AppCustomButton(
                modifier = Modifier
                    .padding(addDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        ITasksScreenIntent.SelectEditedTask(null)
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
            TasksScreenTaskEditor(
                modifier = Modifier
                    .padding(taskScreenComponentOuterPadding)
                    .fillMaxWidth()
                    .background(appColor)
                    .padding(tasksScreenContentPadding),
                task = TaskModelUi(
                    domainModel = TaskModelDomain(
                        id = "kjnj",
                        userId = "212m",
                        title = "lkas",
                        description = "axpooi",
                        dueDate = Calendar.getInstance().time,
                        dueTime = Calendar.getInstance().time,
                        createdDate = Calendar.getInstance().time,
                        priority = TaskPriority.Low,
                        status = TaskStatus.Pending
                    )
                ),
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
            TasksScreenTaskEditor(
                modifier = Modifier
                    .padding(taskScreenComponentOuterPadding)
                    .fillMaxWidth()
                    .background(appColor)
                    .padding(tasksScreenContentPadding),
                task = TaskModelUi(
                    domainModel = TaskModelDomain(
                        id = "kjnj",
                        userId = "212m",
                        title = "lkas",
                        description = "axpooi",
                        dueDate = Calendar.getInstance().time,
                        dueTime = Calendar.getInstance().time,
                        createdDate = Calendar.getInstance().time,
                        priority = TaskPriority.Low,
                        status = TaskStatus.Pending
                    )
                ),
                isTaskUploading = false,
                proceedIntent = {}
            )
        }
    }
}