package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.task.TaskAddingData
import com.alenniboris.personalmanager.presentation.screens.tasks.ITasksScreenIntent
import com.alenniboris.personalmanager.presentation.screens.tasks.TasksScreenValues
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.addDialogProgressHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor

@Composable
fun AppTaskAddingDialog(
    addTaskData: TaskAddingData,
    isTaskUploading: Boolean,
    onDismiss: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (TaskPriority) -> Unit,
    onDueDatePickerVisibility: () -> Unit,
    onDueTimePickerVisibility: () -> Unit,
    onAdd: () -> Unit
) {
    AlertDialog(
        dismissButton = {},
        confirmButton = {},
        onDismissRequest = {
            if (!isTaskUploading) {
                onDismiss()

            }
        },
        containerColor = appColor,
        shape = appRoundedShape,
        text = {
            AddTaskDialogUi(
                data = addTaskData,
                isTaskUploading = isTaskUploading,
                onTitleChange = onTitleChange,
                onDescriptionChange = onDescriptionChange,
                onPriorityChange = onPriorityChange,
                onDueDatePickerVisibility = onDueDatePickerVisibility,
                onDueTimePickerVisibility = onDueTimePickerVisibility,
                onDismiss = onDismiss,
                onAdd = onAdd
            )
        }
    )
}

@Composable
private fun AddTaskDialogUi(
    data: TaskAddingData,
    isTaskUploading: Boolean,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (TaskPriority) -> Unit,
    onDueDatePickerVisibility: () -> Unit,
    onDueTimePickerVisibility: () -> Unit,
    onDismiss: () -> Unit,
    onAdd: () -> Unit
) {

    if (isTaskUploading) {
        AppProgressAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .height(addDialogProgressHeight)
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
                modifier = Modifier.padding(appDialogItemPadding),
                text = stringResource(R.string.create_new_task_text),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            Column(
                modifier = Modifier
                    .padding(appDialogItemPadding)
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
                        onTitleChange(it)
                    },
                    maxLines = Int.MAX_VALUE,
                    placeholder = stringResource(R.string.add_title_text)
                )
            }

            Column(
                modifier = Modifier
                    .padding(appDialogItemPadding)
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
                        onDescriptionChange(it)
                    },
                    maxLines = Int.MAX_VALUE,
                    placeholder = stringResource(R.string.add_description_text)
                )
            }

            Column(
                modifier = Modifier
                    .padding(appDialogItemPadding)
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
                                onPriorityChange(priority)
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
                    .padding(appDialogItemPadding)
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
                            onDueDatePickerVisibility()
                        },
                    value = data.selectedDateText,
                    onValueChanged = {},
                    isEnabled = false,
                    icon = painterResource(R.drawable.today_tasks_option)
                )
            }

            Column(
                modifier = Modifier
                    .padding(appDialogItemPadding)
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
                            onDueTimePickerVisibility()
                        },
                    value = data.selectedTimeText,
                    onValueChanged = {},
                    isEnabled = false,
                    icon = painterResource(R.drawable.pending_task)
                )
            }

            AppCustomButton(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    onAdd()
                },
                text = stringResource(R.string.add_new_task_text),
                icon = painterResource(R.drawable.add_icon)
            )

            AppCustomButton(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    onDismiss()
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
                data = TaskAddingData(),
                isTaskUploading = false,
                onDismiss = {},
                onTitleChange = {},
                onDescriptionChange = {},
                onPriorityChange = {},
                onDueDatePickerVisibility = {},
                onDueTimePickerVisibility = {},
                onAdd = {}
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
                data = TaskAddingData(),
                isTaskUploading = false,
                onDismiss = {},
                onTitleChange = {},
                onDescriptionChange = {},
                onPriorityChange = {},
                onDueDatePickerVisibility = {},
                onDueTimePickerVisibility = {},
                onAdd = {}
            )
        }
    }
}

