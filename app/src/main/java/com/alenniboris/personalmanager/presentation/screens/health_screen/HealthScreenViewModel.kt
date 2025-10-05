package com.alenniboris.personalmanager.presentation.screens.health_screen

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.usecase.logic.food.IGetFoodIntakeByDateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.food.IRecordFoodIntakeUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.food.IRemoveFoodIntakeUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.food.IUpdateFoodIntakeUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.health.IGetTodayHealthStatisticsUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IGetHeartRatesByDateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IGetWeightsListByDateRangeUseCase
import com.alenniboris.personalmanager.domain.utils.SingleFlowEvent
import com.alenniboris.personalmanager.domain.utils.stripTime
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeModelUi
import com.alenniboris.personalmanager.presentation.model.food.toModelUi
import com.alenniboris.personalmanager.presentation.model.health.toModelUi
import com.alenniboris.personalmanager.presentation.model.heart.toModelUi
import com.alenniboris.personalmanager.presentation.model.user.toModelUi
import com.alenniboris.personalmanager.presentation.model.weight.toModelUi
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
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

@HiltViewModel
class HealthScreenViewModel @Inject constructor(
    private val getTodayHealthStatistics: IGetTodayHealthStatisticsUseCase,
    private val getHeartRatesByDateUseCase: IGetHeartRatesByDateUseCase,
    private val getWeightsListByDateRangeUseCase: IGetWeightsListByDateRangeUseCase,
    private val getFoodIntakeByDateUseCase: IGetFoodIntakeByDateUseCase,
    private val recordFoodIntakeUseCase: IRecordFoodIntakeUseCase,
    private val removeFoodIntakeUseCase: IRemoveFoodIntakeUseCase,
    private val updateFoodIntakeUseCase: IUpdateFoodIntakeUseCase,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HealthScreenState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<IHealthScreenEvent>(viewModelScope)
    val event = _event.flow

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.userFlow.collect { user ->
                _state.update {
                    it.copy(
                        user = user?.toModelUi()
                    )
                }
            }
        }
    }

    private var _todayHealthStatisticsLoadingJob: Job? = null
    private var _weightDataLoadingJob: Job? = null
    private var _heartDataLoadingJob: Job? = null
    private var _foodDataLoadingJob: Job? = null

    init {
        viewModelScope.launch {
            _state.map { it.screenOption }
                .buffer(onBufferOverflow = BufferOverflow.DROP_OLDEST)
                .distinctUntilChanged()
                .collect { option ->
                    when (option) {
                        HealthScreenOption.Overview -> loadTodayHealthStatistics()
                        HealthScreenOption.Weight -> loadWeightData()
                        HealthScreenOption.Activity -> loadHeartRateData()
                        HealthScreenOption.Nutrition -> loadFoodData()
                    }
                }
        }
    }

    fun proceedIntent(intent: IHealthScreenIntent) {
        when (intent) {
            is IHealthScreenIntent.OpenPersonalScreen -> openPersonalScreen()
            is IHealthScreenIntent.ChangeScreenOption -> changeScreenOption(intent.option)
            is IHealthScreenIntent.ProceedFoodIntakeAdd -> proceedFoodIntakeAdd()
            is IHealthScreenIntent.ProceedFoodIntakeDelete ->
                proceedFoodIntakeDelete(intent.foodIntake)

            is IHealthScreenIntent.ProceedFoodIntakeUpdate -> proceedFoodIntakeUpdate()
            is IHealthScreenIntent.UpdateFoodIntakeAddDialogVisibility -> updateFoodIntakeAddDialogVisibility()
            is IHealthScreenIntent.UpdateFoodIntakeAddModelCalories ->
                updateFoodIntakeAddModelCalories(intent.newValue)

            is IHealthScreenIntent.UpdateFoodIntakeAddModelCarbs ->
                updateFoodIntakeAddModelCarbs(intent.newValue)

            is IHealthScreenIntent.UpdateFoodIntakeAddModelFats ->
                updateFoodIntakeAddModelFats(intent.newValue)

            is IHealthScreenIntent.UpdateFoodIntakeAddModelProteins ->
                updateFoodIntakeAddModelProteins(intent.newValue)

            is IHealthScreenIntent.UpdateFoodIntakeAddModelTitle ->
                updateFoodIntakeAddModelTitle(intent.newValue)

            is IHealthScreenIntent.UpdateFoodIntakeDatePickerVisibility ->
                updateFoodIntakeDatePickerVisibility()

            is IHealthScreenIntent.UpdateHeartRateChartDate ->
                updateHeartRateChartDate(intent.date)

            is IHealthScreenIntent.UpdateHeartRateChartDateToDefault ->
                updateHeartRateChartDateToDefault()

            is IHealthScreenIntent.UpdateHeartRateDatePickerVisibility ->
                updateHeartRateDatePickerVisibility()

            is IHealthScreenIntent.UpdateWeightChartStartDatePickerVisibility ->
                updateWeightChartStartDatePickerVisibility()

            is IHealthScreenIntent.UpdateWeightChartEndDatePickerVisibility ->
                updateWeightChartEndDatePickerVisibility()

            is IHealthScreenIntent.UpdateWeightChartEndDate ->
                updateWeightChartEndDate(intent.date)

            is IHealthScreenIntent.UpdateWeightChartStartDate ->
                updateWeightChartStartDate(intent.date)

            is IHealthScreenIntent.UpdateWeightChartEndDateToDefault ->
                updateWeightChartEndDateToDefault()

            is IHealthScreenIntent.UpdateWeightChartStartDateToDefault ->
                updateWeightChartStartDateToDefault()

            is IHealthScreenIntent.UpdatedFoodIntakeDetailsSelected ->
                updatedFoodIntakeDetailsSelected(intent.foodIntake)

            is IHealthScreenIntent.UpdatedFoodIntakeUpdateSelected ->
                updatedFoodIntakeUpdateSelected(intent.foodIntake)

            is IHealthScreenIntent.UpdateFoodIntakeFilterDate -> updateFoodIntakeFilterDate(intent.date)
            IHealthScreenIntent.UpdateFoodIntakeFilterDateToDefault -> updateFoodIntakeFilterDateToDefault()

            is IHealthScreenIntent.UpdateFoodIntakeUpdateModelCalories ->
                updateFoodIntakeUpdateModelCalories(intent.newValue)

            is IHealthScreenIntent.UpdateFoodIntakeUpdateModelCarbs ->
                updateFoodIntakeUpdateModelCarbs(intent.newValue)

            is IHealthScreenIntent.UpdateFoodIntakeUpdateModelFats ->
                updateFoodIntakeUpdateModelFats(intent.newValue)

            is IHealthScreenIntent.UpdateFoodIntakeUpdateModelProteins ->
                updateFoodIntakeUpdateModelProteins(intent.newValue)

            is IHealthScreenIntent.UpdateFoodIntakeUpdateModelTitle ->
                updateFoodIntakeUpdateModelTitle(intent.newValue)
            is IHealthScreenIntent.ChangeSettingsDialogVisibility ->
                changeSettingsDialogVisibility()
        }
    }

    private fun changeSettingsDialogVisibility() {
        _state.update { it.copy(isSettingsVisible = !it.isSettingsVisible) }
    }

    private fun openPersonalScreen() {
        _event.emit(IHealthScreenEvent.OpenPersonalScreen)
    }

    private fun updateFoodIntakeFilterDateToDefault() {
        _state.update {
            it.copy(foodIntakeDate = Calendar.getInstance().time.stripTime())
        }
        loadFoodData()
    }

    private fun updateFoodIntakeFilterDate(date: Date) {
        _state.update {
            it.copy(foodIntakeDate = date)
        }
        updateFoodIntakeDatePickerVisibility()
        loadFoodData()
    }

    private fun updatedFoodIntakeUpdateSelected(newValue: FoodIntakeModelUi?) {
        _state.update {
            it.copy(
                foodIntakeUpdateSelected = newValue
            )
        }
    }


    private fun updatedFoodIntakeDetailsSelected(newValue: FoodIntakeModelUi?) {
        _state.update {
            it.copy(
                foodIntakeDetailsSelected = newValue
            )
        }
    }

    private fun updateWeightChartStartDate(newValue: Date) {
        _state.update {
            it.copy(
                weightChartStartDate = newValue ?: ScreensCommonUtils.getDateWeekAgo()
            )
        }
        loadWeightData()
        updateWeightChartStartDatePickerVisibility()
    }

    private fun updateWeightChartStartDateToDefault() {
        _state.update {
            it.copy(
                weightChartStartDate = ScreensCommonUtils.getDateWeekAgo()
            )
        }
        loadWeightData()
    }

    private fun updateWeightChartEndDate(newValue: Date) {
        _state.update {
            it.copy(
                weightChartEndDate = newValue
            )
        }
        loadWeightData()
        updateWeightChartEndDatePickerVisibility()
    }

    private fun updateWeightChartEndDateToDefault() {
        _state.update {
            it.copy(
                weightChartStartDate = Calendar.getInstance().time
            )
        }
        loadWeightData()
    }


    private fun updateWeightChartStartDatePickerVisibility() {
        _state.update {
            it.copy(
                isWeightChartStartDatePickerVisible = !it.isWeightChartStartDatePickerVisible
            )
        }
    }

    private fun updateWeightChartEndDatePickerVisibility() {
        _state.update {
            it.copy(
                isWeightChartEndDatePickerVisible = !it.isWeightChartEndDatePickerVisible
            )
        }
    }

    private fun updateHeartRateDatePickerVisibility() {
        _state.update {
            it.copy(
                isHeartRateDatePickerVisible = !it.isHeartRateDatePickerVisible
            )
        }
    }

    private fun updateHeartRateChartDate(newValue: Date) {
        _state.update {
            it.copy(
                heartRateChartDate = newValue
            )
        }
        updateHeartRateDatePickerVisibility()
        loadHeartRateData()
    }

    private fun updateHeartRateChartDateToDefault() {
        _state.update {
            it.copy(
                heartRateChartDate = Calendar.getInstance().time.stripTime()
            )
        }
        loadHeartRateData()
    }

    private fun updateFoodIntakeDatePickerVisibility() {
        _state.update {
            it.copy(
                isFoodIntakeDatePickerVisible = !it.isFoodIntakeDatePickerVisible
            )
        }
    }

    private fun updateFoodIntakeUpdateModelTitle(newValue: String) {
        _state.value.foodIntakeUpdateSelected?.let { food ->
            _state.update { state ->
                state.copy(
                    foodIntakeUpdateSelected = food.copy(
                        domainModel = food.domainModel.copy(
                            title = newValue
                        )
                    )
                )
            }
        }
    }

    private fun updateFoodIntakeUpdateModelProteins(newValue: String) {
        _state.value.foodIntakeUpdateSelected?.let { food ->
            _state.update { state ->
                state.copy(
                    foodIntakeUpdateSelected = food.copy(
                        domainModel = food.domainModel.copy(
                            proteins = newValue.toDouble()
                        )
                    )
                )
            }
        }
    }

    private fun updateFoodIntakeUpdateModelFats(newValue: String) {
        _state.value.foodIntakeUpdateSelected?.let { food ->
            _state.update { state ->
                state.copy(
                    foodIntakeUpdateSelected = food.copy(
                        domainModel = food.domainModel.copy(
                            fats = newValue.toDouble()
                        )
                    )
                )
            }
        }
    }

    private fun updateFoodIntakeUpdateModelCarbs(newValue: String) {
        _state.value.foodIntakeUpdateSelected?.let { food ->
            _state.update { state ->
                state.copy(
                    foodIntakeUpdateSelected = food.copy(
                        domainModel = food.domainModel.copy(
                            carbohydrates = newValue.toDouble()
                        )
                    )
                )
            }
        }
    }

    private fun updateFoodIntakeUpdateModelCalories(newValue: String) {
        _state.value.foodIntakeUpdateSelected?.let { food ->
            _state.update { state ->
                state.copy(
                    foodIntakeUpdateSelected = food.copy(
                        domainModel = food.domainModel.copy(
                            calories = newValue.toDouble()
                        )
                    )
                )
            }
        }
    }


    private fun updateFoodIntakeAddModelTitle(newValue: String) {
        _state.update { state ->
            state.copy(
                foodIntakeAddModel = state.foodIntakeAddModel.copy(
                    title = newValue
                )
            )
        }
    }

    private fun updateFoodIntakeAddModelProteins(newValue: String) {
        _state.update { state ->
            state.copy(
                foodIntakeAddModel = state.foodIntakeAddModel.copy(
                    proteins = newValue
                )
            )
        }
    }

    private fun updateFoodIntakeAddModelFats(newValue: String) {
        _state.update { state ->
            state.copy(
                foodIntakeAddModel = state.foodIntakeAddModel.copy(
                    fats = newValue
                )
            )
        }
    }

    private fun updateFoodIntakeAddModelCarbs(newValue: String) {
        _state.update { state ->
            state.copy(
                foodIntakeAddModel = state.foodIntakeAddModel.copy(
                    carbohydrates = newValue
                )
            )
        }
    }

    private fun updateFoodIntakeAddModelCalories(newValue: String) {
        _state.update { state ->
            state.copy(
                foodIntakeAddModel = state.foodIntakeAddModel.copy(
                    calories = newValue
                )
            )
        }
    }

    private fun updateFoodIntakeAddDialogVisibility() {
        _state.update {
            it.copy(
                isFoodIntakeAddDialogVisible = !it.isFoodIntakeAddDialogVisible,
                foodIntakeAddModel = HealthScreenState.FoodIntakeAddModel()
            )
        }
    }

    private fun proceedFoodIntakeUpdate() {
        _state.value.foodIntakeUpdateSelected?.let { intake ->
            viewModelScope.launch {
                _state.update { it.copy(isFoodIntakeUpdateProceeding = true) }
                when (
                    val res = updateFoodIntakeUseCase.invoke(
                        updatedIntake = intake.domainModel
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        updatedFoodIntakeUpdateSelected(null)
                        loadFoodData()
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            IHealthScreenEvent.ShowToast(
                                res.exception.toUiString()
                            )
                        )
                    }
                }
                _state.update { it.copy(isFoodIntakeUpdateProceeding = false) }
            }
        }
    }

    private fun proceedFoodIntakeDelete(foodIntake: FoodIntakeModelUi) {
        viewModelScope.launch {
            when (
                val res = removeFoodIntakeUseCase.invoke(
                    foodIntake = foodIntake.domainModel
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    loadFoodData()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHealthScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
        }
    }

    private fun proceedFoodIntakeAdd() {
        viewModelScope.launch {
            _state.update { it.copy(isFoodIntakeAddProceeding = true) }

            when (
                val res = recordFoodIntakeUseCase.invoke(
                    foodIntake = _state.value.foodIntakeAddModel.toFoodIntakeModelDomain()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update {
                        it.copy(
                            isFoodIntakeAddDialogVisible = false
                        )
                    }
                    loadFoodData()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHealthScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }

            _state.update { it.copy(isFoodIntakeAddProceeding = false) }
        }
    }

    private fun loadTodayHealthStatistics() {
        _todayHealthStatisticsLoadingJob?.cancel()
        _todayHealthStatisticsLoadingJob = viewModelScope.launch {
            _state.update { it.copy(isTodayStatisticsLoading = true) }

            when (
                val res = getTodayHealthStatistics.invoke(
                    date = Calendar.getInstance().time.stripTime()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update { state ->
                        state.copy(
                            todayHealthStatistics = res.result.toModelUi()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHealthScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }

            _state.update { it.copy(isTodayStatisticsLoading = false) }
        }
    }

    private fun loadWeightData() {
        _weightDataLoadingJob?.cancel()
        _weightDataLoadingJob = viewModelScope.launch {
            _state.update { it.copy(isWeightDataLoading = true) }

            when (
                val res = getWeightsListByDateRangeUseCase.invoke(
                    startDate = _state.value.weightChartStartDate,
                    endDate = _state.value.weightChartEndDate
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update { state ->
                        state.copy(
                            weightChartList = res.result.map { it.toModelUi() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHealthScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }

            _state.update { it.copy(isWeightDataLoading = false) }
        }
    }

    private fun loadHeartRateData() {
        _heartDataLoadingJob?.cancel()
        _heartDataLoadingJob = viewModelScope.launch {
            _state.update { it.copy(isHeartRateDataLoading = true) }

            when (
                val res = getHeartRatesByDateUseCase.invoke(
                    date = _state.value.heartRateChartDate
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update { state ->
                        state.copy(
                            heartRateChartList = res.result.map { it.toModelUi() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHealthScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }

            _state.update { it.copy(isHeartRateDataLoading = false) }
        }
    }

    private fun loadFoodData() {
        _foodDataLoadingJob?.cancel()
        _foodDataLoadingJob = viewModelScope.launch {
            _state.update { it.copy(isFoodDataLoading = true) }

            when (
                val res = getFoodIntakeByDateUseCase.invoke(
                    date = _state.value.foodIntakeDate
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update { state ->
                        state.copy(
                            foodIntakeList = res.result.map { it.toModelUi() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHealthScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }

            _state.update { it.copy(isFoodDataLoading = false) }
        }
    }

    private fun changeScreenOption(option: HealthScreenOption) {
        _state.update {
            it.copy(
                screenOption = option
            )
        }
    }
}