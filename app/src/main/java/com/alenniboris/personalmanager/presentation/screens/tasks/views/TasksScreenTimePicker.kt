package com.alenniboris.personalmanager.presentation.screens.tasks.views

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import java.util.Date
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreenTimePicker(
    onDismiss: () -> Unit,
    onSelected: (Date) -> Unit
) {

    AlertDialog(
        dismissButton = {},
        confirmButton = {},
        onDismissRequest = {
            onDismiss()
        },
        containerColor = appColor,
        shape = appRoundedShape,
        text = {

            val currentTime = Calendar.getInstance()
            val timePickerState = rememberTimePickerState(
                initialHour = currentTime[Calendar.HOUR_OF_DAY],
                initialMinute = currentTime[Calendar.MINUTE],
                is24Hour = true,
            )

            Column {
                TimePicker(
                    state = timePickerState
                )

                AppCustomButton(
                    modifier = Modifier
                        .padding(appDialogItemPadding)
                        .fillMaxWidth(),
                    onClick = {
                        onSelected(
                            Date(
                                (timePickerState.hour.hours + timePickerState.minute.minutes).inWholeMilliseconds
                            )
                        )
                    },
                    text = stringResource(R.string.select_text),
                    icon = painterResource(R.drawable.add_icon)
                )

                AppCustomButton(
                    modifier = Modifier
                        .padding(appDialogItemPadding)
                        .fillMaxWidth(),
                    onClick = {
                        onDismiss()
                    },
                    text = stringResource(R.string.cancel_text),
                    icon = painterResource(R.drawable.cancel_icon)
                )
            }
        }
    )
}