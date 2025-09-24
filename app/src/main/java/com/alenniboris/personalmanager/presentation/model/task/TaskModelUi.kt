package com.alenniboris.personalmanager.presentation.model.task

import com.alenniboris.personalmanager.domain.model.task.TaskModelDomain
import com.alenniboris.personalmanager.presentation.mapper.toUiPicture
import com.alenniboris.personalmanager.presentation.uikit.utils.ScreensCommonUtils
import java.text.SimpleDateFormat
import java.util.Locale

data class TaskModelUi(
    val domainModel: TaskModelDomain
) {

    val pictureId: Int = domainModel.status.toUiPicture()

    val dueDateText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_DATE_PATTERN, Locale.getDefault()
        ).format(domainModel.dueDate)

    val dueTimeText: String =
        SimpleDateFormat(
            ScreensCommonUtils.SIMPLE_HOUR_PATTERN, Locale.getDefault()
        ).format(domainModel.dueTime)

    val createdTimeText: String =
        SimpleDateFormat(
            "${ScreensCommonUtils.SIMPLE_DATE_PATTERN}/${ScreensCommonUtils.SIMPLE_HOUR_PATTERN}",
            Locale.getDefault()
        ).format(domainModel.createdDate)
}

fun TaskModelDomain.toModelUi(): TaskModelUi =
    TaskModelUi(
        domainModel = this
    )