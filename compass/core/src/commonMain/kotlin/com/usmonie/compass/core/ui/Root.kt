package com.usmonie.compass.core.ui

import androidx.collection.mutableScatterMapOf
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.usmonie.compass.core.LocalRouteManager
import com.usmonie.compass.core.RouteManager
import com.usmonie.compass.core.gesture.isGestureSupported

@Composable
fun Root(
    routeManager: RouteManager,
    modifier: Modifier = Modifier,
    isGestureNavigationEnabled: Boolean = false
) {
    val saveableState = @Composable { key: Screen, content: @Composable () -> Unit ->
        routeManager.SaveableStateProvider(key, content)
    }

    CompositionLocalProvider(LocalRouteManager provides routeManager) {
        BackGestureHandler(
            saveableState,
            isGestureNavigationEnabled && isGestureSupported,
            routeManager,
        ) {
            val state by routeManager.state.collectAsState()
            AnimationScreen(it, state.enterTransition, state.exitTransition, saveableState, modifier)
        }
    }
}

@Composable
private fun AnimationScreen(
    currentScreen: Screen,
    enterTransition: AnimatedContentTransitionScope<Screen>.() -> EnterTransition,
    exitTransition: AnimatedContentTransitionScope<Screen>.() -> ExitTransition,
    saveableState: @Composable (Screen, @Composable () -> Unit) -> Unit,
    modifier: Modifier
) {
    val zIndices = remember { mutableScatterMapOf<ScreenId, Float>() }
    val transition = updateTransition(currentScreen, label = "screen_updating")

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
                enterTransition(this),
                exitTransition(this),
                targetZIndex
            )
        },
        contentAlignment = Alignment.Center,
        content = { screen ->
            saveableState(screen) {
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
