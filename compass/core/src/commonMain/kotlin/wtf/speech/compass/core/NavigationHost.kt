package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

/**
 * A composable that observes changes to the current screen in [RouteManagerImpl] and displays its content.
 *
 * @param routeManager The [RouteManagerImpl] responsible for managing navigation.
 */
@ExperimentalAnimationApi
@Composable
fun NavigationHost(
    routeManager: RouteManager,
    modifier: Modifier = Modifier,
    isGestureNavigationEnabled: Boolean = false
) {
    CompositionLocalProvider(LocalRouteManager provides routeManager) {
        val currentScreen: Screen = routeManager.currentScreen
        val event by routeManager.events.collectAsState(null)
        val zIndices = remember { mutableMapOf<String, Float>() }
        val offset = remember { Animatable(0f) }
        val coroutineScope = rememberCoroutineScope()

        val finalEnter: AnimatedContentTransitionScope<Screen>.() -> EnterTransition =
            remember(event) {
                {
                    println("animation enter: initial = ${initialState.id}, target = ${targetState.id}, $event")

                    when (event) {
                        is NavigationEvent.Back -> targetState.popEnterTransition(this)
                        is NavigationEvent.BackGesture -> targetState.popEnterTransition(this)
                        is NavigationEvent.Next -> targetState.enterTransition(this)
                        null -> targetState.enterTransition(this)
                    }
                }
            }

        val finalExit: AnimatedContentTransitionScope<Screen>.() -> ExitTransition =
            remember(event) {
                {
                    println("animation exit:initial = ${initialState.id}, target = ${targetState.id}, $event")

                    when (event) {
                        is NavigationEvent.Back -> initialState.popExitTransition(this)
                        is NavigationEvent.BackGesture -> initialState.popExitTransition(this)
                        is NavigationEvent.Next -> initialState.exitTransition(this)
                        null -> initialState.exitTransition(this)
                    }
                }
            }
        val transition = updateTransition(currentScreen, label = "entry")

        BackGestureHandler(
            offset,
            { coroutineScope.launch { offset.snapTo(it) } },
            routeManager,
            isGestureNavigationEnabled
        ) {
            transition.AnimatedContent(
                modifier,
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

                    ContentTransform(finalEnter(this), finalExit(this), targetZIndex)
                },
                Alignment.Center,
                contentKey = { it.id },
                content = { it.Content() }
            )
        }

        LaunchedEffect(transition.currentState, transition.targetState) {
            if (transition.currentState == transition.targetState) {
                zIndices
                    .filter { it.key != transition.targetState.id }
                    .forEach { zIndices.remove(it.key) }
            }
        }
    }
}
