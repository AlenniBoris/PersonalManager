package com.alenniboris.personalmanager.presentation.screens.health_screen.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.TodayHealthStatisticsModelDomain
import com.alenniboris.personalmanager.domain.model.food.FoodIntakeModelDomain
import com.alenniboris.personalmanager.domain.model.heart.HeartRateModelDomain
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeModelUi
import com.alenniboris.personalmanager.presentation.model.health.TodayHealthStatisticsModelUi
import com.alenniboris.personalmanager.presentation.model.heart.HeartRateModelUi
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.screens.destinations.PersonalScreenDestination
import com.alenniboris.personalmanager.presentation.screens.health_screen.HealthScreenOption
import com.alenniboris.personalmanager.presentation.screens.health_screen.HealthScreenState
import com.alenniboris.personalmanager.presentation.screens.health_screen.HealthScreenViewModel
import com.alenniboris.personalmanager.presentation.screens.health_screen.IHealthScreenEvent
import com.alenniboris.personalmanager.presentation.screens.health_screen.IHealthScreenIntent
import com.alenniboris.personalmanager.presentation.screens.health_screen.toUiString
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.values.HealthScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.views.AppBottomSheet
import com.alenniboris.personalmanager.presentation.uikit.views.AppLazyButtonRow
import com.alenniboris.personalmanager.presentation.uikit.views.AppTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
@Destination(route = HealthScreenRoute)
@RequiresApi(Build.VERSION_CODES.O)
fun HealthScreen(
    navigator: DestinationsNavigator
) {

    val viewModel: HealthScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by remember { mutableStateOf(viewModel.event) }
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current

    LaunchedEffect(event) {
        launch {
            event.filterIsInstance<IHealthScreenEvent.ShowToast>().collect { coming ->
                LogPrinter.printLog(
                    tag = "!!!",
                    message = context.getString(coming.messageId)
                )
            }
        }
        launch {
            event.filterIsInstance<IHealthScreenEvent.OpenPersonalScreen>().collect {
                navigator.navigate(PersonalScreenDestination)
            }
        }
    }

    HealthScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HealthScreenUi(
    state: HealthScreenState,
    proceedIntent: (IHealthScreenIntent) -> Unit
) {

    state.foodIntakeDetailsSelected?.let { food ->
        AppBottomSheet(
            onDismiss = {
                proceedIntent(
                    IHealthScreenIntent.UpdatedFoodIntakeDetailsSelected(null)
                )
            },
            content = {
                HealthScreenFoodDetailsUi(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(healthScreenContentPadding),
                    food = food,
                    totalUserProteins = state.todayHealthStatistics.proteinsIntake,
                    totalUserFats = state.todayHealthStatistics.fatsIntake,
                    totalUserCarbs = state.todayHealthStatistics.carbsIntake
                )
            }
        )
    }

    state.foodIntakeUpdateSelected?.let { food ->
        HealthScreenUpdateFoodDialog(
            food = food,
            isFoodUploading = state.isFoodIntakeUpdateProceeding,
            proceedIntent = proceedIntent
        )
    }

    if (state.isFoodIntakeAddDialogVisible) {
        HealthScreenAddFoodDialog(
            addModel = state.foodIntakeAddModel,
            isFoodUploading = state.isFoodIntakeUpdateProceeding,
            proceedIntent = proceedIntent
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appColor)
    ) {

        AppTopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(appMainTextColor)
                .padding(topBarInnerPadding),
            textColor = appColor,
            headerTextString = stringResource(R.string.app_name),
            subtleText = stringResource(R.string.hello_text) + state.user?.name,
            secondButtonPainter = painterResource(R.drawable.settings_icon),
            onSecondClicked = {},
            thirdButtonPainter = painterResource(R.drawable.person_icon),
            onThirdClicked = {
                proceedIntent(
                    IHealthScreenIntent.OpenPersonalScreen
                )
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(healthScreenContentPadding)
        ) {

            AppLazyButtonRow(
                modifier = Modifier
                    .padding(healthScreenContentItemTopPadding)
                    .clip(appRoundedShape)
                    .fillMaxWidth()
                    .background(buttonRowBackgroundColor)
                    .padding(appButtonRowInnerPadding),
                currentElement = ClickableElement(
                    text = stringResource(state.screenOption.toUiString()),
                    onClick = {}
                ),
                listOfElements = state.listOfScreenOption.map { option ->
                    ClickableElement(
                        text = stringResource(option.toUiString()),
                        onClick = {
                            proceedIntent(
                                IHealthScreenIntent.ChangeScreenOption(option)
                            )
                        }
                    )
                }
            )

            when (state.screenOption) {
                HealthScreenOption.Overview -> {
                    HealthScreenOverviewUi(
                        modifier = Modifier.fillMaxSize(),
                        todayStatistics = state.todayHealthStatistics,
                        isLoading = state.isTodayStatisticsLoading
                    )
                }

                HealthScreenOption.Weight -> {
                    HealthScreenWeightUi(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        isLoading = state.isWeightDataLoading,
                        weights = state.weightChartList,
                        weightChartStartDateText = state.weightChartStartDateText,
                        weightChartStartDateDayText = state.weightChartStartDateDayText,
                        weightChartEndDateText = state.weightChartEndDateText,
                        weightChartEndDateDayText = state.weightChartEndDateDayText,
                        currentWeightText = state.currentWeightText,
                        weightChangeText = state.weightChangeText,
                        weightChangeIcon = state.weightChangeIcon,
                        weightChangeColor = state.weightChangeColor,
                        isWeightChartEndDatePickerVisible = state.isWeightChartEndDatePickerVisible,
                        isWeightChartStartDatePickerVisible = state.isWeightChartStartDatePickerVisible,
                        proceedIntent = proceedIntent
                    )
                }

                HealthScreenOption.Activity -> {
                    HealthScreenActivityUi(
                        modifier = Modifier.fillMaxSize(),
                        isLoading = state.isHeartRateDataLoading,
                        isHeartRateDatePickerVisible = state.isHeartRateDatePickerVisible,
                        heartRates = state.heartRateChartList,
                        heartRateChartDateText = state.heartRateChartDateText,
                        heartRateChartDateDayText = state.heartRateChartDateDayText,
                        proceedIntent = proceedIntent
                    )
                }

                HealthScreenOption.Nutrition -> {
                    HealthScreenNutritionUi(
                        modifier = Modifier.fillMaxSize(),
                        isLoading = state.isFoodDataLoading,
                        foodIntakeList = state.foodIntakeList,
                        foodIntakeDateText = state.foodIntakeDateText,
                        totalCaloriesText = state.totalCaloriesText,
                        totalProteinsText = state.totalProteinsText,
                        totalFatsText = state.totalFatsText,
                        totalCarbsText = state.totalCarbsText,
                        isFoodIntakeDatePickerVisible = state.isFoodIntakeDatePickerVisible,
                        proceedIntent = proceedIntent
                    )
                }
            }
        }
    }
}

@Composable
@Preview
@RequiresApi(Build.VERSION_CODES.O)
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            HealthScreenUi(
                state = HealthScreenState(
                    screenOption = HealthScreenOption.Overview,
                    todayHealthStatistics = TodayHealthStatisticsModelUi(
                        domainModel = TodayHealthStatisticsModelDomain(
                            currentWeight = 78.2,
                            averageHeartRate = 11.2,
                            consumedCalories = 1212.2,
                            caloriesIntake = 2000.1,
                            consumedProteins = 123.2,
                            proteinsIntake = 150.0,
                            consumedFats = 12.2,
                            fatsIntake = 50.3,
                            consumedCarbohydrates = 140.2,
                            carbohydratesIntake = 300.4
                        )
                    ),
                    weightChartList = listOf(
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 12.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 32.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    heartRateChartList = listOf(
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 122,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 89,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 215,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 90,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
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
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}

@Composable
@Preview
@RequiresApi(Build.VERSION_CODES.O)
private fun DarkTheme() {
    PersonalManagerTheme(
        darkTheme = true
    ) {
        Surface {
            HealthScreenUi(
                state = HealthScreenState(
                    screenOption = HealthScreenOption.Overview,
                    todayHealthStatistics = TodayHealthStatisticsModelUi(
                        domainModel = TodayHealthStatisticsModelDomain(
                            currentWeight = 78.2,
                            averageHeartRate = 11.2,
                            consumedCalories = 1212.2,
                            caloriesIntake = 2000.1,
                            consumedProteins = 123.2,
                            proteinsIntake = 150.0,
                            consumedFats = 12.2,
                            fatsIntake = 50.3,
                            consumedCarbohydrates = 140.2,
                            carbohydratesIntake = 300.4
                        )
                    ),
                    weightChartList = listOf(
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 12.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 32.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    heartRateChartList = listOf(
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 122,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 89,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 215,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        HeartRateModelUi(
                            domainModel = HeartRateModelDomain(
                                id = "1",
                                userId = "1",
                                heartRate = 90,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
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
                    )
                ),
                proceedIntent = {}
            )
        }
    }
}