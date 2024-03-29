package com.makalaster.ethereal_dialpad.pads.draw

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makalaster.ethereal_dialpad.pads.Line
import com.makalaster.ethereal_dialpad.pads.lightsAndSounds

@Composable
fun DrawPad(
    viewModel: DrawViewModel = viewModel(),
    width: Float,
    height: Float,
    onTap: () -> Unit
) {
//    var currentPosition by remember { mutableStateOf(Offset.Unspecified) }
//
//    val xHistory = mutableListOf<Float>()
//    val yHistory = mutableListOf<Float>()
//
//    var d = 0f
//
//    val maxD = 2048f

    val lines = remember {
        mutableStateListOf<Line>()
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .lightsAndSounds(
                on = { x, y ->
                    viewModel.primaryOn()
                    viewModel.primaryXY(viewModel.xTransform(x, width), viewModel.yTransform(y, height))
                },
                off = {
                    viewModel.primaryOff()
                    lines.clear()
                },
                tap = {
                      onTap()
                },
                lights = { change ->
                    val line = Line(change.previousPosition, change.position, strokeWidth = 10.dp)
                    lines.add(line)
                },
                sounds = { x, y ->
                    viewModel.primaryXY(viewModel.xTransform(x, width), viewModel.yTransform(y, height))
                }
            )
//            .pointerInput(Unit) {
//                awaitEachGesture {
//                    val down = awaitFirstDown()
//
//                    var change = awaitTouchSlopOrCancellation(down.id) { inputChange, _ ->
//                        inputChange.position.also {
//                            currentPosition = it
//                        }
//
//                        inputChange.consume()
//                        d = 0f
//                    }
//
//                    while (change != null && change.pressed) {
//                        change = awaitDragOrCancellation(change.id)
//
//                        if (change != null && change.pressed) {
//                            with(change.position) {
//                                val ex = x
//                                val ey = y
//
//                                xHistory.add(ex)
//                                yHistory.add(ey)
//
//                                d += sqrt((ex - currentPosition.x).pow(2) + (ey - currentPosition.y).pow(2).toDouble()).toFloat()
//
//                                while (d > maxD && xHistory.size > 1) {
//                                    val surplus = d - maxD
//                                    val ax = xHistory[0]
//                                    val ay = yHistory[0]
//                                    val bx = xHistory[1]
//                                    val by = yHistory[1]
//                                    val chopD = sqrt((ax - bx).pow(2) + (ay - by).pow(2).toDouble()).toFloat()
//                                    if (surplus > chopD) {
//                                        xHistory.removeAt(0)
//                                        yHistory.removeAt(0)
//                                        d -= chopD
//                                    } else {
//                                        val frac = surplus / chopD
//                                        val nx = (1 - frac) * ax + frac * bx
//                                        val ny = (1 - frac) * ay + frac * by
//                                        xHistory[0] = nx
//                                        yHistory[0] = ny
//                                        d -= surplus
//                                        break
//                                    }
//                                }
//
//                            }
//                            change.consume()
//
//                        } else {
//                            xHistory.clear()
//                            yHistory.clear()
//                            d = 0f
//                        }
//                    }
//                }
//            }
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