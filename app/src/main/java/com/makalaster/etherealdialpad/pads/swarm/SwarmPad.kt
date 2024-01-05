package com.makalaster.etherealdialpad.pads.swarm

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
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
import com.makalaster.etherealdialpad.Constants
import com.makalaster.etherealdialpad.pads.Line
import com.makalaster.etherealdialpad.pads.lightsAndSounds
import com.makalaster.etherealdialpad.prefs.PreferencesBottomSheet
import com.makalaster.etherealdialpad.ui.theme.swarmPadBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwarmPad(
    viewModel: SwarmViewModel = viewModel()
) {
    val width: Float
    val height: Float
    LocalConfiguration.current.let {
        width = with(LocalDensity.current) { it.screenWidthDp.dp.toPx() }
        height = with(LocalDensity.current) { it.screenHeightDp.dp.toPx() }
    }

    val sparks = remember {
        mutableStateListOf<Line>()
    }
    
    var isTouching by remember {
        mutableStateOf(false)
    }

    val state = viewModel.swarmPrefsFlow.collectAsState()

    var targetX by remember {
        mutableFloatStateOf(0f)
    }
    var targetY by remember {
        mutableFloatStateOf(0f)
    }

    val points = FloatArray(4 * Constants.N)

    fun sparkle() {
        sparks.clear()

        val k = 0.975f
        val tx = (1.0f - k) * targetX
        val ty = (1.0f - k) * targetY
        val f = 0.875f
        val d = 0.025f

        for (i in 0 until Constants.N) {
            val px = points[4 * i + 0]
            val py = points[4 * i + 1]
            if (isTouching) {
                points[4 * i + 0] = points[4 * i + 2]
                points[4 * i + 1] = points[4 * i + 3]
                points[4 * i + 2] = k * points[4 * i + 2] + tx + (points[4 * i + 2] - px) * f
                points[4 * i + 3] = k * points[4 * i + 3] + ty + (points[4 * i + 3] - py) * f
            } else {
                points[4 * i + 0] = points[4 * i + 2]
                points[4 * i + 1] = points[4 * i + 3]
                points[4 * i + 2] += (points[4 * i + 0] - px) * f
                points[4 * i + 3] += (points[4 * i + 1] - py) * f
            }
            if (Math.random() < 0.1) {
                points[4 * i + 0] = width * Math.random().toFloat()
                points[4 * i + 1] = height * Math.random().toFloat()
                points[4 * i + 2] = points[4 * i + 0] + d * width * (Math.random() - 0.5).toFloat()
                points[4 * i + 3] = points[4 * i + 1] + d * height * (Math.random() - 0.5).toFloat()
            }
        }

        for (i in 0 until points.size - 4 step 4) {
            sparks.add(
                Line(
                    start = Offset(points[i], points[i + 1]),
                    end = Offset(points[i + 2], points[i + 3]),
                    color = Color(255, 200, 64),
                    strokeWidth = 2.dp
                )
            )
        }
    }

    BottomSheetScaffold(
        sheetContent = {
            PreferencesBottomSheet(
                prefState = state.value,
                onCheckedChange = { key, value ->
                    viewModel.updateSwarmPref(key, value)
                }
            )
        }
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(swarmPadBackground)
                .lightsAndSounds(
                    on = { x, y ->
                        viewModel.primaryOn()
                        isTouching = true

                        targetX = x
                        targetY = y

                        viewModel.primaryXY(targetX / width, 1 - targetY / height)
                    },
                    off = {
                        viewModel.primaryOff()
                        isTouching = false
                    },
                    lights = { change ->
                        with(change.position) {
                            targetX = x
                            targetY = y
                        }
                    },
                    sounds = { x, y ->
                        viewModel.primaryXY(x / width, 1 - y / height)
                    }
                )
        ) {
            sparkle()

            sparks.forEach { spark ->
                drawLine(
                    color = spark.color,
                    start = spark.start,
                    end = spark.end,
                    strokeWidth = spark.strokeWidth.toPx()
                )
            }
        }
    }
}