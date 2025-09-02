package com.alenniboris.personalmanager.presentation.screens.tasks.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenHeaderInfoBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenHeaderInfoBlockOuterLeftPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenHeaderInfoBlockOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenHeaderInfoBlockOuterRightPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenHeaderTextSize
import com.alenniboris.personalmanager.presentation.uikit.views.AppDetailsInfoBlock

@Composable
fun TasksScreenHeader(
    modifier: Modifier = Modifier,
    numberOfTodayTasks: String,
    numberOfCompletedTodayTasks: String,
    numberOfUpcomingTasks: String,
    numberOfSkippedTasks: String,
    isLoading: Boolean
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        AppDetailsInfoBlock(
            modifier = Modifier
                .padding(tasksScreenHeaderInfoBlockOuterLeftPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(tasksScreenHeaderInfoBlockInnerPadding),
            sectionHeader = stringResource(R.string.tasks_today_section_text),
            sectionValue = numberOfTodayTasks,
            headerTextSize = tasksScreenHeaderTextSize,
            isTextAligned = true,
            isLoading = isLoading
        )

        AppDetailsInfoBlock(
            modifier = Modifier
                .padding(tasksScreenHeaderInfoBlockOuterPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(tasksScreenHeaderInfoBlockInnerPadding),
            sectionHeader = stringResource(R.string.tasks_completed_section_text),
            sectionValue = numberOfCompletedTodayTasks,
            headerTextSize = tasksScreenHeaderTextSize,
            isTextAligned = true,
            isLoading = isLoading
        )

        AppDetailsInfoBlock(
            modifier = Modifier
                .padding(tasksScreenHeaderInfoBlockOuterPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(tasksScreenHeaderInfoBlockInnerPadding),
            sectionHeader = stringResource(R.string.tasks_upcoming_section_text),
            sectionValue = numberOfUpcomingTasks,
            headerTextSize = tasksScreenHeaderTextSize,
            isTextAligned = true,
            isLoading = isLoading
        )

        AppDetailsInfoBlock(
            modifier = Modifier
                .padding(tasksScreenHeaderInfoBlockOuterRightPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(tasksScreenHeaderInfoBlockInnerPadding),
            sectionHeader = stringResource(R.string.tasks_skipped_section_text),
            sectionValue = numberOfSkippedTasks,
            headerTextSize = tasksScreenHeaderTextSize,
            isTextAligned = true,
            isLoading = isLoading
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
                    .padding(PaddingValues(horizontal = 20.dp))
            ) {
                TasksScreenHeader(
                    modifier = Modifier
                        .padding(PaddingValues(vertical = 15.dp))
                        .fillMaxWidth(),
                    numberOfTodayTasks = "1",
                    numberOfSkippedTasks = "22",
                    numberOfUpcomingTasks = "122",
                    numberOfCompletedTodayTasks = "15",
                    isLoading = false
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
                    .padding(PaddingValues(horizontal = 20.dp))
            ) {
                TasksScreenHeader(
                    modifier = Modifier
                        .padding(PaddingValues(vertical = 15.dp))
                        .fillMaxWidth(),
                    numberOfTodayTasks = "1",
                    numberOfSkippedTasks = "22",
                    numberOfUpcomingTasks = "122",
                    numberOfCompletedTodayTasks = "15",
                    isLoading = false
                )
            }
        }
    }
}