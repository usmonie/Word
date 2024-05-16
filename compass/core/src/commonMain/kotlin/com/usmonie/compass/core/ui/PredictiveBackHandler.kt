package com.usmonie.compass.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.GraphicsLayerScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.RouteManager
import com.usmonie.compass.core.RouteManagerState
import com.usmonie.compass.core.gesture.Gesture
import kotlinx.coroutines.flow.Flow

@Composable
expect fun PredictiveBackHandler(
    enabled: Boolean = true,
    onBack: suspend (progress: Flow<Gesture>) -> Unit,
    onBackPressed: () -> Unit,
    getDraggingOffset: () -> Float,
    content: @Composable BoxScope.() -> Unit,
)

@Composable
fun BackGestureHandler(
    isGestureNavigationEnabled: Boolean,
    routeManager: RouteManager,
    content: @Composable (BoxScope.(Screen) -> Unit)
) {
    val state by routeManager.state.collectAsState()

    PredictiveBackHandler(
        isGestureNavigationEnabled && state.canPop,
        { it.collect(routeManager::gestureHandle) },
        routeManager::popBackstack,
        { state.draggingOffset.value },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (isGestureNavigationEnabled && state.draggingOffset.value > -1f) {
                PreviousScreen()
            }
            CurrentScreen(content)
        }
    }
}

@Composable
private fun PreviousScreen() {
    val routeManager = LocalRouteManager.current
    val state by routeManager.state.collectAsState()
    val previousScreen by derivedStateOf { state.previousScreen }

    val showPrev by remember(state) {
        derivedStateOf { state.showPrevious }
    }

    if (showPrev && previousScreen != null) {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .graphicsLayer {
                    val prevOffset = if (showPrev) { offset(state) } else { 0.dp }.toPx()

                    translationX = prevOffset
                }
                .fillMaxSize()
        ) {
            previousScreen?.Content()
        }
    }
}

private fun GraphicsLayerScope.offset(state: RouteManagerState) =
    (MAX_PREV_SCREEN_OFFSET + state.draggingOffset.value.toDp() * PREVIOUS_SCREEN_RATIO)
        .coerceIn(MAX_PREV_SCREEN_OFFSET, 0.dp)

@Composable
private fun CurrentScreen(content: @Composable BoxScope.(Screen) -> Unit) {
    val routeManager = LocalRouteManager.current
    val state by routeManager.state.collectAsState()

    Box(
        Modifier
            .graphicsLayer {
                translationX = state.draggingOffset.value + 1f
                shadowElevation = 24.dp.toPx()
            }
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        content(state.currentScreen)
    }
}

private val MAX_PREV_SCREEN_OFFSET = -(180.dp)
private const val PREVIOUS_SCREEN_RATIO = 0.6f
