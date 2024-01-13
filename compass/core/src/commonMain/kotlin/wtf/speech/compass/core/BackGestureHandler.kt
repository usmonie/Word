package wtf.speech.compass.core

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.theapache64.rebugger.Rebugger
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

private val MAX_PREV_SCREEN_OFFSET = 150.dp

@Composable
fun BackGestureHandler(
    offset: Animatable<Float, AnimationVector1D>,
    routeManager: RouteManager,
    isGestureNavigationEnabled: Boolean,
    previousScreen: () -> Screen?,
    currentScreen: () -> Screen,
    content: @Composable BoxScope.(Screen) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Rebugger(
        trackMap = mapOf(
            "offset" to offset,
            "routeManager" to routeManager,
            "isGestureNavigationEnabled" to isGestureNavigationEnabled,
            "previousScreen" to previousScreen,
            "currentScreen" to currentScreen,
            "content" to content,
            "coroutineScope" to coroutineScope,
        ),
        composableName = "BackGestureHandler"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit, remember {
                {
                    if (isGestureNavigationEnabled) {
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
        val prev = previousScreen()

        val prevOffset = if (offset.value > 0 && prev != null) {
            (MAX_PREV_SCREEN_OFFSET * (100 / (offset.value + 1)))
                .coerceIn(0.dp, MAX_PREV_SCREEN_OFFSET)
        } else {
            0.dp
        }
        Box(
            Modifier
                .offset { IntOffset(-prevOffset.toPx().roundToInt(), 0) }
                .fillMaxSize()
        ) {
            if (offset.value > 0 && prev != null) {
                prev.Content()
            } else {
                content(currentScreen())
            }
        }

        if (prev != null && offset.value > 0) {
            Box(
                Modifier
                    .offset { IntOffset(offset.value.roundToInt(), 0) }
                    .shadow(16.dp)
                    .fillMaxSize()
            ) {
                content(currentScreen())
            }
        }
    }
}