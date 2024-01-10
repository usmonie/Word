package wtf.speech.compass.core

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

@Composable
fun BackGestureHandler(
    offset: Animatable<Float, AnimationVector1D>,
    onOffsetChanged: (Float) -> Unit,
    routeManager: RouteManager,
    isGestureNavigationEnabled: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .pointerInput(Unit) {
////                kotlinx.coroutines.coroutineScope {
////                    var currentId: Long = -1L
////                    awaitPointerEventScope {
////                        while (true) {
////                            awaitPointerEvent(PointerEventPass.Initial).changes.forEach { pointerInfo ->
////                                when {
////                                    pointerInfo.pressed && currentId == -1L -> currentId =
////                                        pointerInfo.id.value
////
////                                    pointerInfo.pressed.not() && currentId == pointerInfo.id.value -> currentId =
////                                        -1
////
////                                    pointerInfo.id.value != currentId && currentId != -1L -> pointerInfo.consume()
////                                    else -> Unit
////                                }
////                            }
////                        }
////                    }
////                }
//            }
            .pointerInput(Unit) {
                if (isGestureNavigationEnabled) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            coroutineScope.launch {
                                routeManager.gestureBackEnded(offset.value, size.width)
                            }
                            onOffsetChanged(0f)
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            val newOffset = (offset.value + dragAmount).coerceIn(
                                0f,
                                size.width.toFloat()
                            )

                            coroutineScope.launch {
                                routeManager.gestureBackOffset(newOffset, size.width)
                            }
                            onOffsetChanged(newOffset)
                        }
                    )
                }
            },
        content = content
    )
}