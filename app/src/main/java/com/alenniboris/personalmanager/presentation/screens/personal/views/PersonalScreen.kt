package com.alenniboris.personalmanager.presentation.screens.personal.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.screens.personal.IPersonalScreenEvent
import com.alenniboris.personalmanager.presentation.screens.personal.IPersonalScreenIntent
import com.alenniboris.personalmanager.presentation.screens.personal.PersonalScreenOption
import com.alenniboris.personalmanager.presentation.screens.personal.PersonalScreenState
import com.alenniboris.personalmanager.presentation.screens.personal.PersonalScreenViewModel
import com.alenniboris.personalmanager.presentation.screens.personal.toUiString
import com.alenniboris.personalmanager.presentation.uikit.model.ClickableElement
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appButtonRowInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.buttonRowBackgroundColor
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenContentItemVerticalPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.personalScreenContentPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.utils.ToastUtil
import com.alenniboris.personalmanager.presentation.uikit.values.PersonalScreenRoute
import com.alenniboris.personalmanager.presentation.uikit.views.AppDatePicker
import com.alenniboris.personalmanager.presentation.uikit.views.AppHeartRateAddingDialog
import com.alenniboris.personalmanager.presentation.uikit.views.AppLazyButtonRow
import com.alenniboris.personalmanager.presentation.uikit.views.AppSettingsDialog
import com.alenniboris.personalmanager.presentation.uikit.views.AppTopBar
import com.alenniboris.personalmanager.presentation.uikit.views.AppWeightAddingDialog
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Destination(route = PersonalScreenRoute)
fun PersonalScreen(
    navigator: DestinationsNavigator
) {

    val viewModel = hiltViewModel<PersonalScreenViewModel>()
    val event by remember { mutableStateOf(viewModel.event) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val proceedIntent by remember { mutableStateOf(viewModel::proceedIntent) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        launch {
            event.filterIsInstance<IPersonalScreenEvent.NavigateBack>().collect {
                navigator.popBackStack()
            }
        }

        launch {
            event.filterIsInstance<IPersonalScreenEvent.ShowToast>().collect { coming ->
                ToastUtil.show(
                    context = context,
                    resId = coming.messageId
                )
            }
        }
    }

    PersonalScreenUi(
        state = state,
        proceedIntent = proceedIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun PersonalScreenUi(
    state: PersonalScreenState,
    proceedIntent: (IPersonalScreenIntent) -> Unit
) {

    if (state.isSettingsVisible) {
        AppSettingsDialog(
            onDismiss = {
                proceedIntent(
                    IPersonalScreenIntent.ChangeSettingsDialogVisibility
                )
            }
        )
    }

    if (state.isUserUpdateDialogVisible) {
        PersonalScreenUserUpdateDialog(
            updateUser = state.userUpdate,
            isUpdating = state.isUserUpdating,
            listOfFitnessGoals = state.listOfFitnessGoals.map {
                ClickableElement(
                    text = stringResource(it.toUiString()),
                    onClick = {
                        proceedIntent(
                            IPersonalScreenIntent.ChangeUserUpdateModelFitnessGoal(it)
                        )
                    }
                )
            },
            listOfUserGenders = state.listOfUserGenders.map {
                ClickableElement(
                    text = stringResource(it.toUiString()),
                    onClick = {
                        proceedIntent(
                            IPersonalScreenIntent.ChangeUserUpdateModelGender(it)
                        )
                    }
                )
            },
            proceedIntent = proceedIntent
        )
    }

    if (state.isWeightsDatePickerVisible) {
        AppDatePicker(
            onDismiss = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateWeightsDatePickerVisibility
                )
            },
            onSelected = { date ->
                proceedIntent(
                    IPersonalScreenIntent.UpdateWeightsSelectedDate(date)
                )
                proceedIntent(
                    IPersonalScreenIntent.UpdateWeightsDatePickerVisibility
                )
            }
        )
    }

    if (state.isHeartRatesDatePickerVisible) {
        AppDatePicker(
            onDismiss = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateHeartRatesDatePickerVisibility
                )
            },
            onSelected = { date ->
                proceedIntent(
                    IPersonalScreenIntent.UpdateHeartRatesSelectedDate(date)
                )
                proceedIntent(
                    IPersonalScreenIntent.UpdateHeartRatesDatePickerVisibility
                )
            }
        )
    }

    if (state.isActivitiesDatePickerVisible) {
        AppDatePicker(
            onDismiss = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateActivitiesDatePickerVisibility
                )
            },
            onSelected = { date ->
                proceedIntent(
                    IPersonalScreenIntent.UpdateActivitiesSelectedDate(date)
                )
                proceedIntent(
                    IPersonalScreenIntent.UpdateActivitiesDatePickerVisibility
                )
            }
        )
    }

    if (state.isWeightAddDialogVisible) {
        AppWeightAddingDialog(
            addingWeight = state.addingWeight,
            isUploading = state.isWeightUploading,
            onDismiss = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateWeightsAddDialogVisibility
                )
            },
            onWeightChange = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateWeightAddModelWeight(it)
                )
            },
            onAdd = {
                proceedIntent(
                    IPersonalScreenIntent.AddWeight
                )
            }
        )
    }

    if (state.isHeartRateAddDialogVisible) {
        AppHeartRateAddingDialog(
            addingHeartRate = state.addingHeartRate,
            isUploading = state.isHeartRateUploading,
            onDismiss = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateHeartRatesAddDialogVisibility
                )
            },
            onHeartRate = {
                proceedIntent(
                    IPersonalScreenIntent.UpdateHeartRateAddModelValue(it)
                )
            },
            onAdd = {
                proceedIntent(
                    IPersonalScreenIntent.AddHeartRate
                )
            }
        )
    }

    if (state.isActivityAddDialogVisible) {
        PersonalScreenActivityAddDialog(
            addingActivity = state.addingActivity,
            isUploading = state.isActivityUploading,
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
            headerTextString = stringResource(R.string.personal_profile_text),
            firstButtonPainter = painterResource(R.drawable.back_icon),
            onFirstClicked = {
                proceedIntent(
                    IPersonalScreenIntent.NavigateBack
                )
            },
            secondButtonPainter = painterResource(R.drawable.settings_icon),
            onSecondClicked = {
                proceedIntent(
                    IPersonalScreenIntent.ChangeSettingsDialogVisibility
                )
            },
            thirdButtonPainter = painterResource(R.drawable.exit_app_icon),
            onThirdClicked = {
                proceedIntent(
                    IPersonalScreenIntent.ExitApp
                )
            }
        )

        AppLazyButtonRow(
            modifier = Modifier
                .padding(personalScreenContentPadding)
                .padding(personalScreenContentItemVerticalPadding)
                .clip(appRoundedShape)
                .fillMaxWidth()
                .background(buttonRowBackgroundColor)
                .padding(appButtonRowInnerPadding),
            currentElement = ClickableElement(
                text = stringResource(state.currentOption.toUiString()),
                onClick = {}
            ),
            listOfElements = state.listOfScreenOptions.map {
                ClickableElement(
                    text = stringResource(it.toUiString()),
                    onClick = {
                        proceedIntent(
                            IPersonalScreenIntent.ChangeOption(it)
                        )
                    }
                )
            }
        )

        when (state.currentOption) {
            PersonalScreenOption.Profile -> {
                PersonalScreenProfileOption(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(personalScreenContentPadding),
                    user = state.user,
                    todayWeight = state.todayWeight,
                    todayHeartRate = state.todayHeartRate,
                    todayActivitiesNumber = state.todayActivitiesNumber,
                    isDataLoading = state.isUserTodayDataLoading,
                    proceedIntent = proceedIntent
                )
            }

            PersonalScreenOption.Weight -> {
                PersonalScreenWeightOption(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(personalScreenContentPadding),
                    weights = state.weights,
                    selectedDateText = state.weightsSelectedDateText,
                    selectedDateDayId = state.weightsSelectedDateDayText,
                    isLoading = state.isWeightsLoading,
                    proceedIntent = proceedIntent
                )
            }

            PersonalScreenOption.Heart -> {
                PersonalScreenHeartOption(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(personalScreenContentPadding),
                    heartRates = state.heartRates,
                    selectedDateText = state.heartRatesSelectedDateText,
                    selectedDateDayId = state.heartRatesSelectedDateDayText,
                    isLoading = state.isHeartRatesLoading,
                    proceedIntent = proceedIntent
                )
            }

            PersonalScreenOption.Activity -> {
                PersonalScreenActivityOption(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(personalScreenContentPadding),
                    activities = state.activities,
                    selectedDateText = state.activitiesSelectedDateText,
                    selectedDateDayId = state.activitiesSelectedDateDayText,
                    isLoading = state.isActivitiesLoading,
                    proceedIntent = proceedIntent
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            PersonalScreenUi(
                state = PersonalScreenState(),
                proceedIntent = {}
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
private fun DarkTheme() {
    PersonalManagerTheme(
        darkTheme = true
    ) {
        Surface {
            PersonalScreenUi(
                state = PersonalScreenState(),
                proceedIntent = {}
            )
        }
    }
}