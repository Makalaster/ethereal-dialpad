package com.makalaster.etherealdialpad.pads

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makalaster.etherealdialpad.ui.theme.padStartBackground
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun DrawPad(
    viewModel: PadViewModel = viewModel()
) {
    val width: Float
    val height: Float
    LocalConfiguration.current.let {
        width = with(LocalDensity.current) { it.screenWidthDp.dp.toPx() }
        height = with(LocalDensity.current) { it.screenHeightDp.dp.toPx() }
    }

    val ctx = LocalContext.current
    viewModel.refreshPrefs(ctx)

    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }

    val xHistory = mutableListOf<Float>()
    val yHistory = mutableListOf<Float>()
    
    var d = 0f

    val maxD = 2048f

    val lines = remember {
        mutableStateListOf<Line>()
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(padStartBackground)
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown()

                    var change = awaitTouchSlopOrCancellation(down.id) { inputChange, _ ->
                        viewModel.primaryOn()
                        inputChange.position.also {
                            currentPosition = it
                        }

                        inputChange.consume()
                        d = 0f
                    }

                    while (change != null && change.pressed) {
                        change = awaitDragOrCancellation(change.id)

                        if (change != null && change.pressed) {
                            with(change.position) {
                                val ex = x
                                val ey = y

                                xHistory.add(ex)
                                yHistory.add(ey)

                                d += sqrt((ex - currentPosition.x).pow(2) + (ey - currentPosition.y).pow(2).toDouble()).toFloat()

                                while (d > maxD && xHistory.size > 1) {
                                    val surplus = d - maxD
                                    val ax = xHistory[0]
                                    val ay = yHistory[0]
                                    val bx = xHistory[1]
                                    val by = yHistory[1]
                                    val chopD = sqrt((ax - bx).pow(2) + (ay - by).pow(2).toDouble()).toFloat()
                                    if (surplus > chopD) {
                                        xHistory.removeAt(0)
                                        yHistory.removeAt(0)
                                        d -= chopD
                                    } else {
                                        val frac = surplus / chopD
                                        val nx = (1 - frac) * ax + frac * bx
                                        val ny = (1 - frac) * ay + frac * by
                                        xHistory[0] = nx
                                        yHistory[0] = ny
                                        d -= surplus
                                        break
                                    }
                                }

                                viewModel.primaryXY(ctx, ex / width, 1 - ey / height)
                            }
                            change.consume()

                            val line = Line(change.previousPosition, change.position)
                            lines.add(line)
                        } else {
                            xHistory.clear()
                            yHistory.clear()
                            d = 0f
                            viewModel.primaryOff()
                            lines.clear()
                        }
                    }
                }
            }
    ) {
        // TODO: allow toggle between fixed length and max segments
        // TODO: allow adjusting max segment count
        if (lines.size > 60) {
            lines.removeAt(0)
        }

        lines.forEach { line ->
            drawLine(
                color = line.color,
                start = line.start,
                end = line.end,
                strokeWidth = line.strokeWidth.toPx(),
                cap = StrokeCap.Round
            )
        }
    }
}

data class Line(
    val start: Offset,
    val end: Offset,
    val color: Color = Color.White,
    val strokeWidth: Dp = 10.dp
)