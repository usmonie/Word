package com.usmonie.compass.core.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.usmonie.compass.core.gesture.ScreenGestureHandler
import com.usmonie.compass.core.randomUUID

@Immutable
abstract class Screen(val storeInBackStack: Boolean = true) {

    open val enterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            spring(stiffness = Spring.StiffnessMediumLow),
            initialOffset = { 400 }
        )
    }

    open val exitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            spring(stiffness = Spring.StiffnessMediumLow),
            targetOffset = { -200 }
        )
    }

    open val popEnterTransition: (AnimatedContentTransitionScope<Screen>.() -> EnterTransition) = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            spring(stiffness = Spring.StiffnessMediumLow),
            initialOffset = { -200 }
        )
    }

    open val popExitTransition: (AnimatedContentTransitionScope<Screen>.() -> ExitTransition) = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right,
            spring(stiffness = Spring.StiffnessMediumLow),
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

