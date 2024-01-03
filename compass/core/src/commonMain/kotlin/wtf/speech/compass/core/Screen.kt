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
                println("enterTransition $id")
                400
            }
        )
    }

    open val exitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            targetOffset = {
                println("exitTransition $id")
                -100
            }
        )
    }

    open val popEnterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            initialOffset = {
                println("popEnterTransition $id")
                -100
            }
        )
    }

    open val popExitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            targetOffset = {
                println("popExitTransition $id")
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

