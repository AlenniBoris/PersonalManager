package com.alenniboris.personalmanager.presentation.screens.tasks

import android.os.Build
import androidx.annotation.RequiresApi
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.domain.model.task.TaskPriority
import com.alenniboris.personalmanager.domain.model.task.TaskStatus
import com.alenniboris.personalmanager.domain.utils.stripTime
import com.alenniboris.personalmanager.presentation.mapper.toDayType
import com.alenniboris.personalmanager.presentation.model.task.TaskModelUi
import com.alenniboris.personalmanager.presentation.model.user.UserModelUi
import com.alenniboris.personalmanager.presentation.uikit.model.PieSegment
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenCompletedTaskColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenSkippedTaskColor
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenUpcomingTaskColor
import com.alenniboris.personalmanager.presentation.uikit.values.toUiString
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class TasksScreenState(
    val initTasks: List<TaskModelUi> = emptyList(),
    val isLoading: Boolean = false,
    val screenOptions: List<TasksScreenOption> = TasksScreenOption.entries.toList(),
    val currentScreenOption: TasksScreenOption = TasksScreenOption.All_tasks,
    val user: UserModelUi? = null,
    val isAddTaskDialogVisible: Boolean = false,
    val isTaskUploading: Boolean = false,
    val addTaskData: TaskAddingData = TaskAddingData(),
    val isDatePickerVisible: Boolean = false,
    val isTimePickerVisible: Boolean = false,
    val editedTask: TaskModelUi? = null,
    val isEditor: Boolean = false,
    val selectedTask: TaskModelUi? = null,
    val isDateFilterPickerVisible: Boolean = false,
    val selectedFilterDate: Date? = null
) {

    val selectedFilterDateText: String? = selectedFilterDate?.let {
        SimpleDateFormat(
            "dd.MM.yyyy", Locale.getDefault()
        ).format(selectedFilterDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    val selectedFilterDateDayText: Int? = selectedFilterDate?.let {
        selectedFilterDate.toDayType().toUiString()
    }


    data class TaskAddingData(
        val id: String = "",
        val userId: String = "",
        val title: String = "",
        val description: String = "",
        val dueDate: Date = Calendar.getInstance().time.stripTime(),
        val dueTime: Date = Calendar.getInstance().time.stripTime(),
        val createdDate: Date = Calendar.getInstance().time,
        val priority: TaskPriority = TaskPriority.Low,
        val status: TaskStatus = TaskStatus.Pending
    ) {
        val selectedDateText: String = SimpleDateFormat(
            "dd.MM.yyyy", Locale.getDefault()
        ).format(this.dueDate)
        val selectedTimeText: String = SimpleDateFormat(
            "HH:mm", Locale.getDefault()
        ).format(this.dueTime)

        fun toTaskDomainModel(): TaskModelDomain =
            TaskModelDomain(
                id = this.id,
                userId = this.userId,
                title = this.title,
                description = this.description,
                dueDate = this.dueDate,
                dueTime = this.dueTime,
                createdDate = this.createdDate,
                priority = this.priority,
                status = this.status
            )
    }

    val tasks: List<TaskModelUi> = initTasks.filter { task ->
        when (currentScreenOption) {
            TasksScreenOption.All_tasks ->
                selectedFilterDate?.let { task.domainModel.dueDate == selectedFilterDate } ?: true

            TasksScreenOption.Today ->
                task.domainModel.dueDate.stripTime() == Calendar.getInstance().time.stripTime()

            TasksScreenOption.Upcoming ->
                (selectedFilterDate?.let { task.domainModel.dueDate == selectedFilterDate }) ?: true
                        && task.domainModel.status == TaskStatus.Pending

            TasksScreenOption.Skipped ->
                (selectedFilterDate?.let { task.domainModel.dueDate == selectedFilterDate }) ?: true
                        && task.domainModel.status == TaskStatus.Skipped
        }
    }

    // Таски, что сегодня (число)
    val listOfTodayTasks: List<TaskModelUi> = initTasks.filter {
        it.domainModel.dueDate.stripTime() == Calendar.getInstance().time.stripTime()
    }
    val numberOfTodayTasks: String = listOfTodayTasks.size.toString()

    // таски сеодня, но уже сделаны
    val listOfTodayCompletedTodayTasks = initTasks.filter {
        it.domainModel.dueDate.stripTime() == Calendar.getInstance().time.stripTime()
                && it.domainModel.status == TaskStatus.Completed
    }
    val numberOfCompletedTodayTasks: String = listOfTodayCompletedTodayTasks.size.toString()
    val completedTodayPieSegment = PieSegment(
        value = (listOfTodayCompletedTodayTasks.size.toFloat() / listOfTodayTasks.size.toFloat()) * 100,
        color = tasksScreenCompletedTaskColor,
        labelId = R.string.tasks_completed_section_text
    )

    // таски сегодня, что сделать в будущем
    val listOfTodayUpcomingTasks = initTasks.filter {
        it.domainModel.dueDate.stripTime() == Calendar.getInstance().time.stripTime() &&
                it.domainModel.status == TaskStatus.Pending
    }
    val numberOfTodayUpcomingTasks: String = listOfTodayUpcomingTasks.size.toString()
    val upcomingTodayPieSegment = PieSegment(
        value = (listOfTodayUpcomingTasks.size.toFloat() / listOfTodayTasks.size.toFloat()) * 100,
        color = tasksScreenUpcomingTaskColor,
        labelId = R.string.tasks_upcoming_section_text
    )

    // таски сегодня, что уже пропущены, не сделаны
    val listOfTodaySkippedTasks = initTasks.filter {
        it.domainModel.dueDate.stripTime() == Calendar.getInstance().time.stripTime() &&
                it.domainModel.status == TaskStatus.Skipped
    }
    val numberOfTodaySkippedTasks: String = listOfTodaySkippedTasks.size.toString()
    val skippedTodayPieSegment = PieSegment(
        value = (listOfTodaySkippedTasks.size.toFloat() / listOfTodayTasks.size.toFloat()) * 100,
        color = tasksScreenSkippedTaskColor,
        labelId = R.string.tasks_skipped_section_text
    )

    val listOfTodayPieSegments = listOf(
        completedTodayPieSegment,
        upcomingTodayPieSegment,
        skippedTodayPieSegment
    )
}


