package com.alenniboris.personalmanager.presentation.screens.tasks.views

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.presentation.model.task.TaskModelUi
import com.alenniboris.personalmanager.presentation.screens.tasks.ITasksScreenIntent
import com.alenniboris.personalmanager.presentation.screens.tasks.TasksScreenOption
import com.alenniboris.personalmanager.presentation.screens.tasks.toUiIcon
import com.alenniboris.personalmanager.presentation.screens.tasks.toUiString
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.taskScreenComponentOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenContentListFirstPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenContentListPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenTasksColumnHeaderTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenTasksColumnSizeTextInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation

@Composable
fun TasksScreenContentListSection(
    modifier: Modifier,
    tasks: List<TaskModelUi>,
    tasksScreenOption: TasksScreenOption,
    isLoading: Boolean,
    proceedIntent: (ITasksScreenIntent) -> Unit
) {

    when {
        !isLoading && tasks.isEmpty() -> {
            AppEmptyScreen(
                modifier = Modifier
                    .padding(taskScreenComponentOuterPadding)
                    .fillMaxSize()
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
            )
        }

        isLoading -> {
            AppProgressAnimation(
                modifier = Modifier
                    .padding(taskScreenComponentOuterPadding)
                    .fillMaxSize()
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
            )
        }

        else -> {

            Column(
                modifier = modifier
            ) {

                Row(
                    modifier = Modifier
                        .padding(taskScreenComponentOuterPadding)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Row {
                        Icon(
                            painter = painterResource(tasksScreenOption.toUiIcon()),
                            tint = appMainTextColor,
                            contentDescription = stringResource(R.string.picture_description)
                        )

                        Text(
                            modifier = Modifier.padding(tasksScreenTasksColumnHeaderTextPadding),
                            text = stringResource(tasksScreenOption.toUiString()),
                            style = bodyStyle.copy(
                                color = appMainTextColor,
                                fontSize = appTextSize
                            )
                        )
                    }

                    Text(
                        modifier = Modifier
                            .clip(appRoundedShape)
                            .background(buttonRowBackgroundColor)
                            .padding(tasksScreenTasksColumnSizeTextInnerPadding),
                        text = tasks.size.toString(),
                        style = bodyStyle.copy(
                            color = appMainTextColor,
                            fontSize = appTextSize
                        )
                    )
                }

                LazyColumn {
                    itemsIndexed(tasks) { index, task ->
                        TasksScreenTaskItemUi(
                            modifier = Modifier
                                .padding(
                                    when (index) {
                                        0, tasks.size - 1 -> tasksScreenContentListFirstPadding
                                        else -> tasksScreenContentListPadding
                                    }
                                )
                                .fillMaxWidth(),
                            task = task,
                            proceedIntent = proceedIntent
                        )
                    }
                }
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
            ) {
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
                    tasks = listOf(
                        TaskModelUi(
                            domainModel = TaskModelDomain(
                                id = "kjnj",
                                userId = "212m",
                                title = "lkas",
                                description = "axpooi",
                                dueDate = Calendar.getInstance().time,
                                dueTime = Calendar.getInstance().time,
                                createdDate = Calendar.getInstance().time,
                                priority = TaskPriority.Medium,
                                status = TaskStatus.Skipped
                            )
                        ),
                        TaskModelUi(
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
                        TaskModelUi(
                            domainModel = TaskModelDomain(
                                id = "kjnj",
                                userId = "212m",
                                title = "lkas",
                                description = "axpooi",
                                dueDate = Calendar.getInstance().time,
                                dueTime = Calendar.getInstance().time,
                                createdDate = Calendar.getInstance().time,
                                priority = TaskPriority.High,
                                status = TaskStatus.Completed
                            )
                        )
                    ),
                    tasksScreenOption = TasksScreenOption.All_tasks,
                    isLoading = false,
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
            ) {
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
                    tasks = listOf(
                        TaskModelUi(
                            domainModel = TaskModelDomain(
                                id = "kjnj",
                                userId = "212m",
                                title = "lkas",
                                description = "axpooi",
                                dueDate = Calendar.getInstance().time,
                                dueTime = Calendar.getInstance().time,
                                createdDate = Calendar.getInstance().time,
                                priority = TaskPriority.Medium,
                                status = TaskStatus.Skipped
                            )
                        ),
                        TaskModelUi(
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
                        TaskModelUi(
                            domainModel = TaskModelDomain(
                                id = "kjnj",
                                userId = "212m",
                                title = "lkas",
                                description = "axpooi",
                                dueDate = Calendar.getInstance().time,
                                dueTime = Calendar.getInstance().time,
                                createdDate = Calendar.getInstance().time,
                                priority = TaskPriority.High,
                                status = TaskStatus.Completed
                            )
                        )
                    ),
                    tasksScreenOption = TasksScreenOption.All_tasks,
                    isLoading = false,
                    proceedIntent = {}
                )
            }
        }
    }
}