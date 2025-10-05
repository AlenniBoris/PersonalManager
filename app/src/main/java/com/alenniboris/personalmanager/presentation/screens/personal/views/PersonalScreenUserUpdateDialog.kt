package com.alenniboris.personalmanager.presentation.screens.personal.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.alenniboris.personalmanager.domain.model.user.FitnessGoal
import com.alenniboris.personalmanager.domain.model.user.UserGender
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.user.UserModelUi
import com.alenniboris.personalmanager.presentation.screens.personal.IPersonalScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.addDialogProgressHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenInfoTextTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenProfileOptionTextStartPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppButtonRow
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppDoubleInputField
import com.alenniboris.personalmanager.presentation.uikit.views.AppLazyButtonRow
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppTextField

@Composable
fun PersonalScreenUserUpdateDialog(
    updateUser: UserModelUi,
    isUpdating: Boolean,
    listOfFitnessGoals: List<ClickableElement>,
    listOfUserGenders: List<ClickableElement>,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {
    AlertDialog(
        dismissButton = {},
        confirmButton = {},
        onDismissRequest = {
            if (!isUpdating) {
                proceedIntent(
                    IPersonalScreenIntent.ChangeUserUpdateDialogVisibility
                )
            }
        },
        containerColor = appColor,
        shape = appRoundedShape,
        text = {
            UpdateDialogUi(
                updateUser = updateUser,
                isUpdating = isUpdating,
                listOfFitnessGoals = listOfFitnessGoals,
                listOfUserGenders = listOfUserGenders,
                proceedIntent = proceedIntent
            )
        }
    )
}

@Composable
private fun UpdateDialogUi(
    updateUser: UserModelUi,
    isUpdating: Boolean,
    listOfFitnessGoals: List<ClickableElement>,
    listOfUserGenders: List<ClickableElement>,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    if (isUpdating) {
        AppProgressAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .height(addDialogProgressHeight)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.edit_profile_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(appDialogItemPadding),
                text = stringResource(R.string.edit_profile_description),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            AppTextField(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth()
                    .clip(appRoundedShape)
                    .background(color = enterTextFieldColor),
                value = updateUser.name,
                onValueChanged = { value ->
                    proceedIntent(
                        IPersonalScreenIntent.ChangeUserUpdateModelName(value)
                    )
                },
                placeholder = stringResource(R.string.name_placeholder)
            )

            AppTextField(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth()
                    .clip(appRoundedShape)
                    .background(color = enterTextFieldColor),
                value = updateUser.phoneNumber,
                onValueChanged = { value ->
                    proceedIntent(
                        IPersonalScreenIntent.ChangeUserUpdateModelPhone(value)
                    )
                },
                placeholder = stringResource(R.string.phone_placeholder)
            )

            Row(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                AppDoubleInputField(
                    modifier = Modifier
                        .weight(1f)
                        .clip(appRoundedShape)
                        .background(color = enterTextFieldColor),
                    initialValue = updateUser.age.toString(),
                    onValueChanged = { value ->
                        proceedIntent(
                            IPersonalScreenIntent.ChangeUserUpdateModelAge(value)
                        )
                    },
                    placeholder = stringResource(R.string.age_placeholder)
                )

                AppDoubleInputField(
                    modifier = Modifier
                        .padding(personalScreenProfileOptionTextStartPadding)
                        .weight(1f)
                        .clip(appRoundedShape)
                        .background(color = enterTextFieldColor),
                    initialValue = updateUser.height.toString(),
                    onValueChanged = { value ->
                        proceedIntent(
                            IPersonalScreenIntent.ChangeUserUpdateModelHeight(value)
                        )
                    },
                    placeholder = stringResource(R.string.height_placeholder)
                )
            }

            AppDoubleInputField(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth()
                    .clip(appRoundedShape)
                    .background(color = enterTextFieldColor),
                initialValue = updateUser.weight.toString(),
                onValueChanged = { value ->
                    proceedIntent(
                        IPersonalScreenIntent.ChangeUserUpdateModelWeight(value)
                    )
                },
                placeholder = stringResource(R.string.weight_placeholder)
            )

            AppTextField(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth()
                    .clip(appRoundedShape)
                    .background(color = enterTextFieldColor),
                value = updateUser.address,
                onValueChanged = { value ->
                    proceedIntent(
                        IPersonalScreenIntent.ChangeUserUpdateModelAddress(value)
                    )
                },
                placeholder = stringResource(R.string.address_placeholder)
            )

            AppButtonRow(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .clip(appRoundedShape)
                    .fillMaxWidth()
                    .background(buttonRowBackgroundColor)
                    .padding(appButtonRowInnerPadding),
                currentElement = ClickableElement(
                    text = stringResource(updateUser.gender.toUiString()),
                    onClick = {}
                ),
                listOfElements = listOfUserGenders
            )

            AppLazyButtonRow(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .clip(appRoundedShape)
                    .fillMaxWidth()
                    .background(buttonRowBackgroundColor)
                    .padding(appButtonRowInnerPadding),
                currentElement = ClickableElement(
                    text = stringResource(updateUser.fitnessGoal.toUiString()),
                    onClick = {}
                ),
                listOfElements = listOfFitnessGoals
            )

            NutritionSection(
                user = updateUser
            )

            AppCustomButton(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        IPersonalScreenIntent.UpdateUser
                    )
                },
                text = stringResource(R.string.edit_profile_text),
                icon = painterResource(R.drawable.add_icon)
            )

            AppCustomButton(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        IPersonalScreenIntent.ChangeUserUpdateDialogVisibility
                    )
                },
                text = stringResource(R.string.cancel_text),
                icon = painterResource(R.drawable.cancel_icon)
            )
        }
    }
}

