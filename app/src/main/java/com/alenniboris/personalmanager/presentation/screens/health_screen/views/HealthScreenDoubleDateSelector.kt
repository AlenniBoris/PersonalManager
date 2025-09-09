package com.alenniboris.personalmanager.presentation.screens.health_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockRightPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.taskScreenComponentOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenDateFilterButtonInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appDateFilterInnerPadding

@Composable
fun HealthScreenDoubleDateSelector(
    modifier: Modifier = Modifier,
    selectedFirstFilterDateText: String,
    selectedFirstFilterDateDayTextId: Int,
    selectedSecondFilterDateText: String,
    selectedSecondFilterDateDayTextId: Int,
    onFirstPickerVisibilityChange: () -> Unit,
    onFirstCancel: () -> Unit,
    onSecondPickerVisibilityChange: () -> Unit,
    onSecondCancel: () -> Unit
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = stringResource(R.string.select_date_range_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSize
            )
        )

        Text(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding),
            text = stringResource(R.string.from_date_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSizeSmall
            )
        )

        DateFilterRow(
            modifier = Modifier
                .fillMaxWidth(),
            selectedDateText = selectedFirstFilterDateText,
            selectedDateDayTextId = selectedFirstFilterDateDayTextId,
            onPickerVisibilityChange = {
                onFirstPickerVisibilityChange()
            },
            onCancel = {
                onFirstCancel()
            }
        )

        Text(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding),
            text = stringResource(R.string.to_date_text),
            style = bodyStyle.copy(
                color = appMainTextColor,
                fontSize = appTextSizeSmall
            )
        )

        DateFilterRow(
            modifier = Modifier
                .fillMaxWidth(),
            selectedDateText = selectedSecondFilterDateText,
            selectedDateDayTextId = selectedSecondFilterDateDayTextId,
            onPickerVisibilityChange = {
                onSecondPickerVisibilityChange()
            },
            onCancel = {
                onSecondCancel()
            }
        )
    }
}

@Composable
private fun DateFilterRow(
    modifier: Modifier = Modifier,
    selectedDateText: String?,
    selectedDateDayTextId: Int?,
    onPickerVisibilityChange: () -> Unit,
    onCancel: () -> Unit,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier
                .padding(appDetailsInfoBlockRightPadding)
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .clickable {
                    onPickerVisibilityChange()
                }
                .padding(tasksScreenDateFilterButtonInnerPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.padding(appDetailsInfoBlockRightPadding),
                painter = painterResource(R.drawable.today_tasks_option),
                tint = appMainTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                text = selectedDateText ?: stringResource(R.string.select_date_text),
                style = bodyStyle.copy(
                    fontSize = appTextSize,
                    color = appMainTextColor
                )
            )
        }

        selectedDateText?.let {
            Icon(
                modifier = Modifier
                    .padding(appDetailsInfoBlockRightPadding)
                    .clickable {
                        onCancel()
                    },
                painter = painterResource(R.drawable.cancel_icon),
                tint = appMainTextColor,
                contentDescription = stringResource(R.string.picture_description)
            )

            Text(
                text = stringResource(R.string.filtered_by_text)
                        + (selectedDateDayTextId?.let { stringResource(it) + ", " }
                    ?: "")
                        + selectedDateText,
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSizeSmall,
                    textAlign = TextAlign.End
                )
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(horizontal = 20.dp)
            ) {

                HealthScreenDoubleDateSelector(
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
                    selectedFirstFilterDateText = "12/12/3233",
                    selectedFirstFilterDateDayTextId = R.string.time_monday_text,
                    selectedSecondFilterDateText = "22/11/1222",
                    selectedSecondFilterDateDayTextId = R.string.time_monday_text,
                    onFirstPickerVisibilityChange = {},
                    onFirstCancel = {},
                    onSecondPickerVisibilityChange = {},
                    onSecondCancel = {}
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

                HealthScreenDoubleDateSelector(
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
                    selectedFirstFilterDateText = "12/12/3233",
                    selectedFirstFilterDateDayTextId = R.string.time_monday_text,
                    selectedSecondFilterDateText = "22/11/1222",
                    selectedSecondFilterDateDayTextId = R.string.time_monday_text,
                    onFirstPickerVisibilityChange = {},
                    onFirstCancel = {},
                    onSecondPickerVisibilityChange = {},
                    onSecondCancel = {}
                )
            }
        }
    }
}