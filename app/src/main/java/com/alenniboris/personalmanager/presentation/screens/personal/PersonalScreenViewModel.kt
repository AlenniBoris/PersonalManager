package com.alenniboris.personalmanager.presentation.screens.personal

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.user.FitnessGoal
import com.alenniboris.personalmanager.domain.model.user.UserGender
import com.alenniboris.personalmanager.domain.usecase.logic.activity.IAddActivityUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.activity.IDeleteActivityUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.activity.IGetActivitiesByDateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.activity.IGetAllActivitiesUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IAddHeartRateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IDeleteHeartRateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IGetAllHeartRatesUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IGetHeartRatesByDateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.ISignOutUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IUpdateUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IAddWeightUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IDeleteWeightUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IGetAllWeightsUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IGetWeightsByDateUseCase
import com.alenniboris.personalmanager.domain.utils.SingleFlowEvent
import com.alenniboris.personalmanager.domain.utils.stripTime
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.activity.ActivityModelUi
import com.alenniboris.personalmanager.presentation.model.activity.toModelUi
import com.alenniboris.personalmanager.presentation.model.heart.AddingHeartRate
import com.alenniboris.personalmanager.presentation.model.heart.HeartRateModelUi
import com.alenniboris.personalmanager.presentation.model.heart.toModelUi
import com.alenniboris.personalmanager.presentation.model.user.toDomainModel
import com.alenniboris.personalmanager.presentation.model.user.toModelUi
import com.alenniboris.personalmanager.presentation.model.weight.AddingWeight
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.model.weight.toModelUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class PersonalScreenViewModel @Inject constructor(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val updateUserUseCase: IUpdateUserUseCase,
    private val getWeightsByDateUseCase: IGetWeightsByDateUseCase,
    private val getHeartRatesByDateUseCase: IGetHeartRatesByDateUseCase,
    private val getActivitiesByDateUseCase: IGetActivitiesByDateUseCase,
    private val getAllWeightsUseCase: IGetAllWeightsUseCase,
    private val addWeightUseCase: IAddWeightUseCase,
    private val deleteWeightUseCase: IDeleteWeightUseCase,
    private val getAllHeartRatesUseCase: IGetAllHeartRatesUseCase,
    private val addHeartRateUseCase: IAddHeartRateUseCase,
    private val deleteHeartRateUseCase: IDeleteHeartRateUseCase,
    private val getAllActivitiesUseCase: IGetAllActivitiesUseCase,
    private val addActivityUseCase: IAddActivityUseCase,
    private val deleteActivityUseCase: IDeleteActivityUseCase,
    private val signOutUserUseCase: ISignOutUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(PersonalScreenState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<IPersonalScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _lastRecordedWeightJob: Job? = null
    private var _lastRecordedHeartRateJob: Job? = null
    private var _todayActivitiesNumberJob: Job? = null
    private var _weightsJob: Job? = null
    private var _heartRatesJob: Job? = null
    private var _activitiesJob: Job? = null

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.userFlow.collect { user ->
                user?.let {
                    _state.update {
                        it.copy(
                            user = user.toModelUi()
                        )
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            _state.map { it.currentOption }
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { option ->
                    proceedOptionChangeAction(option)
                }
        }
    }

    private fun proceedOptionChangeAction(option: PersonalScreenOption) {
        when (option) {
            PersonalScreenOption.Profile -> loadInitData()
            PersonalScreenOption.Weight -> loadWeights()
            PersonalScreenOption.Heart -> loadHeartRates()
            PersonalScreenOption.Activity -> loadActivities()
        }
    }

    private fun loadActivities() {
        _activitiesJob?.cancel()
        _activitiesJob = viewModelScope.launch {
            _state.update { it.copy(isActivitiesLoading = true) }
            when (
                val res = _state.value.activitiesSelectedDate?.let { date ->
                    getActivitiesByDateUseCase.invoke(date = date)
                } ?: getAllActivitiesUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update {
                        it.copy(
                            activities = res.result.map { activity -> activity.toModelUi() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isActivitiesLoading = false) }
        }
    }

    private fun loadHeartRates() {
        _heartRatesJob?.cancel()
        _heartRatesJob = viewModelScope.launch {
            _state.update { it.copy(isHeartRatesLoading = true) }
            when (
                val res = _state.value.heartRatesSelectedDate?.let { date ->
                    getHeartRatesByDateUseCase.invoke(date = date)
                } ?: getAllHeartRatesUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update {
                        it.copy(
                            heartRates = res.result.map { heartRate -> heartRate.toModelUi() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isHeartRatesLoading = false) }
        }
    }

    private fun loadWeights() {
        _weightsJob?.cancel()
        _weightsJob = viewModelScope.launch {
            _state.update { it.copy(isWeightsLoading = true) }
            when (
                val res = _state.value.weightsSelectedDate?.let { date ->
                    getWeightsByDateUseCase.invoke(date = date)
                } ?: getAllWeightsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update {
                        it.copy(
                            weights = res.result.map { weight -> weight.toModelUi() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isWeightsLoading = false) }
        }
    }

    init {
        loadInitData()
    }

    private fun loadInitData() {
        loadLastRecordedWeight()
        loadLastRecordedHeartRate()
        loadTodayActivitiesNumber()
    }

    private fun loadTodayActivitiesNumber() {
        _todayActivitiesNumberJob?.cancel()
        _todayActivitiesNumberJob = viewModelScope.launch {
            _state.update { it.copy(isTodayActivitiesNumberLoading = true) }
            when (
                val res = getActivitiesByDateUseCase.invoke(
                    date = Calendar.getInstance().time.stripTime()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update { state ->
                        state.copy(
                            todayActivitiesNumber = res.result.size
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isTodayActivitiesNumberLoading = false) }
        }
    }

    private fun loadLastRecordedHeartRate() {
        _lastRecordedHeartRateJob?.cancel()
        _lastRecordedHeartRateJob = viewModelScope.launch {
            _state.update { it.copy(isTodayHeartRateLoading = true) }
            when (
                val res = getAllHeartRatesUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update { state ->
                        state.copy(
                            todayHeartRate = res.result.firstOrNull()?.toModelUi()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isTodayHeartRateLoading = false) }
        }
    }

    private fun loadLastRecordedWeight() {
        _lastRecordedWeightJob?.cancel()
        _lastRecordedWeightJob = viewModelScope.launch {
            _state.update { it.copy(isTodayWeightLoading = true) }
            when (
                val res = getAllWeightsUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update { state ->
                        state.copy(
                            todayWeight = res.result.firstOrNull()?.toModelUi()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isTodayWeightLoading = false) }
        }
    }

    fun proceedIntent(intent: IPersonalScreenIntent) {
        when (intent) {
            is IPersonalScreenIntent.NavigateBack -> navigateBack()
            is IPersonalScreenIntent.ExitApp -> exitApp()
            is IPersonalScreenIntent.ChangeOption -> changeOption(intent.option)
            is IPersonalScreenIntent.ChangeUserUpdateDialogVisibility -> changeUserUpdateDialogVisibility()
            is IPersonalScreenIntent.UpdateUser -> updateUser()
            is IPersonalScreenIntent.AddActivity -> addActivity()
            is IPersonalScreenIntent.AddHeartRate -> addHeartRate()
            is IPersonalScreenIntent.AddWeight -> addWeight()
            is IPersonalScreenIntent.UpdateActivitiesAddDialogVisibility -> updateActivitiesAddDialogVisibility()
            is IPersonalScreenIntent.UpdateHeartRatesAddDialogVisibility -> updateHeartRatesAddDialogVisibility()
            is IPersonalScreenIntent.UpdateWeightsAddDialogVisibility -> updateWeightsAddDialogVisibility()
            is IPersonalScreenIntent.UpdateActivitiesSelectedDate ->
                updateActivitiesSelectedDate(intent.date)

            is IPersonalScreenIntent.UpdateHeartRatesSelectedDate ->
                updateHeartRatesSelectedDate(intent.date)

            is IPersonalScreenIntent.UpdateWeightsSelectedDate ->
                updateWeightsSelectedDate(intent.date)

            is IPersonalScreenIntent.UpdateActivitiesDatePickerVisibility -> updateActivitiesDatePickerVisibility()
            is IPersonalScreenIntent.UpdateWeightsDatePickerVisibility -> updateWeightsDatePickerVisibility()
            is IPersonalScreenIntent.UpdateHeartRatesDatePickerVisibility -> updateHeartRatesDatePickerVisibility()
            is IPersonalScreenIntent.DeleteWeight -> deleteWeight(intent.selected)
            is IPersonalScreenIntent.DeleteHeartRate -> deleteHeartRate(intent.selected)
            is IPersonalScreenIntent.DeleteActivity -> deleteActivity(intent.selected)
            is IPersonalScreenIntent.UpdateWeightAddModelWeight -> updateWeightAddModelWeight(intent.value)
            is IPersonalScreenIntent.UpdateHeartRateAddModelValue ->
                updateHeartRateAddModelValue(intent.value)

            is IPersonalScreenIntent.UpdateActivityAddModelCalories ->
                updateActivityAddModelCalories(intent.value)

            is IPersonalScreenIntent.UpdateActivityAddModelDuration ->
                updateActivityAddModelDuration(intent.value)

            is IPersonalScreenIntent.UpdateActivityAddModelTitle ->
                updateActivityAddModelTitle(intent.value)

            is IPersonalScreenIntent.ChangeUserUpdateModelAddress ->
                changeUserUpdateModelAddress(intent.newValue)

            is IPersonalScreenIntent.ChangeUserUpdateModelAge ->
                changeUserUpdateModelAge(intent.newValue)

            is IPersonalScreenIntent.ChangeUserUpdateModelHeight ->
                changeUserUpdateModelHeight(intent.newValue)

            is IPersonalScreenIntent.ChangeUserUpdateModelName ->
                changeUserUpdateModelName(intent.newValue)

            is IPersonalScreenIntent.ChangeUserUpdateModelPhone ->
                changeUserUpdateModelPhone(intent.newValue)

            is IPersonalScreenIntent.ChangeUserUpdateModelWeight ->
                changeUserUpdateModelWeight(intent.newValue)

            is IPersonalScreenIntent.ChangeUserUpdateModelFitnessGoal ->
                changeUserUpdateModelFitnessGoal(intent.newValue)

            is IPersonalScreenIntent.ChangeUserUpdateModelGender ->
                changeUserUpdateModelGender(intent.newValue)

            is IPersonalScreenIntent.ChangeSettingsDialogVisibility ->
                changeSettingsDialogVisibility()
        }
    }

    private fun changeSettingsDialogVisibility() {
        _state.update { it.copy(isSettingsVisible = !it.isSettingsVisible) }
    }

    private fun changeUserUpdateModelFitnessGoal(newValue: FitnessGoal) {
        _state.update { state ->
            state.copy(
                userUpdate = state.userUpdate.copy(
                    fitnessGoal = newValue
                )
            )
        }
        updateUserFoodValues()
    }

    private fun changeUserUpdateModelGender(newValue: UserGender) {
        _state.update { state ->
            state.copy(
                userUpdate = state.userUpdate.copy(
                    gender = newValue
                )
            )
        }
        updateUserFoodValues()
    }

    private fun changeUserUpdateModelAddress(newValue: String) {
        _state.update { state ->
            state.copy(
                userUpdate = state.userUpdate.copy(
                    address = newValue
                )
            )
        }
    }

    private fun updateUserFoodValues() {
        val newValues = PersonalScreenUtils.calculateNutrition(
            age = _state.value.userUpdate.age,
            height = _state.value.userUpdate.height,
            weight = _state.value.userUpdate.weight,
            gender = _state.value.userUpdate.gender,
            fitnessGoal = _state.value.userUpdate.fitnessGoal
        )

        _state.update { state ->
            state.copy(
                userUpdate = state.userUpdate.copy(
                    caloriesIntake = newValues.calories,
                    neededProteins = newValues.protein,
                    neededFats = newValues.fat,
                    neededCarbohydrates = newValues.carbs
                )
            )
        }
    }

    private fun changeUserUpdateModelAge(newValue: String) {
        _state.update { state ->
            state.copy(
                userUpdate = state.userUpdate.copy(
                    age = newValue.toDouble().roundToInt()
                )
            )
        }
        updateUserFoodValues()
    }

    private fun changeUserUpdateModelHeight(newValue: String) {
        _state.update { state ->
            state.copy(
                userUpdate = state.userUpdate.copy(
                    height = newValue.toDouble()
                )
            )
        }
        updateUserFoodValues()
    }

    private fun changeUserUpdateModelName(newValue: String) {
        _state.update { state ->
            state.copy(
                userUpdate = state.userUpdate.copy(
                    name = newValue
                )
            )
        }
    }

    private fun changeUserUpdateModelPhone(newValue: String) {
        _state.update { state ->
            state.copy(
                userUpdate = state.userUpdate.copy(
                    phoneNumber = newValue
                )
            )
        }
    }

    private fun changeUserUpdateModelWeight(newValue: String) {
        _state.update { state ->
            state.copy(
                userUpdate = state.userUpdate.copy(
                    weight = newValue.toDouble()
                )
            )
        }
        updateUserFoodValues()
    }

    private fun updateActivityAddModelCalories(value: String) {
        _state.update { state ->
            state.copy(
                addingActivity = state.addingActivity.copy(calories = value)
            )
        }
    }

    private fun updateActivityAddModelDuration(value: String) {
        _state.update { state ->
            state.copy(
                addingActivity = state.addingActivity.copy(duration = value)
            )
        }
    }

    private fun updateActivityAddModelTitle(value: String) {
        _state.update { state ->
            state.copy(
                addingActivity = state.addingActivity.copy(title = value)
            )
        }
    }

    private fun updateHeartRateAddModelValue(value: String) {
        _state.update { state ->
            state.copy(
                addingHeartRate = state.addingHeartRate.copy(heartRate = value)
            )
        }
    }

    private fun updateWeightAddModelWeight(value: String) {
        _state.update { state ->
            state.copy(
                addingWeight = state.addingWeight.copy(weight = value)
            )
        }
    }

    private fun deleteActivity(selected: ActivityModelUi) {
        viewModelScope.launch {
            when (
                val res = deleteActivityUseCase.invoke(activity = selected.domainModel)
            ) {
                is CustomResultModelDomain.Success -> {
                    loadActivities()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
        }
    }

    private fun deleteHeartRate(selected: HeartRateModelUi) {
        viewModelScope.launch {
            when (
                val res = deleteHeartRateUseCase.invoke(heartRate = selected.domainModel)
            ) {
                is CustomResultModelDomain.Success -> {
                    loadHeartRates()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
        }
    }

    private fun deleteWeight(selected: WeightModelUi) {
        viewModelScope.launch {
            when (
                val res = deleteWeightUseCase.invoke(weight = selected.domainModel)
            ) {
                is CustomResultModelDomain.Success -> {
                    loadWeights()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
        }
    }

    private fun updateHeartRatesDatePickerVisibility() {
        _state.update {
            it.copy(
                isHeartRatesDatePickerVisible = !it.isHeartRatesDatePickerVisible
            )
        }
    }

    private fun updateWeightsDatePickerVisibility() {
        _state.update {
            it.copy(
                isWeightsDatePickerVisible = !it.isWeightsDatePickerVisible
            )
        }
    }

    private fun updateActivitiesDatePickerVisibility() {
        _state.update {
            it.copy(
                isActivitiesDatePickerVisible = !it.isActivitiesDatePickerVisible
            )
        }
    }

    private fun updateWeightsSelectedDate(date: Date?) {
        _state.update {
            it.copy(
                weightsSelectedDate = date
            )
        }
        loadWeights()
    }

    private fun updateHeartRatesSelectedDate(date: Date?) {
        _state.update {
            it.copy(
                heartRatesSelectedDate = date
            )
        }
        loadHeartRates()
    }

    private fun updateActivitiesSelectedDate(date: Date?) {
        _state.update {
            it.copy(
                activitiesSelectedDate = date
            )
        }
        loadActivities()
    }

    private fun updateWeightsAddDialogVisibility() {
        _state.update {
            it.copy(
                isWeightAddDialogVisible = !it.isWeightAddDialogVisible,
                addingWeight = AddingWeight()
            )
        }
    }

    private fun updateHeartRatesAddDialogVisibility() {
        _state.update {
            it.copy(
                isHeartRateAddDialogVisible = !it.isHeartRateAddDialogVisible,
                addingHeartRate = AddingHeartRate()
            )
        }
    }

    private fun updateActivitiesAddDialogVisibility() {
        _state.update {
            it.copy(
                isActivityAddDialogVisible = !it.isActivityAddDialogVisible,
                addingActivity = PersonalScreenState.AddingActivity()
            )
        }
    }

    private fun addActivity() {
        if (_state.value.addingActivity.duration.isEmpty() || _state.value.addingActivity.title.isEmpty()) {
            _event.emit(
                IPersonalScreenEvent.ShowToast(
                    CommonExceptionModelDomain.NotAllFieldsFilled.toUiString()
                )
            )
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isActivityUploading = true) }
            when (
                val res = addActivityUseCase.invoke(
                    activity = _state.value.addingActivity.toModelDomain()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    loadActivities()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isActivityUploading = false) }
            updateActivitiesAddDialogVisibility()
        }
    }

    private fun addHeartRate() {
        if (_state.value.addingHeartRate.heartRate.isEmpty()) {
            _event.emit(
                IPersonalScreenEvent.ShowToast(
                    CommonExceptionModelDomain.NotAllFieldsFilled.toUiString()
                )
            )
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isHeartRateUploading = true) }
            when (
                val res = addHeartRateUseCase.invoke(
                    heartRate = _state.value.addingHeartRate.toModelDomain()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    loadHeartRates()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isHeartRateUploading = false) }
            updateHeartRatesAddDialogVisibility()
        }
    }

    private fun addWeight() {
        if (_state.value.addingWeight.weight.isEmpty()) {
            _event.emit(
                IPersonalScreenEvent.ShowToast(
                    CommonExceptionModelDomain.NotAllFieldsFilled.toUiString()
                )
            )
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isWeightUploading = true) }
            when (
                val res = addWeightUseCase.invoke(
                    weight = _state.value.addingWeight.toModelDomain()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    loadWeights()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isWeightUploading = false) }
            updateWeightsAddDialogVisibility()
        }
    }

    private fun updateUser() {
        viewModelScope.launch {
            _state.update { it.copy(isUserUpdating = true) }
            when (
                val res = updateUserUseCase.invoke(
                    user = _state.value.userUpdate.toDomainModel()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    loadInitData()
                    changeUserUpdateDialogVisibility()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IPersonalScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isUserUpdating = false) }
        }
    }

    private fun changeUserUpdateDialogVisibility() {
        _state.update {
            it.copy(
                isUserUpdateDialogVisible = !it.isUserUpdateDialogVisible,
                userUpdate = it.user
            )
        }
    }

    private fun changeOption(option: PersonalScreenOption) {
        _state.update {
            it.copy(
                currentOption = option
            )
        }
    }

    private fun exitApp() {
        signOutUserUseCase.invoke()
    }

    private fun navigateBack() {
        _event.emit(
            IPersonalScreenEvent.NavigateBack
        )
    }
}