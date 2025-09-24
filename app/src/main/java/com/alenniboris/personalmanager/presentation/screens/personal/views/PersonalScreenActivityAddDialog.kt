package com.alenniboris.personalmanager.presentation.screens.personal.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.alenniboris.personalmanager.presentation.screens.personal.IPersonalScreenIntent
import com.alenniboris.personalmanager.presentation.screens.personal.PersonalScreenState
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.addDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.addDialogProgressHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenProfileOptionTextStartPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppDoubleInputField
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppTextField

@Composable
fun PersonalScreenActivityAddDialog(
    addingActivity: PersonalScreenState.AddingActivity,
    isUploading: Boolean,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    AlertDialog(
        dismissButton = {},
        confirmButton = {},
        onDismissRequest = {
            if (!isUploading) {
                proceedIntent(
                    IPersonalScreenIntent.UpdateActivitiesAddDialogVisibility
                )
            }
        },
        containerColor = appColor,
        shape = appRoundedShape,
        text = {
            ActivityAddDialogUi(
                addingActivity = addingActivity,
                isUploading = isUploading,
                proceedIntent = proceedIntent
            )
        }
    )
}

@Composable
private fun ActivityAddDialogUi(
    addingActivity: PersonalScreenState.AddingActivity,
    isUploading: Boolean,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    if (isUploading) {
        AppProgressAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .height(addDialogProgressHeight)
        )
    } else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.add_activity_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(addDialogItemPadding),
                text = stringResource(R.string.add_activity_description),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            AppTextField(
                modifier = Modifier
                    .padding(addDialogItemPadding)
                    .fillMaxWidth()
                    .clip(appRoundedShape)
                    .background(color = enterTextFieldColor),
                value = addingActivity.title,
                onValueChanged = { value ->
                    proceedIntent(
                        IPersonalScreenIntent.UpdateActivityAddModelTitle(value)
                    )
                },
                placeholder = stringResource(R.string.add_activity_title_placeholder)
            )

            Row(
                modifier = Modifier
                    .padding(addDialogItemPadding)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AppDoubleInputField(
                    modifier = Modifier
                        .weight(1f)
                        .clip(appRoundedShape)
                        .background(color = enterTextFieldColor),
                    initialValue = addingActivity.duration,
                    onValueChanged = { value ->
                        proceedIntent(
                            IPersonalScreenIntent.UpdateActivityAddModelDuration(value)
                        )
                    },
                    placeholder = stringResource(R.string.add_activity_duration_placeholder)
                )

                AppDoubleInputField(
                    modifier = Modifier
                        .padding(personalScreenProfileOptionTextStartPadding)
                        .weight(1f)
                        .clip(appRoundedShape)
                        .background(color = enterTextFieldColor),
                    initialValue = addingActivity.calories,
                    onValueChanged = { value ->
                        proceedIntent(
                            IPersonalScreenIntent.UpdateActivityAddModelCalories(value)
                        )
                    },
                    placeholder = stringResource(R.string.add_activity_calories_placeholder)
                )
            }

            AppCustomButton(
                modifier = Modifier
                    .padding(addDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        IPersonalScreenIntent.AddActivity
                    )
                },
                text = stringResource(R.string.add_heart_rate_text),
                icon = painterResource(R.drawable.add_icon)
            )

            AppCustomButton(
                modifier = Modifier
                    .padding(addDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        IPersonalScreenIntent.UpdateActivitiesAddDialogVisibility
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
            Column(
                modifier = Modifier.background(appColor)
            ) {
                ActivityAddDialogUi(
                    addingActivity = PersonalScreenState.AddingActivity(),
                    isUploading = false,
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
                modifier = Modifier.background(appColor)
            ) {
                ActivityAddDialogUi(
                    addingActivity = PersonalScreenState.AddingActivity(),
                    isUploading = false,
                    proceedIntent = {}
                )
            }
        }
    }
}