package com.alenniboris.personalmanager.presentation.screens.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(
        MainActivityState(
            isAuthenticated = getCurrentUserUseCase.userFlow.value != null
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.userFlow.collect { user ->
                _state.update { it.copy(isAuthenticated = user != null) }
            }
        }
    }
}