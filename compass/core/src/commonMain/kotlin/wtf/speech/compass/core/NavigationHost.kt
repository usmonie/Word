package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
        val currentScreen = routeManager.currentScreen
        val previousScreen = routeManager.previousScreen
        val event by routeManager.lastEvent
        val offset: Animatable<Float, AnimationVector1D> = remember { Animatable(0f) }

        val enterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) =
            remember {
                {
                    when (val e = event) {
                        is NavigationEvent.Back -> e.targetScreen.popEnterTransition(this)
                        is NavigationEvent.Next -> e.targetScreen.enterTransition(this)
                        else -> EnterTransition.None
                    }
                }
            }
        val exitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) =
            remember {
                {
                    when (val e = event) {
                        is NavigationEvent.Back -> e.targetScreen.popExitTransition(this)
                        is NavigationEvent.Next -> e.targetScreen.exitTransition(this)
                        else -> ExitTransition.None
                    }
                }
            }
        LaunchedEffect(offset.value) {
            val e = event
            if (e is NavigationEvent.BackGesture.Ended.Success
                && offset.value == e.screenWidth.toFloat()
            ) {
                routeManager.gestureBackAnimationEnded()
            }
        }

        LaunchedEffect(event) {
            when (val e = event) {
                is NavigationEvent.BackGesture.Ended.Success -> {
                    if (offset.value < e.screenWidth) {
                        offset.animateTo(e.screenWidth.toFloat())
                    } else {
                        offset.animateTo(0f)
                    }
                }

                is NavigationEvent.BackGesture.Ended.Cancel -> offset.animateTo(0f)

                is NavigationEvent.BackGesture.Dragging -> offset.snapTo(e.offset)
                else -> offset.animateTo(0f)
            }
        }

        BackGestureHandler(
            offset,
            routeManager,
            isGestureNavigationEnabled,
            {
                val e = event
                if (e is NavigationEvent.BackGesture.Ended.Success) e.previousScreen
                else previousScreen
            },
            {
                val e = event
                if (e is NavigationEvent.BackGesture.Ended.Success) {
                    e.currentScreen
                } else currentScreen
            }
        ) { screen ->
            if (event is NavigationEvent.BackGesture) {
                screen.Content()
            } else {
                AnimationScreen(
                    screen,
                    enterTransition,
                    exitTransition,
                    modifier.matchParentSize()
                )
            }
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
        content = { screen ->
            screen.Content()
        }
    )

    LaunchedEffect(transition.currentState, transition.targetState) {
        if (transition.currentState == transition.targetState) {
            zIndices
                .filter { it.key != transition.targetState.id }
                .forEach { zIndices.remove(it.key) }
        }
    }
}
