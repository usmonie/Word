package com.usmonie.compass.core.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.usmonie.compass.core.gesture.ScreenGestureHandler
import com.usmonie.compass.core.randomUUID

@Immutable
abstract class Screen(val storeInBackStack: Boolean = true) {

    open val enterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            initialOffset = { 400 }
        )
    }

    open val exitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            targetOffset = { -100 }
        )
    }

    open val popEnterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            initialOffset = { -100 }
        )
    }

    open val popExitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            targetOffset = { 400 },
        )
    }

    open val screenGestureHandler: ScreenGestureHandler = ScreenGestureHandler.NoHandling

    abstract val id: ScreenId

    internal val uuid = randomUUID()

    @Composable
    abstract fun Content()

    open fun onCleared() {}
}

