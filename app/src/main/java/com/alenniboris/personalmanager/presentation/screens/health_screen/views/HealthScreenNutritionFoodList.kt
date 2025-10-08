package com.alenniboris.personalmanager.presentation.screens.health_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeModelUi
import com.alenniboris.personalmanager.presentation.screens.health_screen.IHealthScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.deleteButtonColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionFoodItemInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionFoodItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionFoodItemRowPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionFoodItemTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionListPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionTextDivisionSize
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionTextStartPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppEmptyScreen
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import java.util.Calendar

@Composable
fun HealthScreenNutritionFoodList(
    modifier: Modifier = Modifier,
    foodList: List<FoodIntakeModelUi>,
    isLoading: Boolean,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {
    when {
        isLoading -> {
            AppProgressAnimation(
                modifier = Modifier
                    .padding(healthScreenContentItemTopPadding)
                    .fillMaxSize()
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .padding(healthScreenNutritionOptionListPadding),
            )
        }

        !isLoading && foodList.isEmpty() -> {
            AppEmptyScreen(
                modifier = Modifier
                    .padding(healthScreenContentItemTopPadding)
                    .fillMaxSize()
                    .clip(appRoundedShape)
                    .border(
                        width = appDetailsInfoBlockBorderWidth,
                        color = appSubtleTextColor,
                        shape = appRoundedShape
                    )
                    .padding(healthScreenNutritionOptionListPadding),
            )
        }

        else -> {
            LazyColumn(
                modifier = modifier
            ) {

                item {
                    Text(
                        text = stringResource(R.string.today_food_items),
                        style = bodyStyle.copy(
                            color = appMainTextColor,
                            fontSize = appTextSize
                        )
                    )
                }

                itemsIndexed(foodList) { ind, food ->
                    FoodIntakeDetails(
                        modifier = Modifier
                            .padding(
                                if (ind == 0) healthScreenContentItemTopPadding
                                else healthScreenNutritionOptionFoodItemPadding
                            )
                            .fillMaxWidth()
                            .clip(appRoundedShape)
                            .border(
                                width = appDetailsInfoBlockBorderWidth,
                                color = appSubtleTextColor,
                                shape = appRoundedShape
                            )
                            .padding(healthScreenNutritionOptionFoodItemInnerPadding),
                        food = food,
                        proceedIntent = proceedIntent
                    )
                }
            }
        }
    }
}

@Composable
private fun FoodIntakeDetails(
    modifier: Modifier = Modifier,
    food: FoodIntakeModelUi,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        FoodIntakeDetailsTextSection(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    proceedIntent(
                        IHealthScreenIntent.UpdatedFoodIntakeDetailsSelected(
                            foodIntake = food
                        )
                    )
                },
            food = food
        )

        Icon(
            modifier = Modifier
                .padding(healthScreenNutritionOptionTextStartPadding)
                .clickable {
                    proceedIntent(
                        IHealthScreenIntent.UpdatedFoodIntakeUpdateSelected(
                            foodIntake = food
                        )
                    )
                },
            painter = painterResource(R.drawable.edit_icon),
            tint = appMainTextColor,
            contentDescription = stringResource(R.string.picture_description)
        )

        Icon(
            modifier = Modifier
                .padding(healthScreenNutritionOptionTextStartPadding)
                .clickable {
                    proceedIntent(
                        IHealthScreenIntent.ProceedFoodIntakeDelete(
                            foodIntake = food
                        )
                    )
                },
            painter = painterResource(R.drawable.delete_icon),
            tint = deleteButtonColor,
            contentDescription = stringResource(R.string.picture_description)
        )
    }
}

@Composable
private fun FoodIntakeDetailsTextSection(
    modifier: Modifier = Modifier,
    food: FoodIntakeModelUi
) {

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = food.title,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                ),
                maxLines = 1
            )

            Text(
                modifier = Modifier.padding(healthScreenNutritionOptionFoodItemTextPadding),
                text = food.caloriesText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Row(
            modifier = Modifier
                .padding(healthScreenNutritionOptionFoodItemRowPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            FlowRow(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(
                    healthScreenNutritionOptionTextDivisionSize
                ),
                verticalArrangement = Arrangement.spacedBy(
                    healthScreenNutritionOptionTextDivisionSize
                )
            ) {
                Text(
                    text = stringResource(R.string.proteins_small_text) + food.proteinsText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )

                Text(
                    text = stringResource(R.string.fats_small_text) + food.fatsText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )

                Text(
                    text = stringResource(R.string.carbs_small_text) + food.carbsText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSizeSmall
                    )
                )
            }

            Text(
                modifier = Modifier
                    .padding(healthScreenNutritionOptionTextStartPadding),
                text = stringResource(R.string.calories_section),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSizeSmall
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
                HealthScreenNutritionFoodList(
                    modifier = Modifier
                        .padding(healthScreenContentItemTopPadding)
                        .fillMaxWidth()
                        .clip(appRoundedShape)
                        .border(
                            width = appDetailsInfoBlockBorderWidth,
                            color = appSubtleTextColor,
                            shape = appRoundedShape
                        )
                        .padding(healthScreenNutritionOptionListPadding),
                    isLoading = false,
                    foodList = listOf(
                        FoodIntakeModelUi(
                            domainModel = FoodIntakeModelDomain(
                                id = "1",
                                userId = "1",
                                title = "kjsdcnkjds",
                                proteins = 102.3,
                                fats = 1244.3,
                                carbohydrates = 174.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time,
                                calories = 121.3
                            )
                        ),
                        FoodIntakeModelUi(
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
            ) {
                HealthScreenNutritionFoodList(
                    modifier = Modifier
                        .padding(healthScreenContentItemTopPadding)
                        .fillMaxWidth()
                        .clip(appRoundedShape)
                        .border(
                            width = appDetailsInfoBlockBorderWidth,
                            color = appSubtleTextColor,
                            shape = appRoundedShape
                        )
                        .padding(healthScreenNutritionOptionListPadding),
                    isLoading = false,
                    foodList = listOf(
                        FoodIntakeModelUi(
                            domainModel = FoodIntakeModelDomain(
                                id = "1",
                                userId = "1",
                                title = "kjsdcnkjds",
                                proteins = 102.3,
                                fats = 12.3,
                                carbohydrates = 17.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time,
                                calories = 121.3
                            )
                        ),
                        FoodIntakeModelUi(
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
                        )
                    ),
                    proceedIntent = {}
                )
            }
        }
    }
}
