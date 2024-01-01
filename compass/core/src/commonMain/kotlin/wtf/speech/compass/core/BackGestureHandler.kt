package wtf.speech.compass.core

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun BackGestureHandler(
    offset: Animatable<Float, AnimationVector1D>,
    onOffsetChanged: (Float) -> Unit,
    routeManager: RouteManager,
    isGestureNavigationEnabled: Boolean,
    content: @Composable () -> Unit
) {
    // Используйте pointerInput для обнаружения горизонтальных жестов перетаскивания
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    // Обновите смещение и ограничьте его в пределах размера экрана
                    onOffsetChanged((offset.value + dragAmount).coerceIn(0f, size.width.toFloat()))
                }
            }
    ) {
        if (offset.value > 250 && isGestureNavigationEnabled) {
            routeManager.navigateBack()
            onOffsetChanged(0f)
        }

        content()
    }
}