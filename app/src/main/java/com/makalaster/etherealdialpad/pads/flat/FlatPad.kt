package com.makalaster.etherealdialpad.pads.flat

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.makalaster.etherealdialpad.pads.lightsAndSounds
import com.makalaster.etherealdialpad.prefs.PreferencesBottomSheet
import com.makalaster.etherealdialpad.ui.theme.flatPadBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlatPad(
    viewModel: FlatViewModel = viewModel()
) {
    val width: Float
    val height: Float
    LocalConfiguration.current.let {
        width = with(LocalDensity.current) { it.screenWidthDp.dp.toPx() }
        height = with(LocalDensity.current) { it.screenHeightDp.dp.toPx() }
    }

    var color by remember { mutableStateOf(flatPadBackground) }
    val state = viewModel.flow.collectAsState()

    BottomSheetScaffold(
        sheetContent = {
            PreferencesBottomSheet(
                state.value,
                onCheckedChange = { key, checked ->
                    viewModel.updateBooleanPref(key, checked)
                },
                onSelectionChanged = { key, selected ->
                    viewModel.updateStringPref(key, selected)
                }
            )
        }
    ) {
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
}