@Composable
private fun NutritionSection(
    user: UserModelUi
) {

    Text(
        modifier = Modifier.padding(appDialogItemPadding),
        text = stringResource(R.string.recommended_intake_text),
        style = bodyStyle.copy(
            color = appMainTextColor,
            fontSize = appTextSize,
            fontWeight = FontWeight.Bold
        )
    )

    Row(
        modifier = Modifier
            .padding(appDialogItemPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.calories_section),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            Text(
                modifier = Modifier.padding(personalScreenInfoTextTopPadding),
                text = user.caloriesIntakeText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = appTextSize
                )
            )
        }

        Column(
            modifier = Modifier
                .padding(personalScreenProfileOptionTextStartPadding)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.proteins_section_text),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            Text(
                modifier = Modifier.padding(personalScreenInfoTextTopPadding),
                text = user.neededProteinsText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = appTextSize
                )
            )
        }
    }

    Row(
        modifier = Modifier
            .padding(appDialogItemPadding)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.carbs_section_text),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            Text(
                modifier = Modifier.padding(personalScreenInfoTextTopPadding),
                text = user.neededCarbohydratesText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = appTextSize
                )
            )
        }

        Column(
            modifier = Modifier
                .padding(personalScreenProfileOptionTextStartPadding)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.fats_section_text),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            Text(
                modifier = Modifier.padding(personalScreenInfoTextTopPadding),
                text = user.neededFatsText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = appTextSize
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
            ) {
                UpdateDialogUi(
                    updateUser = UserModelUi(),
                    isUpdating = false,
                    listOfUserGenders = UserGender.entries.map {
                        ClickableElement(
                            text = stringResource(it.toUiString()),
                            onClick = {}
                        )
                    },
                    listOfFitnessGoals = FitnessGoal.entries.map {
                        ClickableElement(
                            text = stringResource(it.toUiString()),
                            onClick = {}
                        )
                    },
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
                UpdateDialogUi(
                    updateUser = UserModelUi(),
                    isUpdating = false,
                    listOfUserGenders = UserGender.entries.map {
                        ClickableElement(
                            text = stringResource(it.toUiString()),
                            onClick = {}
                        )
                    },
                    listOfFitnessGoals = FitnessGoal.entries.map {
                        ClickableElement(
                            text = stringResource(it.toUiString()),
                            onClick = {}
                        )
                    },
                    proceedIntent = {}
                )
            }
        }
    }
}