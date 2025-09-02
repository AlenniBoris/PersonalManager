package com.alenniboris.personalmanager.presentation.screens.tasks.views

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import com.alenniboris.personalmanager.presentation.mapper.toUiColor
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.TaskModelUi
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockLeftPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockRightPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
import com.alenniboris.personalmanager.presentation.uikit.theme.taskScreenComponentOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenAddDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenTaskDetailsIconSize
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppDetailsInfoBlock

@Composable
fun TasksScreenTaskDetails(
    modifier: Modifier,
    task: TaskModelUi
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            modifier = Modifier.size(tasksScreenTaskDetailsIconSize),
            painter = painterResource(task.pictureId),
            tint = task.domainModel.status.toUiColor(),
            contentDescription = stringResource(R.string.picture_description)
        )

        Text(
            modifier = Modifier.padding(tasksScreenAddDialogItemPadding),
            text = task.domainModel.title,
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSizeMedium,
                fontWeight = FontWeight.Bold
            )
        )

        Text(
            modifier = Modifier.padding(tasksScreenAddDialogItemPadding),
            text = task.domainModel.description,
            style = bodyStyle.copy(
                color = appSubtleTextColor,
                fontSize = appTextSize
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(tasksScreenAddDialogItemPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            AppDetailsInfoBlock(
                modifier = Modifier
                    .padding(appDetailsInfoBlockRightPadding)
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .weight(1f)
                    .padding(weatherScreenCurrentForecastBlockInnerPadding),
                sectionHeader = stringResource(R.string.status_text),
                sectionValue = stringResource(task.domainModel.status.toUiString())
            )

            AppDetailsInfoBlock(
                modifier = Modifier
                    .padding(appDetailsInfoBlockLeftPadding)
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .weight(1f)
                    .padding(weatherScreenCurrentForecastBlockInnerPadding),
                sectionHeader = stringResource(R.string.priority_text),
                sectionValue = stringResource(task.domainModel.priority.toUiString())
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(tasksScreenAddDialogItemPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            AppDetailsInfoBlock(
                modifier = Modifier
                    .padding(appDetailsInfoBlockRightPadding)
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .weight(1f)
                    .padding(weatherScreenCurrentForecastBlockInnerPadding),
                sectionHeader = stringResource(R.string.due_date_text),
                sectionValue = task.dueDateText
            )

            AppDetailsInfoBlock(
                modifier = Modifier
                    .padding(appDetailsInfoBlockLeftPadding)
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .weight(1f)
                    .padding(weatherScreenCurrentForecastBlockInnerPadding),
                sectionHeader = stringResource(R.string.due_time_text),
                sectionValue = task.dueTimeText
            )
        }

        AppDetailsInfoBlock(
            modifier = Modifier
                .padding(tasksScreenAddDialogItemPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(weatherScreenCurrentForecastBlockInnerPadding),
            sectionHeader = stringResource(R.string.created_date_text),
            sectionValue = task.createdTimeText
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
            TasksScreenTaskDetails(
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
                )
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
            TasksScreenTaskDetails(
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
                )
            )
        }
    }
}