package com.alenniboris.personalmanager.presentation.uikit.views

import android.graphics.PathMeasure
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alenniboris.fastbanking.presentation.uikit.theme.bodyStyle
import com.alenniboris.personalmanager.domain.model.weight.WeightModelDomain
import com.alenniboris.personalmanager.presentation.model.weight.WeightModelUi
import com.alenniboris.personalmanager.presentation.uikit.model.IPlotModelUi
import com.alenniboris.personalmanager.presentation.uikit.theme.PersonalManagerTheme
import com.alenniboris.personalmanager.presentation.uikit.theme.appColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appMainTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appRoundedShape
import com.alenniboris.personalmanager.presentation.uikit.theme.appSubtleTextColor
import com.alenniboris.personalmanager.presentation.uikit.theme.appTextSizeSmall
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenWeightPlotDetailsBoxInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenPlotHeight
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenPlotInnerPadding
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenWeightPlotPointRadius
import com.alenniboris.personalmanager.presentation.uikit.theme.healthScreenWeightPlotSelectedPointRadius
import java.util.Calendar
import kotlin.math.roundToInt

@Composable
fun AppAnimatedPlot(
    modifier: Modifier = Modifier,
    weights: List<IPlotModelUi>,
    isLoading: Boolean
) {
    val animatedProgress = remember { Animatable(0f) }

    var touchX by remember { mutableStateOf<Float?>(null) }
    var selectedPoint by remember { mutableStateOf<IPlotModelUi?>(null) }

    LaunchedEffect(Unit) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        touchX = offset.x
                    },
                    onDrag = { change, _ ->
                        touchX = change.position.x
                    },
                    onDragEnd = {
                        touchX = null
                        selectedPoint = null
                    },
                    onDragCancel = {
                        touchX = null
                        selectedPoint = null
                    }
                )
            }
    ) {

        if (isLoading) {
            AppProgressAnimation(
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Canvas(modifier = Modifier.fillMaxSize()) {
                if (weights.isEmpty()) return@Canvas

                // Сортируем по времени
                val sortedWeights = weights.sortedBy { it.time.time }

                val maxWeight = sortedWeights.maxOf { it.modelValue }
                val minWeight = sortedWeights.minOf { it.modelValue }
                val yRange = maxWeight - minWeight
                val xStep = size.width / (sortedWeights.size - 1)

                // Сетка
                val gridLines = 10
                repeat(gridLines + 1) { i ->
                    val y = i * size.height / gridLines
                    drawLine(
                        color = appSubtleTextColor.copy(alpha = 0.5f),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                // Точки графика
                val points = sortedWeights.mapIndexed { index, item ->
                    val x = index * xStep
                    val y =
                        (size.height - ((item.modelValue - minWeight) / yRange * size.height)).toFloat()
                    Offset(x, y)
                }

                // Сглаженный путь
                val path = Path().apply {
                    if (points.isNotEmpty()) {
                        moveTo(points.first().x, points.first().y)

                        for (i in 0 until points.lastIndex) {
                            val p0 = if (i == 0) points[i] else points[i - 1]
                            val p1 = points[i]
                            val p2 = points[i + 1]
                            val p3 = if (i + 2 < points.size) points[i + 2] else points[i + 1]

                            // контрольные точки для cubicTo
                            val c1x = p1.x + (p2.x - p0.x) / 6f
                            val c1y = p1.y + (p2.y - p0.y) / 6f
                            val c2x = p2.x - (p3.x - p1.x) / 6f
                            val c2y = p2.y - (p3.y - p1.y) / 6f

                            cubicTo(c1x, c1y, c2x, c2y, p2.x, p2.y)
                        }
                    }
                }
                val pathMeasure = PathMeasure(path.asAndroidPath(), false)
                val pathLength = pathMeasure.length
                drawPath(
                    path = path,
                    color = appMainTextColor,
                    style = Stroke(
                        width = 4f,
                        cap = StrokeCap.Round,
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(pathLength, pathLength),
                            phase = pathLength - pathLength * animatedProgress.value
                        )
                    )
                )

                // Точки
                points.take((points.size * animatedProgress.value).toInt()).forEach { point ->
                    drawCircle(
                        color = appMainTextColor,
                        radius = healthScreenWeightPlotPointRadius,
                        center = point
                    )
                }

                // Если пользователь тапнул — ищем ближайшую точку
                touchX?.let { x ->
                    val nearestIndex = (x / xStep).roundToInt().coerceIn(0, points.lastIndex)
                    val point = points[nearestIndex]
                    selectedPoint = sortedWeights[nearestIndex]

                    drawCircle(
                        color = Color.Red,
                        radius = healthScreenWeightPlotSelectedPointRadius,
                        center = point
                    )
                }
            }
        }

        // Подсказка с данными при выборе точки
        selectedPoint?.let { data ->
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .clip(appRoundedShape)
                    .background(appMainTextColor)
                    .padding(healthScreenWeightPlotDetailsBoxInnerPadding)
            ) {
                Text(
                    text = "${data.valueText}\n${data.timeText}",
                    style = bodyStyle.copy(
                        color = appColor,
                        fontSize = appTextSizeSmall
                    )
                )
            }
        }
    }
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
                    .background(appColor)
            ) {
                AppAnimatedPlot(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(healthScreenPlotHeight)
                        .padding(healthScreenPlotInnerPadding),
                    weights = listOf(
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 12.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 32.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 87.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 37.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 22.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 11.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 20.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    isLoading = false
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
                    .background(appColor)
            ) {
                AppAnimatedPlot(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(16.dp),
                    weights = listOf(
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 12.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 32.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 87.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 37.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 0.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 22.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 11.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 17.2,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        ),
                        WeightModelUi(
                            domainModel = WeightModelDomain(
                                id = "1",
                                userId = "1",
                                weight = 20.0,
                                markingDate = Calendar.getInstance().time,
                                markingTime = Calendar.getInstance().time
                            )
                        )
                    ),
                    isLoading = false
                )
            }
        }
    }
}
