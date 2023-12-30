package com.makalaster.etherealdialpad.pads

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min

@Composable
fun FlatPad(
    viewModel: PadViewModel = viewModel()
) {
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var backPressHandled by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = !backPressHandled) {
        viewModel.primaryOff()
        backPressHandled = true
        coroutineScope.launch {
            awaitFrame()
            onBackPressedDispatcher?.onBackPressed()
            backPressHandled = false
        }
    }

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

    val colorTransform: (Float, Float) -> Color = { x, y ->
        val u = xTransform(x)
        val v = yTransform(y)

        val r = (128 + 128 * (u - 0.5f)).toInt()
        val g = (128 + 128 * (v - 0.5f)).toInt()
        val b = (128 + 128 * (0.5f - u)).toInt()

        Color(r, g, b)
    }

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color)
            }
            .lightsAndSounds(
                on = {
                    viewModel.primaryOn()
                    viewModel.primaryXY(ctx, xTransform(it.x), yTransform(it.y))
                    color = colorTransform(it.x, it.y)
                },
                off = {
                    color = padStartBackground
                    viewModel.primaryOff()
                },
                lights = { change ->
                    color = colorTransform(change.position.x, change.position.y)
                },
                sounds = { x, y ->
                    viewModel.primaryXY(ctx, xTransform(x), yTransform(y))
                }
            )
    )
}