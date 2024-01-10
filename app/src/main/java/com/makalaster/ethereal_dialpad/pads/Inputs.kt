package com.makalaster.ethereal_dialpad.pads

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.lightsAndSounds(
    on: (Float, Float) -> Unit,
    off: () -> Unit,
    tap: () -> Unit,
    lights: (PointerInputChange) -> Unit,
    sounds: (Float, Float) -> Unit
) = this.then(
    Modifier
//        .pointerInput(Unit) {
//            awaitEachGesture {
//                val down = awaitFirstDown()
//                down.consume()
//                down.position.also {
//                    on(it.x, it.y)
//                }
//
//                var change = awaitTouchSlopOrCancellation(down.id) { inputChange, _ ->
//
//                    lights(inputChange)
//                    sounds(inputChange.position.x, inputChange.position.y)
//                    inputChange.consume()
//                }
//
//                while (change != null && change.pressed) {
//                    change = awaitDragOrCancellation(change.id)
//
//                    if (change != null && change.pressed) {
//                        lights(change)
//                        sounds(change.position.x, change.position.y)
//
//                        change.consume()
//                    } else {
//                        off()
//                    }
//                }
//
//                off()
//            }
//        }
        .pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    tap()
                },
                onPress = {
                    on(it.x, it.y)
                    if (tryAwaitRelease()) {
                        off()
                    }
                }
            )
        }
        .pointerInput(Unit) {
            detectDragGestures(
                onDragStart = {
                    on(it.x, it.y)
                    sounds(it.x, it.y)
                },
                onDrag = { change, _ ->
                    lights(change)
                    sounds(change.position.x, change.position.y)
                },
                onDragEnd = {
                    off()
                }
            )
        }
)