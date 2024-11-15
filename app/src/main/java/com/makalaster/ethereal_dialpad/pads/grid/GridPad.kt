package com.makalaster.ethereal_dialpad.pads.grid

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makalaster.ethereal_dialpad.pads.lightsAndSounds
import com.makalaster.ethereal_dialpad.ui.theme.gridPadBackground

@Composable
fun GridPad(
    viewModel: GridViewModel = viewModel(),
    width: Float,
    height: Float,
    onTap: () -> Unit,
) {
    val currentDensity = LocalDensity.current
    val currentConfig = LocalConfiguration.current

    var targetX by remember {
        mutableFloatStateOf(0f)
    }

    var targetY by remember {
        mutableFloatStateOf(0f)
    }

    var touching by remember {
        mutableStateOf(false)
    }

    val state = viewModel.flow.collectAsState().value

    val scale = state.quantizer.value.split(',').map { it.toFloat() }

    val nx = scale.size * state.octaves.toInt() + 1
    val ny = if (state.duet) nx else 0

    fun noteColor(n: Float) = Color.hsv(
        n / 12f * 300,
        if (n == 0f) 0f else 1f,
        1f
    )

    val colorsX = scale.map {
        noteColor(it)
    }

    val scaleY = if (state.duet) scale else emptyList()

    val colorsY = scaleY.map {
        noteColor(it)
    }

    val nw = width / nx.toFloat()

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(gridPadBackground)
            .lightsAndSounds(
                on = { x, y ->
                    viewModel.primaryOn()
                    touching = true

                    targetX = x / width
                    targetY = 1f - y / with(currentDensity) { currentConfig.screenHeightDp.dp.toPx() }
                },
                off = {
                    viewModel.primaryOff()
                    touching = false
                },
                tap = onTap,
                lights = { change ->
                    targetX = change.position.x / width
                    targetY = 1f - change.position.y / with(currentDensity) { currentConfig.screenHeightDp.dp.toPx() }
                },
                sounds = { x, y ->
                    viewModel.primaryXY(x / width, 1 - y / height)
                }
            )
    ) {
        if (touching) {
            drawRect(
                color = Color(
                    red = .5f + .5f * (targetX - 0.5f),
                    green = .5f + .5f * (targetY - 0.5f),
                    blue = .5f + .5f * (0.5f - targetX)
                ),
                size = size
            )

            drawCircle(
                color = Color(-0x7f000001),
                radius = size.width / (2 * nx).toFloat(),
                center = Offset(
                    x = size.width * (.5f + (targetX * nx).toInt()) / nx,
                    y = (1 - targetY) * size.height
                )
            )
        }

        for (s in 0 until nx) {
            val x = s * nw + nw / 2f

            drawLine(
                color = colorsX[s % colorsX.size],
                strokeWidth = 1.5f,
                start = Offset(
                    x, 0f
                ),
                end = Offset(
                    x, size.height
                )
            )
        }

        for (s in 0 until ny) {
            val y = (size.height / ny.toFloat()).let { s * it + it / 2f }

            drawLine(
                color = colorsY[s % colorsY.size],
                strokeWidth = 1.5f,
                start = Offset(
                    0f, y
                ),
                end = Offset(
                    size.width, y
                )
            )
        }
    }
}