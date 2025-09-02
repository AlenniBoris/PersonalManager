package com.alenniboris.personalmanager.presentation.uikit.views

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alenniboris.personalmanager.presentation.uikit.model.PieSegment
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun AppAnimatedPieChart(
    modifier: Modifier = Modifier,
    segments: List<PieSegment>,
    animationDuration: Int = 1000,
    radius: Dp = 100.dp,
    useCenter: Boolean = false,
    isFilled: Boolean = false
) {
    val total = segments.sumOf { it.value.toDouble() }.toFloat()

    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(segments) {
        launch {
            animationProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(animationDuration)
            )
        }
    }

    Canvas(
        modifier = modifier.size(radius * 2)
    ) {
        if (total <= 0f) return@Canvas

        var startAngle = -90f

        segments.forEach { segment ->
            val sweepAngle = 360f * (segment.value / total) * animationProgress.value

            drawPieSegment(
                color = segment.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = useCenter,
                isFilled = isFilled
            )

            startAngle += sweepAngle
        }
    }
}

private fun DrawScope.drawPieSegment(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    useCenter: Boolean,
    isFilled: Boolean
) {
    if (sweepAngle <= 0f) return

    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = useCenter,
        size = size,
        style = if (isFilled) Fill
        else Stroke(
            width = 16.dp.toPx(),
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}

@Preview
@Composable
private fun LightTheme() {
    PersonalManagerTheme(
        darkTheme = false
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                AppAnimatedPieChart(
                    segments = listOf(
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
                        ),
                        PieSegment(
                            20f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            15f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            33f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        )
                    ),
                    radius = 150.dp,
                    animationDuration = 1500,
                    useCenter = true,
                    isFilled = true
                )
            }
        }
    }
}

@Preview
@Composable
private fun DarkTheme() {
    PersonalManagerTheme(
        darkTheme = true
    ) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                AppAnimatedPieChart(
                    segments = listOf(
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
                        ),
                        PieSegment(
                            20f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            15f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        ),
                        PieSegment(
                            33f,
                            Color(
                                red = Random.nextInt(),
                                green = Random.nextInt(),
                                blue = Random.nextInt()
                            )
                        )
                    ),
                    radius = 150.dp,
                    animationDuration = 1500
                )
            }
        }
    }
}