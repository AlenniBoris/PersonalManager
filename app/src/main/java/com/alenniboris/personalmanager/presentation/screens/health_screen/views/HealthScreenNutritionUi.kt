package com.alenniboris.personalmanager.presentation.screens.health_screen.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
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
import com.alenniboris.personalmanager.presentation.screens.health_screen.IHealthScreenIntent
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDateFilterInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionListPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenNutritionOptionTextStartPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import com.alenniboris.personalmanager.presentation.uikit.views.AppDatePicker
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation
import com.alenniboris.personalmanager.presentation.uikit.views.AppRefreshIndicator
import com.alenniboris.personalmanager.presentation.uikit.views.AppSingleLineDateFilter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthScreenNutritionUi(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    foodIntakeList: List<FoodIntakeModelUi>,
    foodIntakeDateText: String,
    totalCaloriesText: String,
    totalProteinsText: String,
    totalFatsText: String,
    totalCarbsText: String,
    isFoodIntakeDatePickerVisible: Boolean,
    isRefreshing: Boolean,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {

    if (isFoodIntakeDatePickerVisible) {
        AppDatePicker(
            onDismiss = {
                proceedIntent(
                    IHealthScreenIntent.UpdateFoodIntakeDatePickerVisibility
                )
            },
            onSelected = { date ->
                proceedIntent(
                    IHealthScreenIntent.UpdateFoodIntakeFilterDate(
                        date
                    )
                )
            }
        )
    }

    val refreshState = rememberPullToRefreshState()
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = {
            proceedIntent(
                IHealthScreenIntent.RefreshNutritionUiData
            )
        },
        state = refreshState,
        indicator = {
            AppRefreshIndicator(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                state = refreshState,
                isRefreshing = isRefreshing
            )
        },
        content = {
            ScreenContent(
                isLoading = isLoading,
                foodIntakeList = foodIntakeList,
                foodIntakeDateText = foodIntakeDateText,
                totalCaloriesText = totalCaloriesText,
                totalProteinsText = totalProteinsText,
                totalFatsText = totalFatsText,
                totalCarbsText = totalCarbsText,
                proceedIntent = proceedIntent
            )
        }
    )
}

@Composable
private fun ScreenContent(
    isLoading: Boolean,
    foodIntakeList: List<FoodIntakeModelUi>,
    foodIntakeDateText: String,
    totalCaloriesText: String,
    totalProteinsText: String,
    totalFatsText: String,
    totalCarbsText: String,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        AppSingleLineDateFilter(
            modifier = Modifier
                .fillMaxWidth()
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(appDateFilterInnerPadding),
            selectedFilterDateText = foodIntakeDateText,
            selectedFilterDateDayTextId = null,
            onPickerVisibilityChange = {
                proceedIntent(
                    IHealthScreenIntent.UpdateFoodIntakeDatePickerVisibility
                )
            },
            onCancel = {
                proceedIntent(
                    IHealthScreenIntent.UpdateFoodIntakeFilterDateToDefault
                )
            }
        )

        AppCustomButton(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .fillMaxWidth(),
            onClick = {
                proceedIntent(
                    IHealthScreenIntent.UpdateFoodIntakeAddDialogVisibility
                )
            },
            text = stringResource(R.string.add_food_text),
            icon = painterResource(R.drawable.add_icon)
        )

        TotalFoodStats(
            modifier = Modifier
                .padding(healthScreenContentItemTopPadding)
                .fillMaxWidth()
                .clip(appRoundedShape)
                .border(
                    width = appDetailsInfoBlockBorderWidth,
                    color = appSubtleTextColor,
                    shape = appRoundedShape
                )
                .padding(appDateFilterInnerPadding),
            totalCaloriesText = totalCaloriesText,
            totalProteinsText = totalProteinsText,
            totalFatsText = totalFatsText,
            totalCarbsText = totalCarbsText,
            isLoading = isLoading
        )

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
            foodList = foodIntakeList,
            isLoading = isLoading,
            proceedIntent = proceedIntent
        )
    }
}

@Composable
private fun TotalFoodStats(
    modifier: Modifier = Modifier,
    totalCaloriesText: String,
    totalProteinsText: String,
    totalFatsText: String,
    totalCarbsText: String,
    isLoading: Boolean
) {

    Column(
        modifier = modifier
    ) {

        if (isLoading) {
            AppProgressAnimation(
                modifier = Modifier.fillMaxSize()
            )
        } else {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = stringResource(R.string.daily_total_text),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    modifier = Modifier.padding(healthScreenNutritionOptionTextStartPadding),
                    text = totalCaloriesText + stringResource(R.string.ccal_text),
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Row(
                modifier = Modifier
                    .padding(healthScreenContentItemTopPadding)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(R.string.proteins_small_text) + totalProteinsText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize
                    )
                )

                Text(
                    modifier = Modifier.padding(healthScreenNutritionOptionTextStartPadding),
                    text = stringResource(R.string.fats_small_text) + totalFatsText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize
                    )
                )

                Text(
                    modifier = Modifier.padding(healthScreenNutritionOptionTextStartPadding),
                    text = stringResource(R.string.carbs_small_text) + totalCarbsText,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = appTextSize
                    )
                )
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
                HealthScreenNutritionUi(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = false,
                    foodIntakeDateText = "12/21/2122",
                    totalCaloriesText = "122",
                    totalProteinsText = "1222",
                    totalFatsText = "12",
                    totalCarbsText = "43",
                    isFoodIntakeDatePickerVisible = false,
                    foodIntakeList = listOf(
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
                    isRefreshing = false,
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
                HealthScreenNutritionUi(
                    modifier = Modifier.fillMaxSize(),
                    isLoading = false,
                    foodIntakeDateText = "12/21/2122",
                    totalCaloriesText = "122",
                    totalProteinsText = "1222",
                    totalFatsText = "12",
                    totalCarbsText = "43",
                    isFoodIntakeDatePickerVisible = false,
                    foodIntakeList = listOf(
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
                    isRefreshing = false,
                    proceedIntent = {}
                )
            }
        }
    }
}