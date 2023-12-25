package wtf.speech.compass.core

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

@Composable
fun BackGestureHandler(routeManager: RouteManager) {
    // Создайте анимируемый объект для хранения текущего смещения
    val offset = remember { Animatable(0f) }
    // Используйте pointerInput для обнаружения горизонтальных жестов перетаскивания
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    coroutineScope.launch {
                        // Обновите смещение и ограничьте его в пределах размера экрана
                        offset.snapTo((offset.value + dragAmount).coerceIn(0f, size.width.toFloat()))
                    }
                }
            }
    ) {
        if (offset.value > 150) {
            routeManager.navigateBack()
        }
    }
}