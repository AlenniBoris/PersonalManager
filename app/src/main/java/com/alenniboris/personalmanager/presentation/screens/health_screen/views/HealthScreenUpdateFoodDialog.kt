package com.alenniboris.personalmanager.presentation.screens.health_screen.views

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
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeModelUi
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import com.alenniboris.personalmanager.presentation.screens.health_screen.IHealthScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.addDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenFoodAddDialogItemStartPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.addDialogProgressHeight
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppDoubleInputField
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppTextField
import java.util.Calendar

@Composable
fun HealthScreenUpdateFoodDialog(
    food: FoodIntakeModelUi,
    isFoodUploading: Boolean,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {
    AlertDialog(
        dismissButton = {},
        confirmButton = {},
        onDismissRequest = {
            if (!isFoodUploading) {
                proceedIntent(
                    IHealthScreenIntent.UpdatedFoodIntakeUpdateSelected(
                        null
                    )
                )
            }
        },
        containerColor = appColor,
        shape = appRoundedShape,
        text = {
            UpdateFoodDialogUi(
                food = food,
                isUploading = isFoodUploading,
                proceedIntent = proceedIntent
            )
        }
    )
}

@Composable
private fun UpdateFoodDialogUi(
    food: FoodIntakeModelUi,
    isUploading: Boolean,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {

    if (isUploading) {
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
                text = stringResource(R.string.update_food_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSizeMedium,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(addDialogItemPadding),
                text = stringResource(R.string.update_food_goal_text),
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
                    value = food.title,
                    onValueChanged = {
                        proceedIntent(
                            IHealthScreenIntent.UpdateFoodIntakeUpdateModelTitle(
                                newValue = it
                            )
                        )
                    },
                    maxLines = Int.MAX_VALUE,
                    placeholder = stringResource(R.string.food_name_example_placeholder)
                )
            }

            Column(
                modifier = Modifier
                    .padding(addDialogItemPadding)
            ) {
                Text(
                    text = stringResource(R.string.calories_section),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )
                AppDoubleInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(appRoundedShape)
                        .background(color = enterTextFieldColor),
                    initialValue = food.caloriesText,
                    onValueChanged = {
                        proceedIntent(
                            IHealthScreenIntent.UpdateFoodIntakeUpdateModelCalories(
                                newValue = ScreensCommonUtils.sanitizeCaloriesInputForTextField(
                                    it
                                )
                            )
                        )
                    },
                    placeholder = stringResource(R.string.calories_add_placeholder)
                )
            }

            Row(
                modifier = Modifier.padding(addDialogItemPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.proteins_section_text),
                        style = bodyStyle.copy(
                            color = appMainTextColor,
                            fontSize = appTextSizeSmall
                        )
                    )
                    AppDoubleInputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(appRoundedShape)
                            .background(color = enterTextFieldColor),
                        initialValue = food.proteinsAsString,
                        onValueChanged = {
                            proceedIntent(
                                IHealthScreenIntent.UpdateFoodIntakeUpdateModelProteins(
                                    newValue = it
                                )
                            )
                        },
                        placeholder = stringResource(R.string.proteins_add_placeholder)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(healthScreenFoodAddDialogItemStartPadding)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.fats_section_text),
                        style = bodyStyle.copy(
                            color = appMainTextColor,
                            fontSize = appTextSizeSmall
                        )
                    )
                    AppDoubleInputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(appRoundedShape)
                            .background(color = enterTextFieldColor),
                        initialValue = food.fatsAsString,
                        onValueChanged = {
                            proceedIntent(
                                IHealthScreenIntent.UpdateFoodIntakeUpdateModelFats(
                                    newValue = it
                                )
                            )
                        },
                        placeholder = stringResource(R.string.fats_add_placeholder)
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(healthScreenFoodAddDialogItemStartPadding)
                        .weight(1f)
                ) {
                    Text(
                        text = stringResource(R.string.carbs_section_text),
                        style = bodyStyle.copy(
                            color = appMainTextColor,
                            fontSize = appTextSizeSmall
                        )
                    )
                    AppDoubleInputField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(appRoundedShape)
                            .background(color = enterTextFieldColor),
                        initialValue = food.carbsAsString,
                        onValueChanged = {
                            proceedIntent(
                                IHealthScreenIntent.UpdateFoodIntakeUpdateModelCarbs(
                                    newValue = it
                                )
                            )
                        },
                        placeholder = stringResource(R.string.carbs_add_placeholder)
                    )
                }
            }

            AppCustomButton(
                modifier = Modifier
                    .padding(addDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        IHealthScreenIntent.ProceedFoodIntakeUpdate
                    )
                },
                text = stringResource(R.string.update_food_text),
                icon = painterResource(R.drawable.add_icon)
            )

            AppCustomButton(
                modifier = Modifier
                    .padding(addDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    proceedIntent(
                        IHealthScreenIntent.UpdatedFoodIntakeUpdateSelected(
                            null
                        )
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                UpdateFoodDialogUi(
                    food = FoodIntakeModelUi(
                        domainModel = FoodIntakeModelDomain(
                            id = "1",
                            userId = "1",
                            title = "kjsdcnkjds",
                            proteins = 112.3,
                            fats = 122.3,
                            carbohydrates = 17.0,
                            markingDate = Calendar.getInstance().time,
                            markingTime = Calendar.getInstance().time,
                            calories = 12112.3
                        )
                    ),
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                UpdateFoodDialogUi(
                    food = FoodIntakeModelUi(
                        domainModel = FoodIntakeModelDomain(
                            id = "1",
                            userId = "1",
                            title = "kjsdcnkjds",
                            proteins = 112.3,
                            fats = 122.3,
                            carbohydrates = 0.0,
                            markingDate = Calendar.getInstance().time,
                            markingTime = Calendar.getInstance().time,
                            calories = 12112.3
                        )
                    ),
                    isUploading = false,
                    proceedIntent = {}
                )
            }
        }
    }
}