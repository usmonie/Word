package com.usmonie.compass.core.ui

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import com.usmonie.compass.core.gesture.Gesture
import com.usmonie.compass.core.gesture.OnBackInstance
import kotlinx.coroutines.flow.Flow

@Composable
actual fun PredictiveBackHandler(
    enabled: Boolean,
    onBack: suspend (progress: Flow<Gesture>) -> Unit,
    onBackPressed: () -> Unit,
    getDraggingOffset: () -> Float,
    content: @Composable BoxScope.() -> Unit,
) {
    val currentOnBack by rememberUpdatedState(onBack)
    val onBackScope = rememberCoroutineScope()

    val pointerInput: suspend PointerInputScope.() -> Unit = remember(enabled) {
        {
            var onBackInstance: OnBackInstance? = null

            detectHorizontalDragGestures(
                onDragStart = {
                    // in case the previous onBackInstance was started by a normal back gesture
                    // we want to make sure it's still cancelled before we start a predictive
                    // back gesture
                    onBackInstance?.cancel()
                    onBackInstance = OnBackInstance(onBackScope, true, currentOnBack)
                    onBackInstance?.send(Gesture.Start(it.x, it.y, size.width.toFloat()))
                },
                onDragEnd = {
                    // regular back.
                    onBackInstance?.apply {
                        if (!isPredictiveBack) {
                            cancel()
                            onBackInstance = null
                        } else {
                            send(Gesture.End(size.width.toFloat()))
                        }
                    }
                    if (onBackInstance == null) {
                        onBackInstance = OnBackInstance(onBackScope, false, currentOnBack)
                        onBackPressed()
                    }

                    // finally, we close the channel to ensure no more events can be sent
                    // but let the job complete normally
                    onBackInstance?.close()
                },
                onHorizontalDrag = { change, amount ->
                    change.consume()
                    onBackInstance?.send(
                        Gesture.Sliding(
                            positionX = (getDraggingOffset() + amount).coerceIn(0f, size.width.toFloat()),
                            positionY = change.position.y,
                            screenWidth = size.width.toFloat()
                        )
                    )
                },
                onDragCancel = {
                    onBackInstance?.send(Gesture.End(size.width.toFloat()))
//                    onBackInstance?.cancel()
                }
            )
        }
    }

    Box(
        modifier = Modifier.pointerInput(Unit, pointerInput),
        content = content
    )
}
