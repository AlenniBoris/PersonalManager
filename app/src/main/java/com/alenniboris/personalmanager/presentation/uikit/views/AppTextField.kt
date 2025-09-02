package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.enterTextFieldColor
import com.alenniboris.personalmanager.presentation.uikit.theme.enterValueTextFieldInnerBoxPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.passwordHidePicture
import com.alenniboris.personalmanager.presentation.uikit.theme.passwordShowPicture

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String = "",
    icon: Painter? = null,
    onIconClicked: () -> Unit = {},
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    isEnabled: Boolean = true,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Unspecified
) {

    EnterValueTextField(
        modifier = modifier,
        value = value,
        onValueChanged = onValueChanged,
        placeholder = placeholder,
        icon = icon,
        onIconClicked = onIconClicked,
        isPasswordField = isPasswordField,
        isPasswordVisible = isPasswordVisible,
        isEnabled = isEnabled,
        maxLines = maxLines,
        keyboardType = keyboardType
    )
}

@Composable
private fun EnterValueTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChanged: (String) -> Unit = {},
    placeholder: String = "Hint",
    icon: Painter? = null,
    onIconClicked: () -> Unit = {},
    isPasswordField: Boolean = false,
    isPasswordVisible: Boolean = false,
    isEnabled: Boolean = true,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChanged,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        enabled = isEnabled,
        modifier = modifier
            .width(IntrinsicSize.Max),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon?.let {
                    IconButton(
                        onClick = { onIconClicked() },
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = stringResource(R.string.text_field_icon_btn_description),
                            tint = appSubtleTextColor
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(enterValueTextFieldInnerBoxPadding)
                        .weight(1f),
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = appSubtleTextColor,
                            style = bodyStyle.copy(fontSize = appTextSize)
                        )
                    }
                    innerTextField()
                }
            }
        },
        textStyle = bodyStyle.copy(
            color = appMainTextColor,
            fontSize = appTextSize
        ),
        cursorBrush = SolidColor(appMainTextColor),
        visualTransformation = if (isPasswordField && !isPasswordVisible) PasswordVisualTransformation()
        else VisualTransformation.None,
        maxLines = maxLines
    )
}


@Composable
@Preview
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                AppTextField(
                    value = "",
                    onValueChanged = {},
                    placeholder = "Hello",
                    icon = painterResource(passwordShowPicture)
                )

                AppTextField(
                    value = "",
                    onValueChanged = {},
                    placeholder = "world",
                    icon = painterResource(passwordHidePicture),
                    modifier = Modifier
                        .background(appColor)
                        .fillMaxWidth()
                )

                AppTextField(
                    value = "ededed",
                    onValueChanged = {},
                    placeholder = "world coming true",
                    modifier = Modifier
                        .background(enterTextFieldColor, shape = RoundedCornerShape(20))
                        .padding(10.dp)
                        .width(100.dp)
                )

                AppTextField(
                    value = "",
                    onValueChanged = {},
                    placeholder = "world",
                    modifier = Modifier
                        .background(enterTextFieldColor, shape = RoundedCornerShape(20))
                        .padding(10.dp)
                        .width(300.dp)
                )

                AppTextField(
                    value = "",
                    onValueChanged = {},
                    placeholder = "world",
                    modifier = Modifier
                        .background(enterTextFieldColor, shape = RoundedCornerShape(20))
                        .padding(10.dp)
                        .width(500.dp)
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                AppTextField(
                    value = "",
                    onValueChanged = {},
                    placeholder = "Hello",
                    icon = painterResource(passwordShowPicture)
                )

                AppTextField(
                    value = "",
                    onValueChanged = {},
                    placeholder = "world",
                    icon = painterResource(passwordHidePicture),
                    modifier = Modifier
                        .background(appColor)
                        .fillMaxWidth()
                )

                AppTextField(
                    value = "ededed",
                    onValueChanged = {},
                    placeholder = "world coming true",
                    modifier = Modifier
                        .background(enterTextFieldColor, shape = RoundedCornerShape(20))
                        .padding(10.dp)
                        .width(100.dp)
                )

                AppTextField(
                    value = "",
                    onValueChanged = {},
                    placeholder = "world",
                    modifier = Modifier
                        .background(enterTextFieldColor, shape = RoundedCornerShape(20))
                        .padding(10.dp)
                        .width(300.dp)
                )

                AppTextField(
                    value = "",
                    onValueChanged = {},
                    placeholder = "world",
                    modifier = Modifier
                        .background(enterTextFieldColor, shape = RoundedCornerShape(20))
                        .padding(10.dp)
                        .width(500.dp)
                )
            }
        }
    }
}
