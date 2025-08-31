package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appIconPlaceholderShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarButtonInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarSubtleTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.topBarThirdButtonPadding

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    textColor: Color,
    headerTextString: String = "",
    subtleText: String? = null,
    firstButtonPainter: Painter? = null,
    onFirstClicked: () -> Unit = {},
    secondButtonPainter: Painter? = null,
    onSecondClicked: () -> Unit = {},
    thirdButtonPainter: Painter,
    onThirdClicked: () -> Unit = {}
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        firstButtonPainter?.let {
            AppIconPlaceholder(
                modifier = Modifier
                    .clip(appIconPlaceholderShape)
                    .background(appMainTextColor)
                    .padding(topBarButtonInnerPadding)
                    .clickable { onFirstClicked() },
                icon = firstButtonPainter,
                iconTint = textColor
            )
        }

        Column(
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = headerTextString,
                style = bodyStyle.copy(
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = topBarTextSize
                )
            )

            subtleText?.let {
                Text(
                    modifier = Modifier.padding(topBarSubtleTextPadding),
                    text = subtleText,
                    style = bodyStyle.copy(
                        color = textColor,
                        fontSize = topBarTextSize
                    )
                )
            }

        }

        Row {

            secondButtonPainter?.let {
                AppIconPlaceholder(
                    modifier = Modifier
                        .clip(appIconPlaceholderShape)
                        .background(appMainTextColor)
                        .padding(topBarButtonInnerPadding)
                        .clickable { onSecondClicked() },
                    icon = secondButtonPainter,
                    iconTint = textColor
                )
            }

            AppIconPlaceholder(
                modifier = Modifier
                    .padding(topBarThirdButtonPadding)
                    .clip(appIconPlaceholderShape)
                    .background(appMainTextColor)
                    .padding(topBarButtonInnerPadding)
                    .clickable { onThirdClicked() },
                icon = thirdButtonPainter,
                iconTint = textColor
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {

                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(appMainTextColor)
                        .padding(topBarInnerPadding),
                    textColor = appColor,
                    headerTextString = stringResource(R.string.app_name),
                    subtleText = stringResource(R.string.hello_text) + "s,dscscsd",
                    secondButtonPainter = painterResource(R.drawable.settings_icon),
                    onSecondClicked = {},
                    thirdButtonPainter = painterResource(R.drawable.person_icon),
                    onThirdClicked = {}
                )

                AppTopBar(
                    modifier = Modifier
                        .padding(PaddingValues(top = 20.dp))
                        .fillMaxWidth()
                        .background(appMainTextColor)
                        .padding(topBarInnerPadding),
                    textColor = appColor,
                    headerTextString = stringResource(R.string.app_name),
                    subtleText = stringResource(R.string.hello_text) + "s,dscscsd",
                    firstButtonPainter = painterResource(R.drawable.back_icon),
                    onFirstClicked = {},
                    secondButtonPainter = painterResource(R.drawable.settings_icon),
                    onSecondClicked = {},
                    thirdButtonPainter = painterResource(R.drawable.person_icon),
                    onThirdClicked = {}
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

                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(appMainTextColor)
                        .padding(topBarInnerPadding),
                    textColor = appColor,
                    headerTextString = stringResource(R.string.app_name),
                    subtleText = stringResource(R.string.hello_text),
                    secondButtonPainter = painterResource(R.drawable.settings_icon),
                    onSecondClicked = {},
                    thirdButtonPainter = painterResource(R.drawable.person_icon),
                    onThirdClicked = {}
                )

                AppTopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(appMainTextColor)
                        .padding(topBarInnerPadding),
                    textColor = appColor,
                    headerTextString = stringResource(R.string.app_name),
                    subtleText = stringResource(R.string.hello_text),
                    firstButtonPainter = painterResource(R.drawable.back_icon),
                    onFirstClicked = {},
                    secondButtonPainter = painterResource(R.drawable.settings_icon),
                    onSecondClicked = {},
                    thirdButtonPainter = painterResource(R.drawable.person_icon),
                    onThirdClicked = {}
                )
            }
        }
    }
}