package com.alenniboris.personalmanager.presentation.screens.tasks.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.R
import com.alenniboris.personalmanager.presentation.uikit.model.PieSegment
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenPieChartDescriptionColorBlockSize
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenPieChartDescriptionPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenPieChartDescriptionTextPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenPieChartPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.tasksScreenPieChartRadius
import com.alenniboris.personalmanager.presentation.uikit.views.AppAnimatedPieChart
import kotlin.random.Random

@Composable
fun TasksScreenPieChart(
    modifier: Modifier = Modifier,
    listOfPieSegments: List<PieSegment>,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AppAnimatedPieChart(
            segments = listOfPieSegments,
            modifier = Modifier.padding(tasksScreenPieChartPadding),
            radius = tasksScreenPieChartRadius,
            animationDuration = 1500,
            useCenter = true,
            isFilled = true
        )

        FlowRow(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOfPieSegments.forEach { segment ->
                PieSegmentDescription(
                    modifier = Modifier.padding(tasksScreenPieChartDescriptionPadding),
                    segment = segment
                )
            }
        }
    }
}

@Composable
private fun PieSegmentDescription(
    modifier: Modifier = Modifier,
    segment: PieSegment
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(tasksScreenPieChartDescriptionColorBlockSize)
                .clip(CircleShape)
                .background(segment.color)
        )

        Text(
            modifier = Modifier.padding(tasksScreenPieChartDescriptionTextPadding),
            text = stringResource(segment.labelId ?: R.string.no_text_placeholder),
            style = bodyStyle.copy(
                fontSize = appTextSizeSmall,
                color = appSubtleTextColor
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(appColor)
            ) {
                TasksScreenPieChart(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    listOfPieSegments = listOf(
                        PieSegment(
                            40f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            10f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            30f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            10f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        )
                    )
                )

                TasksScreenPieChart(
                    modifier = Modifier.padding(top = 15.dp),
                    listOfPieSegments = listOf(
                        PieSegment(
                            40f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            10f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        )
                    )
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
                TasksScreenPieChart(
                    modifier = Modifier.padding(top = 15.dp),
                    listOfPieSegments = listOf(
                        PieSegment(
                            40f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            10f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            30f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            10f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        )
                    )
                )

                TasksScreenPieChart(
                    modifier = Modifier.padding(top = 15.dp),
                    listOfPieSegments = listOf(
                        PieSegment(
                            40f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            10f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        )
                    )
                )
            }
        }
    }
}