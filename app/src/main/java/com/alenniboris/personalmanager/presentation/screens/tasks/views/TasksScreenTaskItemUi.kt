package com.alenniboris.personalmanager.presentation.screens.tasks.views

import android.icu.util.Calendar
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.presentation.mapper.toUiColor
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.TaskModelUi
import com.alenniboris.personalmanager.presentation.screens.tasks.ITasksScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.taskItemUiDetailsItemIconPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.taskItemUiInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.taskItemUiTextTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.taskScreenTaskDetailsTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenCompletedTaskBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenCompletedTaskBorderColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenPendingTaskBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenSkippedTaskBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenSkippedTaskBorderColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenTaskItemButtonPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenTaskItemSize
import kotlin.math.roundToInt

@Composable
fun TasksScreenTaskItemUi(
    modifier: Modifier = Modifier,
    task: TaskModelUi,
    proceedIntent: (ITasksScreenIntent) -> Unit
) {

    var isMoved by remember { mutableStateOf(false) }
    val offsetX by animateFloatAsState(
        targetValue = if (!isMoved) 0f else -350f,
        animationSpec = tween(150)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        isMoved = !isMoved
                    },
                    onHorizontalDrag = { _, dragAmount -> }
                )
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier
                    .size(tasksScreenTaskItemSize)
                    .clickable {
                        proceedIntent(
                            ITasksScreenIntent.UpdateSelectedTask(task)
                        )
                    },
                painter = painterResource(R.drawable.info_button),
                tint = appMainTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Icon(
                modifier = Modifier
                    .padding(tasksScreenTaskItemButtonPadding)
                    .size(tasksScreenTaskItemSize)
                    .clickable {
                    proceedIntent(
                        ITasksScreenIntent.SelectEditedTask(
                            task
                        )
                    )
                },
                painter = painterResource(R.drawable.edit_icon),
                tint = appMainTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Icon(
                modifier = Modifier
                    .padding(tasksScreenTaskItemButtonPadding)
                    .size(tasksScreenTaskItemSize)
                    .clickable {
                        proceedIntent(
                            ITasksScreenIntent.RemoveTask(
                                task
                            )
                        )
                    },
                painter = painterResource(R.drawable.delete_icon),
                tint = appMainTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )
        }

        TaskItemContent(
            modifier = modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) },
            task = task
        )
    }
}

@Composable
private fun TaskItemContent(
    modifier: Modifier,
    task: TaskModelUi
) {

    val backgroundColor by animateColorAsState(
        targetValue = when (task.domainModel.status) {
            TaskStatus.Skipped -> tasksScreenSkippedTaskBackgroundColor
            TaskStatus.Completed -> tasksScreenCompletedTaskBackgroundColor
            TaskStatus.Undefined, TaskStatus.Pending -> tasksScreenPendingTaskBackgroundColor
        }
    )
    val borderColor by animateColorAsState(
        targetValue = when (task.domainModel.status) {
            TaskStatus.Skipped -> tasksScreenSkippedTaskBorderColor
            TaskStatus.Completed -> tasksScreenCompletedTaskBorderColor
            TaskStatus.Undefined, TaskStatus.Pending -> appSubtleTextColor
        }
    )

    Row(
        modifier = modifier
            .clip(appRoundedShape)
            .border(
                width = appDetailsInfoBlockBorderWidth,
                color = borderColor,
                shape = appRoundedShape
            )
            .background(color = backgroundColor)
            .padding(taskItemUiInnerPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier.padding(taskItemUiDetailsItemIconPadding),
            painter = painterResource(task.pictureId),
            tint = task.domainModel.status.toUiColor(),
            contentDescription = stringResource(R.string.picture_description)
        )

        Column(
        ) {

            Text(
                text = task.domainModel.title,
                style = bodyStyle.copy(
                    color = taskScreenTaskDetailsTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = appTextSize
                ),
                maxLines = 1,
            )

            Text(
                modifier = Modifier.padding(taskItemUiTextTopPadding),
                text = task.domainModel.description,
                style = bodyStyle.copy(
                    color = taskScreenTaskDetailsTextColor,
                    fontSize = appTextSize
                ),
                maxLines = 1,
            )

            FlowRow(
                modifier = Modifier
                    .padding(taskItemUiTextTopPadding),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                TaskItemDetailsPlaceholder(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .background(color = task.domainModel.priority.toUiColor())
                        .padding(taskItemUiInnerPadding),
                    text = stringResource(task.domainModel.priority.toUiString())
                )

                TaskItemDetailsPlaceholder(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .background(color = task.domainModel.status.toUiColor())
                        .padding(taskItemUiInnerPadding),
                    text = stringResource(task.domainModel.status.toUiString())
                )

                TaskItemDetailsPlaceholder(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .padding(taskItemUiInnerPadding),
                    icon = painterResource(R.drawable.today_tasks_option),
                    text = task.dueDateText
                )

                TaskItemDetailsPlaceholder(
                    modifier = Modifier
                        .clip(appRoundedShape)
                        .padding(taskItemUiInnerPadding),
                    icon = painterResource(R.drawable.pending_task),
                    text = task.dueTimeText
                )
            }
        }
    }
}

