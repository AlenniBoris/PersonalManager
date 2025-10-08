package com.alenniboris.personalmanager.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.model.common.CommonExceptionModelDomain
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.usecase.logic.activity.IGetAllActivitiesUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.food.IGetFoodIntakeByDateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.food.IRecordFoodIntakeUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IAddHeartRateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.heart.IGetHeartRatesByDateUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IAddTaskUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IGetAllTasksUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location.IGetCurrentForecastUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IAddWeightUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weight.IGetAllWeightsUseCase
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.domain.utils.SingleFlowEvent
import com.alenniboris.personalmanager.domain.utils.stripTime
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.activity.toModelUi
import com.alenniboris.personalmanager.presentation.model.food.FoodIntakeAddModel
import com.alenniboris.personalmanager.presentation.model.food.toModelUi
import com.alenniboris.personalmanager.presentation.model.heart.AddingHeartRate
import com.alenniboris.personalmanager.presentation.model.heart.toModelUi
import com.alenniboris.personalmanager.presentation.model.task.TaskAddingData
import com.alenniboris.personalmanager.presentation.model.task.toModelUi
import com.alenniboris.personalmanager.presentation.model.user.toModelUi
import com.alenniboris.personalmanager.presentation.model.weather.toModelUi
import com.alenniboris.personalmanager.presentation.model.weight.AddingWeight
import com.alenniboris.personalmanager.presentation.model.weight.toModelUi
import com.alenniboris.personalmanager.presentation.uikit.utils.DatastoreRepository
import com.alenniboris.personalmanager.presentation.uikit.values.DatastoreValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase,
    private val getCurrentForecastUseCase: IGetCurrentForecastUseCase,
    private val getAllTasksUseCase: IGetAllTasksUseCase,
    private val addTaskUseCase: IAddTaskUseCase,
    private val getHeartRatesByDateUseCase: IGetHeartRatesByDateUseCase,
    private val getAllWeightsUseCase: IGetAllWeightsUseCase,
    private val recordFoodIntakeUseCase: IRecordFoodIntakeUseCase,
    private val addWeightUseCase: IAddWeightUseCase,
    private val addHeartRateUseCase: IAddHeartRateUseCase,
    private val getFoodIntakeByDateUseCase: IGetFoodIntakeByDateUseCase,
    private val datastoreRepository: DatastoreRepository,
    private val getAllActivitiesUseCase: IGetAllActivitiesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<IHomeScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _currentForecastJob: Job? = null
    private var _tasksJob: Job? = null
    private var _lastHeartRateJob: Job? = null
    private var _lastWeightJob: Job? = null
    private var _foodJob: Job? = null
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
        loadCurrentForecast()
        loadTasks()
        loadLastHeartRate()
        loadLastWeight()
        loadFoodData()
        loadActivitiesData()
    }

    private fun loadActivitiesData() {
        _activitiesJob?.cancel()
        _activitiesJob = viewModelScope.launch {
            _state.update { it.copy(isActivitiesLoading = true) }
            loadActivitiesDataInternal()
            _state.update { it.copy(isActivitiesLoading = false) }
        }
    }

    private suspend fun loadActivitiesDataInternal() {
        when (
            val res = getAllActivitiesUseCase.invoke()
        ) {
            is CustomResultModelDomain.Success -> {
                _state.update { state ->
                    state.copy(
                        activities = res.result.map { it.toModelUi() }
                    )
                }
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IHomeScreenEvent.ShowToast(
                        res.exception.toUiString()
                    )
                )
            }
        }
    }

    private fun loadFoodData() {
        _foodJob?.cancel()
        _foodJob = viewModelScope.launch {
            _state.update { it.copy(isFoodDataLoading = true) }
            loadFoodDataInternal()
            _state.update { it.copy(isFoodDataLoading = false) }
        }
    }

    private suspend fun loadFoodDataInternal() {
        when (
            val res = getFoodIntakeByDateUseCase.invoke(
                date = Calendar.getInstance().time.stripTime()
            )
        ) {
            is CustomResultModelDomain.Success -> {
                _state.update { state ->
                    state.copy(
                        listOfConsumedFood = res.result.map {
                            it.toModelUi()
                        }
                    )
                }
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IHomeScreenEvent.ShowToast(
                        res.exception.toUiString()
                    )
                )
            }
        }
    }

    private fun loadLastWeight() {
        _lastWeightJob?.cancel()
        _lastWeightJob = viewModelScope.launch {
            _state.update { it.copy(isWeightsLoading = true) }
            loadLastWeightInternal()
            _state.update { it.copy(isWeightsLoading = false) }
        }
    }

    private suspend fun loadLastWeightInternal() {
        when (
            val res = getAllWeightsUseCase.invoke()
        ) {
            is CustomResultModelDomain.Success -> {
                _state.update { state ->
                    state.copy(
                        lastWeight = res.result.firstOrNull()?.toModelUi()
                    )
                }
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IHomeScreenEvent.ShowToast(
                        res.exception.toUiString()
                    )
                )
            }
        }
    }

    private fun loadLastHeartRate() {
        _lastHeartRateJob?.cancel()
        _lastHeartRateJob = viewModelScope.launch {
            _state.update { it.copy(isHeartRatesLoading = true) }
            loadLastHeartRateInternal()
            _state.update { it.copy(isHeartRatesLoading = false) }
        }
    }

    private suspend fun loadLastHeartRateInternal() {
        when (
            val res = getHeartRatesByDateUseCase.invoke(
                date = Calendar.getInstance().time.stripTime()
            )
        ) {
            is CustomResultModelDomain.Success -> {
                _state.update { state ->
                    state.copy(
                        lastHeartRate = res.result.lastOrNull()?.toModelUi()
                    )
                }
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IHomeScreenEvent.ShowToast(
                        res.exception.toUiString()
                    )
                )
            }
        }
    }

    private fun loadTasks() {
        _tasksJob?.cancel()
        _tasksJob = viewModelScope.launch {
            _state.update { it.copy(isTasksListLoading = true) }
            loadTasksInternal()
            _state.update { it.copy(isTasksListLoading = false) }
        }
    }

    private suspend fun loadTasksInternal() {
        when (
            val res = getAllTasksUseCase.invoke()
        ) {
            is CustomResultModelDomain.Success -> {
                _state.update { state ->
                    state.copy(
                        tasksList = res.result.map {
                            it.toModelUi()
                        }
                    )
                }
            }

            is CustomResultModelDomain.Error -> {
                _event.emit(
                    IHomeScreenEvent.ShowToast(res.exception.toUiString())
                )
            }
        }
    }

    private fun loadCurrentForecast() {
        _currentForecastJob?.cancel()
        _currentForecastJob = viewModelScope.launch {
            _state.update { it.copy(isTemperatureLoading = true) }
            loadCurrentForecastInternal()
            _state.update { it.copy(isTemperatureLoading = false) }
        }
    }

    private suspend fun loadCurrentForecastInternal() {
        val lat = datastoreRepository.getData(DatastoreValues.LATITUDE)
        val lon = datastoreRepository.getData(DatastoreValues.LONGITUDE)
        if (lat != null && lon != null) {
            when (
                val res = getCurrentForecastUseCase.invoke(
                    lat = lat.toDouble(),
                    lon = lon.toDouble()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update {
                        it.copy(
                            currentWeatherForecast = res.result.toModelUi()
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHomeScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
        }
    }

    fun proceedIntent(intent: IHomeScreenIntent) {
        when (intent) {
            is IHomeScreenIntent.OpenPersonalScreen -> openPersonalScreen()
            is IHomeScreenIntent.ChangeSettingsDialogVisibility -> changeSettingsDialogVisibility()
            is IHomeScreenIntent.RefreshData -> refreshData()
            is IHomeScreenIntent.AddNewTask -> addNewTask()
            is IHomeScreenIntent.ChangeTaskAddDialogVisibility -> changeTaskAddDialogVisibility()
            is IHomeScreenIntent.UpdateAddTaskTitle -> updateAddTaskTitle(intent.newValue)
            is IHomeScreenIntent.UpdateAddTaskDescription -> updateAddTaskDescription(intent.newValue)
            is IHomeScreenIntent.UpdateAddTaskPriority -> updateAddTaskPriority(intent.newValue)
            is IHomeScreenIntent.UpdateAddTaskDate -> updateAddTaskDate(intent.newValue)
            is IHomeScreenIntent.UpdateAddTaskTime -> updateAddTaskTime(intent.newValue)
            is IHomeScreenIntent.UpdateDatePickerVisibility -> updateDatePickerVisibility()
            is IHomeScreenIntent.UpdateTimePickerVisibility -> updateTimePickerVisibility()
            is IHomeScreenIntent.AddHeartRate -> addHeartRate()
            is IHomeScreenIntent.AddWeight -> addWeight()
            is IHomeScreenIntent.UpdateHeartRateAddModelValue ->
                updateHeartRateAddModelValue(intent.value)

            is IHomeScreenIntent.UpdateHeartRatesAddDialogVisibility ->
                updateHeartRatesAddDialogVisibility()

            is IHomeScreenIntent.UpdateWeightAddModelWeight ->
                updateWeightAddModelWeight(intent.value)

            is IHomeScreenIntent.UpdateWeightsAddDialogVisibility ->
                updateWeightsAddDialogVisibility()

            is IHomeScreenIntent.ProceedFoodIntakeAdd -> proceedFoodIntakeAdd()
            is IHomeScreenIntent.UpdateFoodIntakeAddDialogVisibility -> updateFoodIntakeAddDialogVisibility()
            is IHomeScreenIntent.UpdateFoodIntakeAddModelCalories ->
                updateFoodIntakeAddModelCalories(intent.newValue)

            is IHomeScreenIntent.UpdateFoodIntakeAddModelCarbs ->
                updateFoodIntakeAddModelCarbs(intent.newValue)

            is IHomeScreenIntent.UpdateFoodIntakeAddModelFats ->
                updateFoodIntakeAddModelFats(intent.newValue)

            is IHomeScreenIntent.UpdateFoodIntakeAddModelProteins ->
                updateFoodIntakeAddModelProteins(intent.newValue)

            is IHomeScreenIntent.UpdateFoodIntakeAddModelTitle ->
                updateFoodIntakeAddModelTitle(intent.newValue)

            is IHomeScreenIntent.ProceedQuickAction -> proceedQuickAction(intent.action)
        }
    }

    private fun proceedQuickAction(action: HomeScreenQuickActions) {
        when (action) {
            HomeScreenQuickActions.AddTask -> updateAddTaskDialogVisibility()
            HomeScreenQuickActions.LogFood -> updateFoodIntakeAddDialogVisibility()
            HomeScreenQuickActions.WeightCheck -> updateWeightsAddDialogVisibility()
            HomeScreenQuickActions.AddHeartRate -> updateHeartRatesAddDialogVisibility()
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

    private fun proceedFoodIntakeAdd() {
        viewModelScope.launch {
            _state.update { it.copy(isFoodIntakeAddProceeding = true) }

            when (
                val res = recordFoodIntakeUseCase.invoke(
                    foodIntake = _state.value.foodIntakeAddModel.toFoodIntakeModelDomain()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    loadFoodData()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHomeScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }

            _state.update {
                it.copy(
                    isFoodIntakeAddProceeding = false
                )
            }
            updateFoodIntakeAddDialogVisibility()
        }
    }


    private fun updateFoodIntakeAddDialogVisibility() {
        _state.update {
            it.copy(
                isFoodIntakeAddDialogVisible = !it.isFoodIntakeAddDialogVisible,
                foodIntakeAddModel = FoodIntakeAddModel()
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

    private fun addWeight() {
        if (_state.value.addingWeight.weight.isEmpty()) {
            _event.emit(
                IHomeScreenEvent.ShowToast(
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
                    loadLastWeight()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHomeScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isWeightUploading = false) }
            updateWeightsAddDialogVisibility()
        }
    }

    private fun addHeartRate() {
        if (_state.value.addingHeartRate.heartRate.isEmpty()) {
            _event.emit(
                IHomeScreenEvent.ShowToast(
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
                    loadLastHeartRate()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHomeScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isHeartRateUploading = false) }
            updateHeartRatesAddDialogVisibility()
        }
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

    private fun changeTaskAddDialogVisibility() {
        _state.update {
            it.copy(
                isAddTaskDialogVisible = !it.isAddTaskDialogVisible,
                addTaskData = TaskAddingData()
            )
        }
    }

    private fun addNewTask() {
        viewModelScope.launch {
            _state.update { it.copy(isTaskUploading = true) }

            when (
                val result = addTaskUseCase.invoke(
                    task = _state.value.addTaskData.toTaskDomainModel()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    loadTasks()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        IHomeScreenEvent.ShowToast(
                            result.exception.toUiString()
                        )
                    )
                }
            }

            _state.update { it.copy(isTaskUploading = false) }
            updateAddTaskDialogVisibility()
        }
    }

    private fun updateAddTaskTitle(newValue: String) {
        _state.update { state ->
            state.copy(
                addTaskData = state.addTaskData.copy(
                    title = newValue
                )
            )
        }
    }

    private fun updateAddTaskTime(newValue: Date) {
        _state.update { state ->
            state.copy(
                addTaskData = state.addTaskData.copy(
                    dueTime = Date(newValue.time + state.addTaskData.dueDate.time)
                )
            )
        }
        updateTimePickerVisibility()
    }

    private fun updateAddTaskPriority(newValue: TaskPriority) {
        _state.update { state ->
            state.copy(
                addTaskData = state.addTaskData.copy(
                    priority = newValue
                )
            )
        }
    }

    private fun updateAddTaskDescription(newValue: String) {
        _state.update { state ->
            state.copy(
                addTaskData = state.addTaskData.copy(
                    description = newValue
                )
            )
        }
    }

    private fun updateAddTaskDate(newValue: Date) {
        _state.update { state ->
            state.copy(
                addTaskData = state.addTaskData.copy(
                    dueDate = newValue
                )
            )
        }
        updateDatePickerVisibility()
    }

    private fun updateAddTaskDialogVisibility() {
        _state.update {
            it.copy(
                isAddTaskDialogVisible = !it.isAddTaskDialogVisible,
                addTaskData = TaskAddingData()
            )
        }
    }

    private fun updateDatePickerVisibility() {
        _state.update {
            it.copy(
                isDatePickerVisible = !it.isDatePickerVisible
            )
        }
    }

    private fun updateTimePickerVisibility() {
        _state.update {
            it.copy(
                isTimePickerVisible = !it.isTimePickerVisible
            )
        }
    }

    private fun refreshData() {
        _currentForecastJob?.cancel()
        _tasksJob?.cancel()
        _lastHeartRateJob?.cancel()
        _lastWeightJob?.cancel()
        _foodJob?.cancel()
        _activitiesJob?.cancel()
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            loadCurrentForecastInternal()
            loadTasksInternal()
            loadLastHeartRateInternal()
            loadLastWeightInternal()
            loadFoodDataInternal()
            loadActivitiesDataInternal()
            _state.update { it.copy(isRefreshing = false) }
        }
    }

    private fun changeSettingsDialogVisibility() {
        _state.update { it.copy(isSettingsVisible = !it.isSettingsVisible) }
    }

    private fun openPersonalScreen() {
        _event.emit(
            IHomeScreenEvent.OpenPersonalScreen
        )
    }
}