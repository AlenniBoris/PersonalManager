package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appIconPlaceholderShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor

@Composable
fun AppIconPlaceholder(
    modifier: Modifier = Modifier,
    icon: Painter,
    iconTint: Color = appColor,
    iconContentDescription: String = ""
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = icon,
            tint = iconTint,
            contentDescription = iconContentDescription
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
                    .padding(horizontal = 20.dp)
            ) {

                AppIconPlaceholder(
                    modifier = Modifier
                        .padding(all = 20.dp)
                        .clip(appIconPlaceholderShape)
                        .background(appMainTextColor)
                        .padding(PaddingValues(all = 15.dp)),
                    icon = painterResource(R.drawable.app_icon)
                )

                AppIconPlaceholder(
                    modifier = Modifier
                        .padding(all = 20.dp)
                        .clip(CircleShape)
                        .background(appMainTextColor)
                        .padding(PaddingValues(all = 15.dp)),
                    icon = painterResource(R.drawable.app_icon),
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
                    .padding(horizontal = 20.dp)
            ) {

                AppIconPlaceholder(
                    modifier = Modifier
                        .padding(all = 20.dp)
                        .clip(appIconPlaceholderShape)
                        .background(appMainTextColor)
                        .padding(PaddingValues(all = 15.dp)),
                    icon = painterResource(R.drawable.app_icon)
                )

                AppIconPlaceholder(
                    modifier = Modifier
                        .padding(all = 20.dp)
                        .clip(CircleShape)
                        .background(appMainTextColor)
                        .padding(PaddingValues(all = 15.dp)),
                    icon = painterResource(R.drawable.app_icon),
                )
            }
        }
    }
}