package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.appDetailsInfoBlockLeftPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockIconPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWindColor

@Composable
fun AppDetailsInfoBlock(
    modifier: Modifier,
    sectionIcon: Painter? = null,
    iconTint: Color = appMainTextColor,
    sectionHeader: String,
    sectionValue: String,
    headerTextSize: TextUnit? = null,
    subtleTextSize: TextUnit? = null,
    isTextAligned: Boolean = false,
    isLoading: Boolean = false
) {

    if (isLoading) {
        AppProgressAnimation(
            modifier = modifier,
            color = appMainTextColor
        )
    } else {
        Row(
            modifier = modifier
        ) {

            sectionIcon?.let {
                Icon(
                    modifier = Modifier.padding(weatherScreenCurrentForecastBlockIconPadding),
                    painter = sectionIcon,
                    tint = iconTint,
                    contentDescription = stringResource(R.string.picture_description)
                )
            }

            Column {
                Text(
                    modifier = Modifier
                        .align(
                            if (isTextAligned) Alignment.CenterHorizontally
                            else Alignment.Start
                        ),
                    text = sectionHeader,
                    style = bodyStyle.copy(
                        color = appSubtleTextColor,
                        fontSize = headerTextSize ?: appTextSize
                    )
                )

                Text(
                    modifier = Modifier
                        .align(
                            if (isTextAligned) Alignment.CenterHorizontally
                            else Alignment.Start
                        )
                        .padding(weatherScreenCurrentForecastBlockTextPadding),
                    text = sectionValue,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = subtleTextSize ?: appTextSize
                    )
                )
            }
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
                    .padding(PaddingValues(horizontal = 20.dp))
            ) {
                AppDetailsInfoBlock(
                    modifier = Modifier
                        .padding(appDetailsInfoBlockLeftPadding)
                        .clip(appRoundedShape)
                        .border(
                            width = appDetailsInfoBlockBorderWidth,
                            color = appSubtleTextColor,
                            shape = appRoundedShape
                        )
                        .padding(weatherScreenCurrentForecastBlockInnerPadding),
                    sectionIcon = painterResource(R.drawable.wind_speed_icon),
                    iconTint = weatherScreenWindColor,
                    sectionHeader = stringResource(R.string.wind_speed_section_text),
                    sectionValue = "jasnkjasxsxasa",
                    isLoading = false
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
                    .padding(PaddingValues(horizontal = 20.dp))
            ) {
                AppDetailsInfoBlock(
                    modifier = Modifier
                        .padding(appDetailsInfoBlockLeftPadding)
                        .clip(appRoundedShape)
                        .border(
                            width = appDetailsInfoBlockBorderWidth,
                            color = appSubtleTextColor,
                            shape = appRoundedShape
                        )
                        .padding(weatherScreenCurrentForecastBlockInnerPadding),
                    sectionIcon = painterResource(R.drawable.wind_speed_icon),
                    iconTint = weatherScreenWindColor,
                    sectionHeader = stringResource(R.string.wind_speed_section_text),
                    sectionValue = "jasnkjasxsxasa",
                    isLoading = false
                )
            }
        }
    }
}