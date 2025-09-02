package com.alenniboris.personalmanager.presentation.model

import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.presentation.mapper.toUiPicture
import java.text.SimpleDateFormat
import java.util.Locale

data class TaskModelUi(
    val domainModel: TaskModelDomain
) {

    val pictureId: Int = domainModel.status.toUiPicture()

    val dueDateText: String =
        SimpleDateFormat(
            "dd.MM.yyyy", Locale.getDefault()
        ).format(domainModel.dueDate)

    val dueTimeText: String =
        SimpleDateFormat(
            "HH:mm", Locale.getDefault()
        ).format(domainModel.dueTime)

    val createdTimeText: String =
        SimpleDateFormat(
            "dd.MM.yyyy/HH:mm", Locale.getDefault()
        ).format(domainModel.createdDate)
}

fun TaskModelDomain.toModelUi(): TaskModelUi =
    TaskModelUi(
        domainModel = this
    )