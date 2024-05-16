package com.usmonie.compass.core.ui

import androidx.collection.mutableScatterMapOf
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.RouteManager

@Composable
fun Root(
    routeManager: RouteManager,
    modifier: Modifier = Modifier,
    isGestureNavigationEnabled: Boolean = false
) {
    CompositionLocalProvider(LocalRouteManager provides routeManager) {
        BackGestureHandler(isGestureNavigationEnabled, routeManager) {
            AnimationScreen(modifier)
        }
    }
}

@Composable
private fun AnimationScreen(modifier: Modifier) {
    val routeManager = LocalRouteManager.current
    val state by routeManager.state.collectAsState()

    val zIndices = remember { mutableScatterMapOf<ScreenId, Float>() }
    val transition = updateTransition(state.currentScreen, label = "screen_updating")

    val saveableStateHolder = rememberSaveableStateHolder()
    transition.AnimatedContent(
        modifier = modifier,
        transitionSpec = {
            // If the initialState of the AnimatedContent is not in visibleEntries, we are in
            // a case where visible has cleared the old state for some reason, so instead of
            // attempting to animate away from the initialState, we skip the animation.
            val initialZIndex = zIndices[initialState.id]
                ?: 0f.also { zIndices[initialState.id] = 0f }
            val targetZIndex = when {
                targetState.id == initialState.id -> initialZIndex
                else -> initialZIndex + 1f
            }.also { zIndices[targetState.id] = it }

            ContentTransform(
                state.enterTransition(this),
                state.exitTransition(this),
                targetZIndex
            )
        },
        contentAlignment = Alignment.Center,
        content = { screen ->
            saveableStateHolder.SaveableStateProvider(screen.hashCode()) {
                screen.Content()
            }
        }
    )

    LaunchedEffect(transition.currentState, transition.targetState) {
        if (transition.currentState == transition.targetState) {
            zIndices.forEach { key, _ -> if (key != transition.targetState.id) zIndices.remove(key) }
        }
    }
}