@Composable
private fun TaskItemDetailsPlaceholder(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    iconTint: Color = taskScreenTaskDetailsTextColor,
    text: String
) {

    Row(
        modifier = modifier
    ) {

        icon?.let {
            Icon(
                modifier = Modifier.padding(taskItemUiDetailsItemIconPadding),
                painter = it,
                tint = iconTint,
                contentDescription = stringResource(R.string.picture_description)
            )
        }

        Text(
            text = text,
            style = bodyStyle.copy(
                color = taskScreenTaskDetailsTextColor,
                fontSize = appTextSizeSmall
            )
        )
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
                    .padding(horizontal = 20.dp)
            ) {

                TasksScreenTaskItemUi(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    task = TaskModelUi(
                        domainModel = TaskModelDomain(
                            id = "kjnj",
                            userId = "212m",
                            title = "lkasawlkxmlaswkxmlkasmxlkmsxlkmasxlkmxaslkmsxklmasxlkmsxlkmaslkxm",
                            description = "axpooi",
                            dueDate = Calendar.getInstance().time,
                            dueTime = Calendar.getInstance().time,
                            createdDate = Calendar.getInstance().time,
                            priority = TaskPriority.Medium,
                            status = TaskStatus.Completed
                        )
                    ),
                    proceedIntent = {}
                )

                TasksScreenTaskItemUi(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    task = TaskModelUi(
                        domainModel = TaskModelDomain(
                            id = "kjnj",
                            userId = "212m",
                            title = "lkasawlkxmlaswkxmlkasmxlkmsxlkmasxlkmxaslkmsxklmasxlkmsxlkmaslkxm",
                            description = "axpooi",
                            dueDate = Calendar.getInstance().time,
                            dueTime = Calendar.getInstance().time,
                            createdDate = Calendar.getInstance().time,
                            priority = TaskPriority.Medium,
                            status = TaskStatus.Pending
                        )
                    ),
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
                    .padding(horizontal = 20.dp)
            ) {

                TasksScreenTaskItemUi(
                    task = TaskModelUi(
                        domainModel = TaskModelDomain(
                            id = "kjnj",
                            userId = "212m",
                            title = "lkas",
                            description = "axpooi",
                            dueDate = Calendar.getInstance().time,
                            dueTime = Calendar.getInstance().time,
                            createdDate = Calendar.getInstance().time,
                            priority = TaskPriority.Medium,
                            status = TaskStatus.Completed
                        )
                    ),
                    proceedIntent = {}
                )

                TasksScreenTaskItemUi(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    task = TaskModelUi(
                        domainModel = TaskModelDomain(
                            id = "kjnj",
                            userId = "212m",
                            title = "lkasawlkxmlaswkxmlkasmxlkmsxlkmasxlkmxaslkmsxklmasxlkmsxlkmaslkxm",
                            description = "axpooi",
                            dueDate = Calendar.getInstance().time,
                            dueTime = Calendar.getInstance().time,
                            createdDate = Calendar.getInstance().time,
                            priority = TaskPriority.Medium,
                            status = TaskStatus.Pending
                        )
                    ),
                    proceedIntent = {}
                )
            }
        }
    }
}
