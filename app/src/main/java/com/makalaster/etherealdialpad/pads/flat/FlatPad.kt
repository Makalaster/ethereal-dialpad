package com.makalaster.etherealdialpad.pads.flat

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makalaster.etherealdialpad.pads.lightsAndSounds
import com.makalaster.etherealdialpad.ui.theme.flatPadBackground

@Composable
fun FlatPad(
    viewModel: FlatViewModel = viewModel(),
    width: Float,
    height: Float
) {
    var color by remember { mutableStateOf(flatPadBackground) }

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color)
            }
            .lightsAndSounds(
                on = { x, y ->
                    viewModel.primaryOn()
                    viewModel.primaryXY(
                        viewModel.transform(x, width),
                        viewModel.transform(y, height)
                    )
                    color = viewModel.colorTransform(x, width, y, height)
                },
                off = {
                    color = flatPadBackground
                    viewModel.primaryOff()
                },
                lights = { change ->
                    color = viewModel.colorTransform(change.position.x, width, change.position.y, height)
                },
                sounds = { x, y ->
                    viewModel.primaryXY(
                        viewModel.transform(x, width),
                        viewModel.transform(y, height)
                    )
                }
            )
    )
}