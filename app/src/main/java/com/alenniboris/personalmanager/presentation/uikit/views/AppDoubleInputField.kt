package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.enterValueTextFieldInnerBoxPadding

@Composable
fun AppDoubleInputField(
    modifier: Modifier = Modifier,
    initialValue: String,
    onValueChanged: (String) -> Unit,
    placeholder: String
) {
    var text by remember { mutableStateOf(initialValue) }
    var hasFocus by remember { mutableStateOf(false) }

    BasicTextField(
        value = text,
        onValueChange = { newValue ->
            if (newValue.isEmpty()) {
                text = newValue
                return@BasicTextField
            }

            val normalized = newValue.replace(',', '.')

            normalized.toDoubleOrNull()?.let { validDouble ->
                text = newValue
                onValueChanged(normalized)
            } ?: run {
                text = newValue
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Decimal
        ),
        enabled = true,
        modifier = modifier
            .onFocusChanged { focusState ->
                if (hasFocus && !focusState.isFocused) {
                    // Потеря фокуса → нормализуем
                    val normalized = text.replace(',', '.')
                    normalized.toDoubleOrNull()?.let { validDouble ->
                        text = validDouble.toString()
                        onValueChanged(normalized)
                    }
                }
                hasFocus = focusState.isFocused
            }
            .width(IntrinsicSize.Max),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .padding(enterValueTextFieldInnerBoxPadding)
            ) {
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = appSubtleTextColor,
                        style = bodyStyle.copy(fontSize = appTextSizeSmall)
                    )
                }
                innerTextField()
            }
        },
        textStyle = bodyStyle.copy(
            color = appMainTextColor,
            fontSize = appTextSize
        ),
        cursorBrush = SolidColor(appMainTextColor),
        maxLines = 1
    )
}