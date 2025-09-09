package com.alenniboris.personalmanager.presentation.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location.IGetCurrentForecastUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location.IGetTodayWeatherForecastUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.weather_and_location.IGetWeekWeatherForecastUseCase
import com.alenniboris.personalmanager.domain.utils.LogPrinter
import com.alenniboris.personalmanager.domain.utils.SingleFlowEvent
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.user.toModelUi
import com.alenniboris.personalmanager.presentation.model.weather.DayWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.weather.HourWeatherForecastModelUi
import com.alenniboris.personalmanager.presentation.model.weather.toModelUi
import com.alenniboris.personalmanager.presentation.uikit.utils.DatastoreRepository
import com.alenniboris.personalmanager.presentation.uikit.values.DatastoreValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(
    private val getCurrentWeatherForecastUseCase: IGetCurrentForecastUseCase,
    private val getTodayWeatherForecastUseCase: IGetTodayWeatherForecastUseCase,
    private val getWeekWeatherForecastUseCase: IGetWeekWeatherForecastUseCase,
    private val datastore: DatastoreRepository,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WeatherScreenState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<IWeatherScreenEvent>(viewModelScope)
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

    init {
        loadCurrentForecast()
        loadHourForecast()
        loadWeekForecast()
    }

    private fun loadWeekForecast() {
        viewModelScope.launch {
            _state.update { it.copy(isWeekForecastLoading = true) }

            val lat = datastore.getData(key = DatastoreValues.LATITUDE)?.toDouble()
            val lon = datastore.getData(key = DatastoreValues.LONGITUDE)?.toDouble()

            if (lat != null && lon != null) {
                when (
                    val res = getWeekWeatherForecastUseCase.invoke(lat = lat, lon = lon)
                ) {
                    is CustomResultModelDomain.Success -> {
                        _state.update { state ->
                            state.copy(
                                weekWeatherForecast = res.result.map { it.toModelUi() }
                            )
                        }
                    }

                    is CustomResultModelDomain.Error -> {
                        LogPrinter.printLog("!!!", res.exception.stackTraceToString())
                        _event.emit(
                            IWeatherScreenEvent.ShowToast(
                                res.exception.toUiString()
                            )
                        )
                    }
                }
            }

            _state.update { it.copy(isWeekForecastLoading = false) }
        }
    }

    private fun loadHourForecast() {
        viewModelScope.launch {
            _state.update { it.copy(isHourForecastLoading = true) }

            val lat = datastore.getData(key = DatastoreValues.LATITUDE)?.toDouble()
            val lon = datastore.getData(key = DatastoreValues.LONGITUDE)?.toDouble()

            if (lat != null && lon != null) {
                when (
                    val res = getTodayWeatherForecastUseCase.invoke(lat = lat, lon = lon)
                ) {
                    is CustomResultModelDomain.Success -> {
                        _state.update { state ->
                            state.copy(
                                hourWeatherForecast = res.result.map { it.toModelUi() }
                            )
                        }
                    }

                    is CustomResultModelDomain.Error -> {
                        LogPrinter.printLog("!!!", res.exception.stackTraceToString())
                        _event.emit(
                            IWeatherScreenEvent.ShowToast(
                                res.exception.toUiString()
                            )
                        )
                    }
                }
            }

            _state.update { it.copy(isHourForecastLoading = false) }
        }
    }

    private fun loadCurrentForecast() {
        viewModelScope.launch {
            _state.update { it.copy(isCurrentForecastLoading = true) }

            val lat = datastore.getData(key = DatastoreValues.LATITUDE)?.toDouble()
            val lon = datastore.getData(key = DatastoreValues.LONGITUDE)?.toDouble()

            if (lat != null && lon != null) {
                when (
                    val res = getCurrentWeatherForecastUseCase.invoke(lat = lat, lon = lon)
                ) {
                    is CustomResultModelDomain.Success -> {
                        _state.update {
                            it.copy(
                                currentWeatherForecast = res.result.toModelUi()
                            )
                        }
                    }

                    is CustomResultModelDomain.Error -> {
                        LogPrinter.printLog("!!!", res.exception.stackTraceToString())
                        _event.emit(
                            IWeatherScreenEvent.ShowToast(
                                res.exception.toUiString()
                            )
                        )
                    }
                }
            }

            _state.update { it.copy(isCurrentForecastLoading = false) }
        }
    }

    fun proceedIntent(intent: IWeatherScreenIntent) {
        when (intent) {
            is IWeatherScreenIntent.ChangeViewedOption -> changeViewedOption(intent.option)
            is IWeatherScreenIntent.UpdateSelectedDay -> updateSelectedDay(intent.selected)
            is IWeatherScreenIntent.UpdateSelectedHour -> updateSelectedHour(intent.selected)
        }
    }

    private fun updateSelectedHour(hourForecast: HourWeatherForecastModelUi?) {
        _state.update {
            it.copy(
                selectedHourForecast = hourForecast
            )
        }
    }

    private fun updateSelectedDay(dayForecast: DayWeatherForecastModelUi?) {
        _state.update {
            it.copy(
                selectedDayForecast = dayForecast
            )
        }
    }

    private fun changeViewedOption(option: WeatherScreenOptions) {
        _state.update { it.copy(currentOption = option) }
    }
}