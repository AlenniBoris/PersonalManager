package com.alenniboris.personalmanager.presentation.screens.weather.views

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockBorderWidth
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenBlockShape
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockIconPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockRightPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenCurrentForecastBlockTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.weatherScreenWindColor
import com.alenniboris.personalmanager.presentation.uikit.views.AppProgressAnimation

@Composable
fun ForecastDetailsInfoBlock(
    modifier: Modifier,
    sectionIcon: Painter,
    iconTint: Color,
    sectionHeader: String,
    sectionValue: String,
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

            Icon(
                modifier = Modifier.padding(weatherScreenCurrentForecastBlockIconPadding),
                painter = sectionIcon,
                tint = iconTint,
                contentDescription = stringResource(R.string.picture_description)
            )

            Column {
                Text(
                    text = sectionHeader,
                    style = bodyStyle.copy(
                        color = appSubtleTextColor,
                        fontSize = weatherScreenTextSize
                    )
                )

                Text(
                    modifier = Modifier.padding(weatherScreenCurrentForecastBlockTextPadding),
                    text = sectionValue,
                    style = bodyStyle.copy(
                        color = appMainTextColor,
                        fontSize = weatherScreenTextSize
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
                ForecastDetailsInfoBlock(
                    modifier = Modifier
                        .padding(weatherScreenCurrentForecastBlockRightPadding)
                        .clip(weatherScreenBlockShape)
                        .border(
                            width = weatherScreenBlockBorderWidth,
                            color = appSubtleTextColor,
                            shape = weatherScreenBlockShape
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
                ForecastDetailsInfoBlock(
                    modifier = Modifier
                        .padding(weatherScreenCurrentForecastBlockRightPadding)
                        .clip(weatherScreenBlockShape)
                        .border(
                            width = weatherScreenBlockBorderWidth,
                            color = appSubtleTextColor,
                            shape = weatherScreenBlockShape
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