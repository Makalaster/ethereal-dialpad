package com.makalaster.etherealdialpad.pads

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.makalaster.etherealdialpad.prefs.PreferencesBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pad(
    viewModel: PadViewModel,
    padContent: @Composable BoxScope.(width: Float, height: Float) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val width: Float
        val height: Float
        LocalConfiguration.current.let {
            width = with(LocalDensity.current) { it.screenWidthDp.dp.toPx() }
            height = with(LocalDensity.current) { it.screenHeightDp.dp.toPx() }
        }

        val state = viewModel.flow.collectAsState()

        BottomSheetScaffold(
            sheetContent = {
                PreferencesBottomSheet(
                    prefState = state.value,
                    onCheckedChange = viewModel::updateBooleanPref,
                    onSelectionChanged = viewModel::updateStringPref
                )
            }
        ) {
            padContent(width, height)
        }
    }
}