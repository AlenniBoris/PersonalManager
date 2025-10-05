package com.alenniboris.personalmanager.presentation.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IAddTaskUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IGetAllTasksUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IRemoveTaskUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.tasks.IUpdateTaskUseCase
import com.alenniboris.personalmanager.domain.usecase.logic.user.IGetCurrentUserUseCase
import com.alenniboris.personalmanager.domain.utils.SingleFlowEvent
import com.alenniboris.personalmanager.presentation.mapper.toUiString
import com.alenniboris.personalmanager.presentation.model.task.TaskModelUi
import com.alenniboris.personalmanager.presentation.model.task.toModelUi
import com.alenniboris.personalmanager.presentation.model.user.toModelUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TasksScreenViewModel @Inject constructor(
    private val addTaskUseCase: IAddTaskUseCase,
    private val getAllTasksUseCase: IGetAllTasksUseCase,
    private val removeTaskUseCase: IRemoveTaskUseCase,
    private val updateTaskUseCase: IUpdateTaskUseCase,
    private val getCurrentUserUseCase: IGetCurrentUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TasksScreenState())
    val state = _state.asStateFlow()

    private val _event = SingleFlowEvent<ITasksScreenEvent>(viewModelScope)
    val event = _event.flow

    private var _tasksLoadingJob: Job? = null

    init {
        loadTasks()
    }

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

    fun proceedIntent(intent: ITasksScreenIntent) {
        when (intent) {
            is ITasksScreenIntent.OpenPersonalScreen -> openPersonalScreen()
            is ITasksScreenIntent.UpdateScreenOption -> updateScreenOption(intent.option)
            is ITasksScreenIntent.UpdateAddTaskDialogVisibility -> updateAddTaskDialogVisibility()
            is ITasksScreenIntent.UpdateAddTaskDate -> updateAddTaskDate(intent.newValue)
            is ITasksScreenIntent.UpdateAddTaskDescription -> updateAddTaskDescription(intent.newValue)
            is ITasksScreenIntent.UpdateAddTaskPriority -> updateAddTaskPriority(intent.newValue)
            is ITasksScreenIntent.UpdateAddTaskTime -> updateAddTaskTime(intent.newValue)
            is ITasksScreenIntent.UpdateAddTaskTitle -> updateAddTaskTitle(intent.newValue)
            is ITasksScreenIntent.ProceedAddTaskAction -> proceedAddTaskAction()
            is ITasksScreenIntent.UpdateDatePickerVisibility -> updateDatePickerVisibility(intent.isEditor)
            is ITasksScreenIntent.UpdateTimePickerVisibility -> updateTimePickerVisibility(intent.isEditor)
            is ITasksScreenIntent.RemoveTask -> removeTask(intent.task)
            is ITasksScreenIntent.SelectEditedTask -> selectEditedTask(intent.task)
            is ITasksScreenIntent.ProceedUpdateTaskAction -> proceedUpdateTaskAction()
            is ITasksScreenIntent.UpdateEditedTaskDate -> updateEditedTaskDate(intent.newValue)
            is ITasksScreenIntent.UpdateEditedTaskDescription -> updateEditedTaskDescription(intent.newValue)
            is ITasksScreenIntent.UpdateEditedTaskPriority -> updateEditedTaskPriority(intent.newValue)
            is ITasksScreenIntent.UpdateEditedTaskStatus -> updateEditedTaskStatus(intent.newValue)
            is ITasksScreenIntent.UpdateEditedTaskTime -> updateEditedTaskTime(intent.newValue)
            is ITasksScreenIntent.UpdateEditedTaskTitle -> updateEditedTaskTitle(intent.newValue)
            is ITasksScreenIntent.UpdateSelectedTask -> updateSelectedTask(intent.task)
            is ITasksScreenIntent.UpdateDateFilterPickerVisibility -> updateDateFilterPickerVisibility()
            is ITasksScreenIntent.UpdateSelectedFilterDate -> updateSelectedFilterDate(intent.date)
            is ITasksScreenIntent.ChangeSettingsDialogVisibility -> changeSettingsDialogVisibility()
        }
    }

    private fun changeSettingsDialogVisibility() {
        _state.update { it.copy(isSettingsVisible = !it.isSettingsVisible) }
    }


    private fun openPersonalScreen() {
        _event.emit(ITasksScreenEvent.OpenPersonalScreen)
    }

    private fun updateSelectedFilterDate(date: Date?) {
        _state.update {
            it.copy(
                selectedFilterDate = date
            )
        }
        date?.let {
            updateDateFilterPickerVisibility()
        }
    }

    private fun updateDateFilterPickerVisibility() {
        _state.update {
            it.copy(
                isDateFilterPickerVisible = !it.isDateFilterPickerVisible
            )
        }
    }

    private fun updateSelectedTask(task: TaskModelUi?) {
        _state.update {
            it.copy(
                selectedTask = task
            )
        }
    }

    private fun updateEditedTaskStatus(newValue: TaskStatus) {
        _state.update {
            it.copy(
                editedTask = it.editedTask?.copy(
                    domainModel = it.editedTask.domainModel.copy(
                        status = newValue
                    )
                )
            )
        }
    }

    private fun updateEditedTaskTitle(newValue: String) {
        _state.update {
            it.copy(
                editedTask = it.editedTask?.copy(
                    domainModel = it.editedTask.domainModel.copy(
                        title = newValue
                    )
                )
            )
        }
    }

    private fun updateEditedTaskTime(newValue: Date) {
        _state.update {
            it.copy(
                editedTask = it.editedTask?.copy(
                    domainModel = it.editedTask.domainModel.copy(
                        dueTime = newValue
                    )
                )
            )
        }
        updateTimePickerVisibility()
    }

    private fun updateEditedTaskPriority(newValue: TaskPriority) {
        _state.update {
            it.copy(
                editedTask = it.editedTask?.copy(
                    domainModel = it.editedTask.domainModel.copy(
                        priority = newValue
                    )
                )
            )
        }
    }

    private fun updateEditedTaskDescription(newValue: String) {
        _state.update {
            it.copy(
                editedTask = it.editedTask?.copy(
                    domainModel = it.editedTask.domainModel.copy(
                        description = newValue
                    )
                )
            )
        }
    }

    private fun updateEditedTaskDate(newValue: Date) {
        _state.update {
            it.copy(
                editedTask = it.editedTask?.copy(
                    domainModel = it.editedTask.domainModel.copy(
                        dueDate = newValue
                    )
                )
            )
        }
        updateDatePickerVisibility()
    }

    private fun proceedUpdateTaskAction() {
        _state.value.editedTask?.let { task ->
            viewModelScope.launch {
                _state.update { it.copy(isTaskUploading = true) }

                when (
                    val result = updateTaskUseCase.invoke(
                        task = task.domainModel
                    )
                ) {
                    is CustomResultModelDomain.Success -> {
                        loadTasks()
                        selectEditedTask(null)
                    }

                    is CustomResultModelDomain.Error -> {
                        _event.emit(
                            ITasksScreenEvent.ShowToast(
                                result.exception.toUiString()
                            )
                        )
                    }
                }

                _state.update { it.copy(isTaskUploading = false) }
            }
        }
    }

    private fun selectEditedTask(task: TaskModelUi?) {
        _state.update {
            it.copy(
                editedTask = task
            )
        }
    }

    private fun removeTask(task: TaskModelUi) {
        viewModelScope.launch {
            when (
                val result = removeTaskUseCase.invoke(task.domainModel)
            ) {
                is CustomResultModelDomain.Success -> {
                    loadTasks()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ITasksScreenEvent.ShowToast(
                            result.exception.toUiString()
                        )
                    )
                }
            }
        }
    }

    private fun updateDatePickerVisibility(isEditor: Boolean = false) {
        _state.update {
            it.copy(
                isDatePickerVisible = !it.isDatePickerVisible,
                isEditor = isEditor
            )
        }
    }

    private fun updateTimePickerVisibility(isEditor: Boolean = false) {
        _state.update {
            it.copy(
                isTimePickerVisible = !it.isTimePickerVisible,
                isEditor = isEditor
            )
        }
    }

    private fun proceedAddTaskAction() {
        viewModelScope.launch {
            _state.update { it.copy(isTaskUploading = true) }

            when (
                val result = addTaskUseCase.invoke(
                    task = _state.value.addTaskData.toTaskDomainModel()
                )
            ) {
                is CustomResultModelDomain.Success -> {
                    loadTasks()
                    updateAddTaskDialogVisibility()
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ITasksScreenEvent.ShowToast(
                            result.exception.toUiString()
                        )
                    )
                }
            }

            _state.update { it.copy(isTaskUploading = false) }
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
                addTaskData = TasksScreenState.TaskAddingData()
            )
        }
    }

    private fun updateScreenOption(option: TasksScreenOption) {
        _state.update { state ->
            state.copy(
                currentScreenOption = option
            )
        }
    }

    private fun loadTasks() {
        _tasksLoadingJob?.cancel()
        _tasksLoadingJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            when (
                val res = getAllTasksUseCase.invoke()
            ) {
                is CustomResultModelDomain.Success -> {
                    _state.update { state ->
                        state.copy(
                            initTasks = res.result.map { it.toModelUi() }
                        )
                    }
                }

                is CustomResultModelDomain.Error -> {
                    _event.emit(
                        ITasksScreenEvent.ShowToast(
                            res.exception.toUiString()
                        )
                    )
                }
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}