package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.taskScreenComponentOuterPadding
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDatePicker(
    onDismiss: () -> Unit,
    onSelected: (Date) -> Unit,
    datePickerState: DatePickerState = rememberDatePickerState()
) {

    DatePickerDialog(
        colors = DatePickerDefaults.colors().copy(
            containerColor = appColor
        ),
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
            state = datePickerState,
            colors = DatePickerDefaults.colors().copy(
                containerColor = appColor,
                titleContentColor = appMainTextColor,
                headlineContentColor = appMainTextColor,
                weekdayContentColor = appMainTextColor,
                subheadContentColor = appSubtleTextColor,
                navigationContentColor = appMainTextColor,
                yearContentColor = appSubtleTextColor,
                disabledYearContentColor = appSubtleTextColor,
                currentYearContentColor = appSubtleTextColor,
                selectedYearContentColor = appColor,
                disabledSelectedYearContentColor = appSubtleTextColor,
                selectedYearContainerColor = appMainTextColor,
                disabledSelectedYearContainerColor = appColor,
                dayContentColor = appSubtleTextColor,
                disabledDayContentColor = appSubtleTextColor,
                selectedDayContentColor = appColor,
                disabledSelectedDayContentColor = appSubtleTextColor,
                selectedDayContainerColor = appMainTextColor,
                disabledSelectedDayContainerColor = appSubtleTextColor,
                todayContentColor = appMainTextColor,
                todayDateBorderColor = appSubtleTextColor
            ),
        )
    }
}