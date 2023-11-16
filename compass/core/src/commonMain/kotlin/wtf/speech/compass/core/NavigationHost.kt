package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

/**
 * A composable that observes changes to the current screen in [RouteManagerImpl] and displays its content.
 *
 * @param routeManager The [RouteManagerImpl] responsible for managing navigation.
 */
@ExperimentalAnimationApi
@Composable
fun NavigationHost(routeManager: RouteManager, vararg values: ProvidedValue<*>) {
    CompositionLocalProvider(LocalRouteManager provides routeManager, *values) {
        val currentScreen: Screen = routeManager.currentScreen

        val event by routeManager.events.collectAsState()

//        val rightScreenTranslation = animateIntOffsetAsState(when (event) {
//            is NavigationEvent.Back ->
//            is NavigationEvent.BackGesture -> TODO()
//            is NavigationEvent.Next -> TODO()
//            null -> IntOffset.Zero
//        })

//        val animation: AnimatedContentTransitionScope<Screen>.() -> ContentTransform =
//            remember(event) {
//                {
//                    when (event) {
//                        NavigationEvent.NEXT -> enterTransition(
//                            AnimatedContentTransitionScope.SlideDirection.Left,
//                        ) togetherWith exitTransition(
//                            AnimatedContentTransitionScope.SlideDirection.Left,
//                        )
//
//                        NavigationEvent.BACK -> enterTransition(
//                            AnimatedContentTransitionScope.SlideDirection.Right
//                        ) togetherWith exitTransition(
//                            AnimatedContentTransitionScope.SlideDirection.Right
//                        )
//                    }
//                }
//            }

//        when (event) {
//            is NavigationEvent.Back -> TODO()
//            is NavigationEvent.BackGesture -> TODO()
//            is NavigationEvent.Next -> {
//                Box(
//                    Modifier
//                        .fillMaxSize()
//                        .graphicsLayer {
//                            this.translationX =
//                        }
//                ) {
//                    currentScreen.Content()
//                }
//            }
//
//            null -> currentScreen.Content()
//        }
//

        currentScreen.Content()

//        AnimatedContent(
//            currentScreen,
//            Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center,
//            content = {
//                currentScreen.Content()
//            }
//        )
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
