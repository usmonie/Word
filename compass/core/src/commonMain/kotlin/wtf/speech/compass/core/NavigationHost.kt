package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A composable that observes changes to the current screen in [RouteManagerImpl] and displays its content.
 *
 * @param routeManager The [RouteManagerImpl] responsible for managing navigation.
 */
@Suppress("NonSkippableComposable")
@ExperimentalAnimationApi
@Composable
fun NavigationHost(
    routeManager: RouteManager,
    vararg values: ProvidedValue<*>,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalRouteManager provides routeManager, *values) {
        val currentScreen: Screen = routeManager.currentScreen

        val event by routeManager.events.collectAsState(null)

        val zIndices = remember { mutableMapOf<String, Float>() }

        val finalEnter: AnimatedContentTransitionScope<Screen>.() -> EnterTransition = {
            when (event) {
                is NavigationEvent.Back -> targetState.popEnterTransition(this)
                is NavigationEvent.BackGesture -> targetState.popEnterTransition(this)
                is NavigationEvent.Next -> targetState.enterTransition(this)
                null -> targetState.enterTransition(this)
            }
        }

        val finalExit: AnimatedContentTransitionScope<Screen>.() -> ExitTransition =
            {
                when (event) {
                    is NavigationEvent.Back -> initialState.popExitTransition(this)
                    is NavigationEvent.BackGesture -> initialState.popExitTransition(this)
                    is NavigationEvent.Next -> initialState.exitTransition(this)
                    null -> initialState.exitTransition(this)
                }
            }

//        DisposableEffect(true) {
//            onDispose {
//                visibleEntries.forEach { entry ->
//                    composeNavigator.onTransitionComplete(entry)
//                }
//            }
//        }

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

                    ContentTransform(finalEnter(this), finalExit(this), targetZIndex)
//                } else {
//                    EnterTransition.None togetherWith ExitTransition.None
//                }
            },
            Alignment.Center,
            contentKey = { it.id }
        ) {
            // In some specific cases, such as clearing your back stack by changing your
            // start destination, AnimatedContent can contain an entry that is no longer
            // part of visible entries since it was cleared from the back stack and is not
            // animating. In these cases the currentEntry will be null, and in those cases,
            // AnimatedContent will just skip attempting to transition the old entry.
            // See https://issuetracker.google.com/238686802
//            val currentEntry = visibleEntries.lastOrNull { entry -> it == entry }

            // while in the scope of the composable, we provide the navBackStackEntry as the
            // ViewModelStoreOwner and LifecycleOwner
//            currentEntry?.LocalOwnersProvider(saveableStateHolder) {
//                (currentEntry.destination as ComposeNavigator.Destination)
//                    .content(this, currentEntry)

                it.Content()
            }
        LaunchedEffect(transition.currentState, transition.targetState) {
            if (transition.currentState == transition.targetState) {
//                visibleEntries.forEach { entry ->
//                    composeNavigator.onTransitionComplete(entry)
//                }
                zIndices
                    .filter { it.key != transition.targetState.id }
                    .forEach { zIndices.remove(it.key) }
            }
        }
    }
}


private fun AnimatedContentTransitionScope<Screen>.enterTransition(
    direction: AnimatedContentTransitionScope.SlideDirection
) = slideIntoContainer(
    direction,
    tween(300),
    initialOffset = {
        if (direction == AnimatedContentTransitionScope.SlideDirection.Right) 0 else it
    }
)

private fun AnimatedContentTransitionScope<Screen>.exitTransition(
    direction: AnimatedContentTransitionScope.SlideDirection
) = slideOutOfContainer(direction, tween(300),
    targetOffset = {
        if (direction == AnimatedContentTransitionScope.SlideDirection.Right) it else 0
    })
