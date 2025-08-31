package com.alenniboris.personalmanager.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.utils.SingleFlowEvent
import com.alenniboris.personalmanager.presentation.screens.activity.IMainActivityEvent
import com.alenniboris.personalmanager.presentation.uikit.utils.PermissionType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<IHomeScreenEvent>(viewModelScope)
    val event = _event.flow

    fun proceedIntent(intent: IHomeScreenIntent) {
        when (intent) {
            is IHomeScreenIntent.OpenSettingsAndHidePermissionDialog -> openSettingsAndHidePermissionDialog()
            is IHomeScreenIntent.UpdateRequestedPermissionAndShowDialog -> updateRequestedPermissionAndShowDialog(
                intent.newRequestedPermission
            )
        }
    }

    private fun openSettingsAndHidePermissionDialog() {
        _event.emit(
            IHomeScreenEvent.OpenSettings
        )
        hidePermissionDialog()
    }

    private fun hidePermissionDialog() {
        _state.update {
            it.copy(
                isPermissionDialogVisible = false
            )
        }
    }

    private fun updateRequestedPermissionAndShowDialog(newPermission: PermissionType) {
        _state.update {
            it.copy(
                requestedPermission = newPermission,
                isPermissionDialogVisible = true
            )
        }
    }
}