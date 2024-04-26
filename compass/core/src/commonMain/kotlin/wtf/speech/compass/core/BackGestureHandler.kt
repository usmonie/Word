package wtf.speech.compass.core

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

private val MAX_PREV_SCREEN_OFFSET = -(150.dp)

@Composable
fun BackGestureHandler(
    isGestureNavigationEnabled: Boolean,
    content: @Composable BoxScope.(Screen) -> Unit
) {
    val routeManager = LocalRouteManager.current
    val coroutineScope = rememberCoroutineScope()
    val pointerInput: suspend PointerInputScope.() -> Unit = remember(isGestureNavigationEnabled) {
        {
            if (isGestureNavigationEnabled) {
                val offset = routeManager.draggingOffset
                detectHorizontalDragGestures(
                    onDragEnd = {
                        coroutineScope.launch {
                            routeManager.gestureBackEnded(offset.value, size.width)
                        }
                    },
                    onHorizontalDrag = { change, dragAmount ->
                        change.consume()

                        coroutineScope.launch {
                            val newOffset = (offset.value + dragAmount)
                                .coerceIn(0f, size.width.toFloat())
                            routeManager.gestureBackOffset(newOffset, size.width)
                        }
                    }
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit, pointerInput),
    ) {
        PreviousScreen()
        CurrentScreen(content)
    }
}

@Composable
private fun CurrentScreen(content: @Composable BoxScope.(Screen) -> Unit) {
    val routeManager = LocalRouteManager.current

    Box(
        Modifier
            .graphicsLayer {
                translationX = routeManager.draggingOffset.value
                shadowElevation = 24.dp.toPx()
            }
            .fillMaxSize()
    ) {
        val currentScreen by routeManager.currentScreen.collectAsState()
        content(currentScreen)
    }
}

@Composable
private fun PreviousScreen() {
    val routeManager = LocalRouteManager.current
    val previousScreen by routeManager.previousScreen.collectAsState()

    val showPrev by remember(previousScreen) {
        derivedStateOf {
            previousScreen != null
        }
    }

    val showCurrent by remember(previousScreen) {
        derivedStateOf { routeManager.draggingOffset.isRunning && routeManager.draggingOffset.targetValue > 0 }
    }

    if (showPrev || showCurrent) {
        Box(
            Modifier
                .graphicsLayer {
                    val prevOffset = if (showPrev || showCurrent) {
                        (MAX_PREV_SCREEN_OFFSET + routeManager.draggingOffset.value.toDp() * 0.6f)
                            .coerceIn(MAX_PREV_SCREEN_OFFSET, 0.dp)
                    } else {
                        0.dp
                    }.toPx()

                    translationX = prevOffset
                }
                .fillMaxSize()
        ) {
            previousScreen?.Content()
        }
    }
}