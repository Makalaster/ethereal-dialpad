package com.makalaster.etherealdialpad.pads

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitTouchSlopOrCancellation
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.AwaitPointerEventScope
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.colorResource
import com.makalaster.etherealdialpad.ui.theme.EtherealDialpadTheme
import com.makalaster.etherealdialpad.ui.theme.padStartBackground

@Composable
fun BasicPad(

) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = padStartBackground)
            .dragMotionEvent(
                onDragStart = {

                },
                onDrag = {

                },
                onDragEnd = {

                }
            )
    ) {

    }
}

suspend fun AwaitPointerEventScope.awaitDragMotionEvent(
    onDragStart: (PointerInputChange) -> Unit = {},
    onDrag: (PointerInputChange) -> Unit = {},
    onDragEnd: (PointerInputChange) -> Unit = {}
) {
    // Wait for at least one pointer to press down, and set first contact position
    val down: PointerInputChange = awaitFirstDown()
    onDragStart(down)

    var pointer = down

    // 🔥 Waits for drag threshold to be passed by pointer
    // or it returns null if up event is triggered
    val change: PointerInputChange? =
        awaitTouchSlopOrCancellation(down.id) { change: PointerInputChange, _: Offset ->
            // 🔥🔥 If consumePositionChange() is not consumed drag does not
            // function properly.
            // Consuming position change causes change.positionChanged() to return false.
            if (change.positionChange() != Offset.Zero) change.consume()
        }

    if (change != null) {
        // 🔥 Calls  awaitDragOrCancellation(pointer) in a while loop
        drag(change.id) { pointerInputChange: PointerInputChange ->
            pointer = pointerInputChange
            onDrag(pointer)
        }

        // All of the pointers are up
        onDragEnd(pointer)
    } else {
        // Drag threshold is not passed and last pointer is up
        onDragEnd(pointer)
    }
}

fun Modifier.dragMotionEvent(
    onDragStart: (PointerInputChange) -> Unit = {},
    onDrag: (PointerInputChange) -> Unit = {},
    onDragEnd: (PointerInputChange) -> Unit = {}
) = this.then(
    Modifier.pointerInput(Unit) {
        awaitEachGesture {
            awaitDragMotionEvent(onDragStart, onDrag, onDragEnd)
        }
    }
)