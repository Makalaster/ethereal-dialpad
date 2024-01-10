package com.makalaster.ethereal_dialpad.pads

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.makalaster.ethereal_dialpad.prefs.PreferencesBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pad(
    viewModel: PadViewModel,
    padContent: @Composable BoxScope.(padding: PaddingValues, width: Float, height: Float, onTap: () -> Unit) -> Unit
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

        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        var showBottomSheet by remember {
            mutableStateOf(false)
        }

        fun onTap() {
            if (showBottomSheet) {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            } else {
                showBottomSheet = true
            }
        }

        Scaffold { contentPadding ->
            padContent(contentPadding, width, height) { onTap() }

            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = sheetState
                ) {
                    PreferencesBottomSheet(
                        prefState = state.value,
                        onCheckedChange = viewModel::updateBooleanPref,
                        onSelectionChanged = viewModel::updateStringPref
                    )
                }
            }
        }
    }
}