package com.makalaster.etherealdialpad.pads

import androidx.compose.foundation.gestures.awaitDragOrCancellation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makalaster.etherealdialpad.ui.theme.padStartBackground
import kotlin.math.max
import kotlin.math.min

@Composable
fun FlatPad(
    viewModel: PadViewModel = viewModel()
) {
    var color by remember { mutableStateOf(padStartBackground) }

    val width: Float
    val height: Float
    LocalConfiguration.current.let {
        width = with(LocalDensity.current) { it.screenWidthDp.dp.toPx() }
        height = with(LocalDensity.current) { it.screenHeightDp.dp.toPx() }
    }

    val ctx = LocalContext.current
    viewModel.refreshPrefs(ctx)

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color)
            }
            .pointerInput(Unit) {
                awaitEachGesture {
                    val down = awaitFirstDown()

                    var change = awaitTouchSlopOrCancellation(down.id) { change, _ ->
                        viewModel.primaryOn()
                        change.consume()
                    }

                    while (change != null && change.pressed) {
                        change = awaitDragOrCancellation(change.id)

                        if (change != null && change.pressed) {
                            with(change.position) {
                                val u = min(1f, max(0f, x / width))
                                val v = min(1f, max(0f, y / height))

                                val r = (128 + 128 * (u - 0.5f)).toInt()
                                val g = (128 + 128 * (v - 0.5f)).toInt()
                                val b = (128 + 128 * (0.5f - u)).toInt()

                                color = Color(r, g, b)

                                viewModel.primaryXY(ctx, u, v)
                            }
                            change.consume()
                        } else {
                            color = padStartBackground
                            viewModel.primaryOff()
                        }
                    }
                }
            }
    )
}