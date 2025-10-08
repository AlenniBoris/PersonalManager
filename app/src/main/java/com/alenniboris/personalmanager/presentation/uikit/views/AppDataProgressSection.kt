package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSize
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenContentItemTopPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenInfoTextColumnPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenInfoTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenLinearProgressIndicatorTrackColor
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenOverviewStatisticsSectionProgressLineHeight
import kotlinx.coroutines.launch

@Composable
fun AppDataProgressSection(
    modifier: Modifier = Modifier,
    headerText: String,
    valueText: String = "",
    valuePercent: Double,
    animationDuration: Int = 1000,
) {

    val animationProgress = remember { Animatable(0f) }
    LaunchedEffect(valuePercent) {
        launch {
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(animationDuration)
            )
        }
    }

    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = headerText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )

            Text(
                modifier = Modifier.padding(healthScreenInfoTextColumnPadding),
                text = valueText,
                style = bodyStyle.copy(
                    color = appMainTextColor,
                    fontSize = appTextSize
                )
            )
        }

        Box(
            modifier = Modifier
                .padding(healthScreenInfoTextPadding)
                .fillMaxWidth()
                .clip(appRoundedShape)
                .background(healthScreenLinearProgressIndicatorTrackColor)
                .height(healthScreenOverviewStatisticsSectionProgressLineHeight)
        ) {
            Box(
                modifier = Modifier
                    .background(appMainTextColor)
                    .fillMaxHeight()
                    .fillMaxWidth(valuePercent.toFloat() * animationProgress.value)
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
                AppDataProgressSection(
                    modifier = Modifier.padding(healthScreenContentItemTopPadding),
                    headerText = stringResource(R.string.proteins_section_text),
                    valueText = "120/10",
                    valuePercent = 0.86
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
                AppDataProgressSection(
                    modifier = Modifier.padding(healthScreenContentItemTopPadding),
                    headerText = stringResource(R.string.proteins_section_text),
                    valueText = "120/10",
                    valuePercent = 0.86
                )
            }
        }
    }
}