package wtf.speech.compass.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable

abstract class Screen(private val viewModel: ViewModel) {
    open val enterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            initialOffset = {
                println("${id} enterTransition $it")
                400
            }
        )
    }
    open val exitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            targetOffset = {
                println("${id} exitTransition $it")
                -100
            }
        )
    }

    open val popEnterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            initialOffset = {
                println("${id} popEnterTransition $it")
                -100
            }
        )
    }

    open val popExitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            targetOffset = {
                println("${id} popExitTransition $it")
                400
            }
        )
    }

    abstract val id: String

    @Composable
    abstract fun Content()

    internal fun onCleared() {
        viewModel.onCleared()
    }
}

