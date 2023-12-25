package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable

abstract class Screen(private val viewModel: ViewModel) {
    open val enterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) =
        {
            println("TRANSITION: enterTransition targetState = ${targetState.id} initialState = ${initialState.id}")

            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                initialOffset = { 400 }
            )
//            slideInHorizontally(initialOffsetX = { 400 }) /*+ scaleIn(initialScale = 0.8f)*/
        }
    open val exitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) =
        {
            println("TRANSITION: exitTransition targetState = ${targetState.id} initialState = ${initialState.id}")
            fadeOut(targetAlpha = .9f)
        }

    open val popEnterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) =
        {
            println("TRANSITION: popEnterTransition targetState = ${targetState.id} initialState = ${initialState.id}")

            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                initialOffset = { -100 }
            )
//            slideInHorizontally(initialOffsetX = { -300 }) /*+ scaleIn(initialScale = 1.2f)*/
        }

    open val popExitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) =
        {
            println("TRANSITION: popExitTransition targetState = ${targetState.id} initialState = ${initialState.id}")
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, targetOffset = { 300 })
        }

    abstract val id: String

    @Composable
    abstract fun Content()

    internal fun onCleared() {
        viewModel.onCleared()
    }
}

