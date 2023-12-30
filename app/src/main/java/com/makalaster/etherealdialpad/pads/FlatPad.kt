package com.makalaster.etherealdialpad.pads

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

    val xTransform: (Float) -> Float = { x -> min(1f, max(0f, x / width)) }
    val yTransform: (Float) -> Float = { y -> min(1f, max(0f, y / height)) }

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color)
            }
            .lightsAndSounds(
                on = { viewModel.primaryOn() },
                off = {
                    color = padStartBackground
                    viewModel.primaryOff()
                },
                lights = { change ->
                    val u = xTransform(change.position.x)
                    val v = yTransform(change.position.y)

                    val r = (128 + 128 * (u - 0.5f)).toInt()
                    val g = (128 + 128 * (v - 0.5f)).toInt()
                    val b = (128 + 128 * (0.5f - u)).toInt()

                    color = Color(r, g, b)
                },
                sounds = { x, y ->
                    viewModel.primaryXY(ctx, xTransform(x), yTransform(y))
                }
            )
    )
}