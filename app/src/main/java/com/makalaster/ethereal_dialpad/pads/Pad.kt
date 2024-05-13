package com.makalaster.ethereal_dialpad.pads

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makalaster.ethereal_dialpad.prefs.PreferencesBottomSheet
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pad(
    viewModel: PadViewModel,
    @StringRes padTitle: Int,
    onBackPressed: () -> Unit,
    padContent: @Composable BoxScope.(
        padding: PaddingValues,
        width: Float,
        height: Float,
        onTap: () -> Unit) -> Unit
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

        var showTopBar by remember { mutableStateOf(false) }

        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        var showBottomSheet by remember { mutableStateOf(false) }

        fun onTap() {
            showTopBar = !showTopBar
        }

        LaunchedEffect(showTopBar, showBottomSheet) {
            if (showTopBar && !showBottomSheet) {
                delay(5_000)
                onTap()
            }
        }

        fun toggleBottomSheet() {
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

        Scaffold(
            topBar = {
                AnimatedVisibility(
                    visible = showTopBar,
                    enter = slideInVertically(initialOffsetY = { -it }),
                    exit = slideOutVertically(targetOffsetY = { -it })
                ) {
                    TopAppBar(
                        colors = topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        title = {
                            Text(
                                stringResource(id = padTitle),
                                color = Color.Black
                            )
                        },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    onBackPressed()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Go Back",
                                    tint = Color.Black
                                )
                            }
                        },
                        actions = {
                            IconButton(
                                onClick = {
                                    toggleBottomSheet()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.MoreVert,
                                    contentDescription = "Show Prefs",
                                    tint = Color.Black
                                )
                            }
                        }
                    )
                }
            }
        ) { contentPadding ->
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