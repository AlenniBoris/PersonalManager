package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.model.heart.AddingHeartRate
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.addDialogProgressHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDialogItemPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor

@Composable
fun AppHeartRateAddingDialog(
    addingHeartRate: AddingHeartRate,
    isUploading: Boolean,
    onDismiss: () -> Unit,
    onHeartRate: (String) -> Unit,
    onAdd: () -> Unit
) {

    AlertDialog(
        dismissButton = {},
        confirmButton = {},
        onDismissRequest = {
            if (!isUploading) {
                onDismiss()
            }
        },
        containerColor = appColor,
        shape = appRoundedShape,
        text = {
            HeartRateAddDialogUi(
                addingHeartRate = addingHeartRate,
                isUploading = isUploading,
                onDismiss = onDismiss,
                onHeartRateChange = onHeartRate,
                onAdd = onAdd
            )
        }
    )
}

@Composable
private fun HeartRateAddDialogUi(
    addingHeartRate: AddingHeartRate,
    isUploading: Boolean,
    onDismiss: () -> Unit,
    onHeartRateChange: (String) -> Unit,
    onAdd: () -> Unit
) {

    if (isUploading) {
        AppProgressAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .height(addDialogProgressHeight)
        )
    } else {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.add_heart_rate_text),
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(appDialogItemPadding),
                text = stringResource(R.string.add_heart_rate_description),
                style = bodyStyle.copy(
                    color = appSubtleTextColor,
                    fontSize = appTextSize
                )
            )

            AppDoubleInputField(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth()
                    .clip(appRoundedShape)
                    .background(color = enterTextFieldColor),
                initialValue = addingHeartRate.heartRate,
                onValueChanged = { value ->
                    onHeartRateChange(value)
                },
                placeholder = stringResource(R.string.add_heart_rate_placeholder)
            )

            AppCustomButton(
                modifier = Modifier
                    .padding(appDialogItemPadding)
                    .fillMaxWidth(),
                onClick = {
                    onAdd()
                },
                text = stringResource(R.string.add_heart_rate_text),
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
}

@Composable
@Preview
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier.background(appColor)
            ) {
                HeartRateAddDialogUi(
                    addingHeartRate = AddingHeartRate(),
                    isUploading = false,
                    onDismiss = {},
                    onHeartRateChange = {},
                    onAdd = {}
                )
            }
        }
    }
}

@Composable
@Preview
private fun DarkTheme() {
    PersonalManagerTheme(
        darkTheme = true
    ) {
        Surface {
            Column(
                modifier = Modifier.background(appColor)
            ) {
                HeartRateAddDialogUi(
                    addingHeartRate = AddingHeartRate(),
                    isUploading = false,
                    onDismiss = {},
                    onHeartRateChange = {},
                    onAdd = {}
                )
            }
        }
    }
}