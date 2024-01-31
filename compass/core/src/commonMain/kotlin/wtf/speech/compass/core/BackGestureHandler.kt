package wtf.speech.compass.core

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private val MAX_PREV_SCREEN_OFFSET = 150.dp

@Composable
fun BackGestureHandler(
    getOffset: () -> Animatable<Float, AnimationVector1D>,
    routeManager: RouteManager,
    isGestureNavigationEnabled: Boolean,
    previousScreen: () -> Screen?,
    currentScreen: () -> Screen,
    content: @Composable BoxScope.(Screen) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val current = currentScreen()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .pointerInput(Unit, remember {
                {
                    val offset = getOffset()
                    if (isGestureNavigationEnabled && current.backGestureEnabled) {
                        detectHorizontalDragGestures(
                            onDragEnd = {
                                coroutineScope.launch {
                                    routeManager.gestureBackEnded(offset.value, size.width)
                                }
                            },
                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(0f, size.width.toFloat())

                                coroutineScope.launch {
                                    routeManager.gestureBackOffset(newOffset, size.width)
                                }
                            }
                        )
                    }
                }
            }),
    ) {
        val offset = getOffset()
        val prevScreen = previousScreen()
        val showPrev = (offset.value > 0 && prevScreen != null)
        val prevOffset = if (showPrev) {
            with(LocalDensity.current) {
                (-MAX_PREV_SCREEN_OFFSET + offset.value.toDp() * 0.5f)
                    .coerceIn(-MAX_PREV_SCREEN_OFFSET, 0.dp)
            }
        } else {
            0.dp
        }

        Box(
            Modifier
                .graphicsLayer {
                    translationX = prevOffset.toPx()
                }
                .fillMaxSize()
        ) {
            if (showPrev) {
                prevScreen?.Content()
            } else {
                content(currentScreen())
            }
        }

        if (showPrev) {
            Box(
                Modifier
                    .graphicsLayer {
                        translationX = offset.value
                    }
                    .shadow(16.dp)
                    .fillMaxSize()
            ) {
                content(current)
            }
        }
    }
}