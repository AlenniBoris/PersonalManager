package com.alenniboris.personalmanager.presentation.uikit.views

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
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeAddModel
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.addDialogProgressHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeMedium
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenFoodAddDialogItemStartPadding

@Composable
fun AppFoodAddingDialog(
    addModel: FoodIntakeAddModel,
    isFoodUploading: Boolean,
    onDismiss: () -> Unit,
    onTitleChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit,
    onProteinsChange: (String) -> Unit,
    onFatsChange: (String) -> Unit,
    onCarbsChange: (String) -> Unit,
    onAdd: () -> Unit
) {
    AlertDialog(
        dismissButton = {},
        confirmButton = {},
        onDismissRequest = {
            if (!isFoodUploading) {
                onDismiss()
            }
        },
        containerColor = appColor,
        shape = appRoundedShape,
        text = {
            AddFoodDialogUi(
                data = addModel,
                isUploading = isFoodUploading,
                onDismiss = onDismiss,
                onTitleChange = onTitleChange,
                onCaloriesChange = onCaloriesChange,
                onProteinsChange = onProteinsChange,
                onFatsChange = onFatsChange,
                onCarbsChange = onCarbsChange,
                onAdd = onAdd
            )
        }
    )
}

@Composable
private fun AddFoodDialogUi(
    data: FoodIntakeAddModel,
    isUploading: Boolean,
    onDismiss: () -> Unit,
    onTitleChange: (String) -> Unit,
    onCaloriesChange: (String) -> Unit,
    onProteinsChange: (String) -> Unit,
    onFatsChange: (String) -> Unit,
    onCarbsChange: (String) -> Unit,
    onAdd: () -> Unit
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
                text = stringResource(R.string.add_new_food_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSizeMedium,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(appDialogItemPadding),
                text = stringResource(R.string.add_food_goal_text),
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
                    placeholder = stringResource(R.string.food_name_example_placeholder)
                )
            }

            Column(
                modifier = Modifier
                    .padding(appDialogItemPadding)
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
                    initialValue = data.calories,
                    onValueChanged = {
                        onCaloriesChange(it)
                    },
                    placeholder = stringResource(R.string.calories_add_placeholder)
                )
            }

            Row(
                modifier = Modifier.padding(appDialogItemPadding),
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
                        initialValue = data.proteins,
                        onValueChanged = {
                            onProteinsChange(it)
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
                        initialValue = data.fats,
                        onValueChanged = {
                            onFatsChange(it)
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
                        initialValue = data.carbohydrates,
                        onValueChanged = {
                            onCarbsChange(it)
                        },
                        placeholder = stringResource(R.string.carbs_add_placeholder)
                    )
                }
            }

            AppCustomButton(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    onAdd()
                },
                text = stringResource(R.string.add_food_text),
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                AddFoodDialogUi(
                    data = FoodIntakeAddModel(
                        title = "firsdsd",
                        proteins = "123.0",
                        fats = "21.2",
                        carbohydrates = "44.2"
                    ),
                    isUploading = false,
                    onDismiss = {},
                    onTitleChange = {},
                    onCaloriesChange = {},
                    onProteinsChange = {},
                    onFatsChange = {},
                    onCarbsChange = {},
                    onAdd = {}
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
                AddFoodDialogUi(
                    data = FoodIntakeAddModel(
                        title = "firsdsd",
                        proteins = "123.0",
                        fats = "21.2",
                        carbohydrates = "44.2"
                    ),
                    isUploading = false,
                    onDismiss = {},
                    onTitleChange = {},
                    onCaloriesChange = {},
                    onProteinsChange = {},
                    onFatsChange = {},
                    onCarbsChange = {},
                    onAdd = {}
                )
            }
        }
    }
}