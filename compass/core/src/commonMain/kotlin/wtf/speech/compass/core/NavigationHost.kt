package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
        val enterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) =
            remember {
                {
                    when (val state = routeManager.currentState) {
                        is NavigationState.Back -> state.targetScreen.popEnterTransition(this)
                        is NavigationState.Next -> state.targetScreen.enterTransition(this)
                        null -> EnterTransition.None
                    }
                }
            }

        val exitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) =
            remember {
                {
                    when (val state = routeManager.currentState) {
                        is NavigationState.Back -> state.targetScreen.popExitTransition(this)
                        is NavigationState.Next -> state.targetScreen.exitTransition(this)
                        null -> ExitTransition.None
                    }
                }
            }

        BackGestureHandler(isGestureNavigationEnabled) { screen ->
            AnimationScreen(
                screen,
                enterTransition,
                exitTransition,
                modifier.matchParentSize()
            )
        }
    }
}

@Composable
private fun AnimationScreen(
    currentScreen: Screen,
    enterTransition: AnimatedContentTransitionScope<Screen>.() -> EnterTransition,
    exitTransition: AnimatedContentTransitionScope<Screen>.() -> ExitTransition,
    modifier: Modifier
) {
    val zIndices = remember { mutableMapOf<String, Float>() }
    val transition = updateTransition(currentScreen, label = "entry")

    AnimationScreen(enterTransition, exitTransition, zIndices, transition, modifier)

    LaunchedEffect(transition.currentState, transition.targetState) {
        if (transition.currentState == transition.targetState) {
            zIndices
                .filter { it.key != transition.targetState.id }
                .forEach { zIndices.remove(it.key) }
        }
    }
}

@Composable
fun AnimationScreen(
    enterTransition: AnimatedContentTransitionScope<Screen>.() -> EnterTransition,
    exitTransition: AnimatedContentTransitionScope<Screen>.() -> ExitTransition,
    zIndices: MutableMap<String, Float>,
    transition: Transition<Screen>,
    modifier: Modifier = Modifier
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

            ContentTransform(
                enterTransition(this),
                exitTransition(this),
                targetZIndex
            )
        },
        Alignment.Center,
        contentKey = { it.id },
        content = { screen -> screen.Content() }
    )
}
