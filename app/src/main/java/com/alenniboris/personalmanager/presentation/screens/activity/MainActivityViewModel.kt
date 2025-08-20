package com.alenniboris.personalmanager.presentation.screens.activity

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.usecase.logic.IGetCurrentUserUseCase
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : ViewModel() {

    private val _isAuthenticatedFlow = MutableStateFlow(
        getCurrentUserUseCase.userFlow.value != null
    )
    val isAuthenticatedFlow = _isAuthenticatedFlow.asStateFlow()

    init {
        viewModelScope.launch {
            getCurrentUserUseCase.userFlow.collect { user ->
                Log.e("!!!!", (user != null).toString())
                _isAuthenticatedFlow.update { user != null }
            }
        }
    }
}