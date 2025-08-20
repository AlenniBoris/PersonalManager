package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appCustomButtonInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appCustomButtonShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appCustomButtonTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.logRegScreenBackgroundColor

@Composable
fun AppCustomButton(
    modifier: Modifier = Modifier,
    isClickable: Boolean = true,
    onClick: () -> Unit = {},
    text: String = "",
    icon: Painter? = null,
    iconDescription: String = ""
) {

    Row(
        modifier = modifier
            .clip(appCustomButtonShape)
            .background(appMainTextColor)
            .padding(appCustomButtonInnerPadding)
            .clickable {
                if (isClickable) {
                    onClick()
                }
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        icon?.let {
            Icon(
                painter = icon,
                tint = appColor,
                contentDescription = iconDescription
            )
        }

        Text(
            text = text,
            style = bodyStyle.copy(
                fontSize = appCustomButtonTextSize,
                color = appColor
            )
        )
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
                Modifier
                    .fillMaxSize()
                    .background(logRegScreenBackgroundColor)
            ) {
                AppCustomButton(
                    text = "Loooo",
                    onClick = {},
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
                Modifier
                    .fillMaxSize()
                    .background(logRegScreenBackgroundColor)
            ) {
                AppCustomButton(
                    text = "Loooo",
                    onClick = {},
                )
            }
        }
    }
}