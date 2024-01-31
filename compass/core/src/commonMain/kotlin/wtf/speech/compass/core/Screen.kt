package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable

abstract class Screen(private val viewModel: ViewModel? = null) {
    open val enterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            initialOffset = {
                400
            }
        )
    }

    open val exitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            targetOffset = {
                -100
            }
        )
    }

    open val popEnterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            initialOffset = {
                -100
            }
        )
    }

    open val popExitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            targetOffset = {
                400
            }
        )
    }

    open val backGestureEnabled: Boolean = true

    abstract val id: String

    @Composable
    abstract fun Content()

    internal fun onCleared() {
        viewModel?.onCleared()
    }
}

