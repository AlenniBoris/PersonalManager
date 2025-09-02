package com.alenniboris.personalmanager.presentation.screens.tasks.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.taskScreenComponentOuterPadding
import com.alenniboris.personalmanager.presentation.uikit.views.AppCustomButton
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreenDatePicker(
    onDismiss: () -> Unit,
    onSelected: (Date) -> Unit,
    datePickerState: DatePickerState = rememberDatePickerState()
) {

    DatePickerDialog(
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            AppCustomButton(
                modifier = Modifier
                    .padding(taskScreenComponentOuterPadding)
                    .fillMaxWidth(),
                onClick = {
                    datePickerState.selectedDateMillis?.let { selDate ->
                        onSelected(
                            Date(selDate)
                        )
                    }
                },
                text = stringResource(R.string.select_text),
                icon = painterResource(R.drawable.add_icon)
            )
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}