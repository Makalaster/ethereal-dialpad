package com.makalaster.etherealdialpad.pads

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.lightsAndSounds(
    on: (Offset) -> Unit,
    off: () -> Unit,
    lights: (PointerInputChange) -> Unit,
    sounds: (Float, Float) -> Unit
) = this.then(
    Modifier.pointerInput(Unit) {
        detectDragGestures(
            onDragStart = { on(it) },
            onDragEnd = { off() }
        ) { change, _ ->
            lights(change)
            with(change.position) {
                sounds(x, y)
            }
        }
    }
)