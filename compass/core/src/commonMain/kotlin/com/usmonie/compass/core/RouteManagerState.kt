package com.usmonie.compass.core

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.Immutable
import com.usmonie.compass.core.ui.Screen

@Immutable
data class RouteManagerState(
	val currentGraph: NavigationGraph,
	val currentScreen: Screen,
	val previousScreen: Screen? = null,
	val navigationState: Navigating? = null
) {
	val draggingOffset: Animatable<Float, AnimationVector1D> = Animatable(-1f)

	val canPop: Boolean
		get() = previousScreen != null

	val showPrevious: Boolean
		get() = canPop && (draggingOffset.isRunning || draggingOffset.value > -1f)

	val enterTransition: AnimatedContentTransitionScope<Screen>.() -> EnterTransition = {
		when (navigationState) {
			null -> EnterTransition.None
			RouteManagerState.Navigating.BACK ->
				currentScreen.popEnterTransition(this)

			RouteManagerState.Navigating.FORWARD ->
				currentScreen.enterTransition(this)
		}
	}
	val exitTransition: AnimatedContentTransitionScope<Screen>.() -> ExitTransition = {
		when (navigationState) {
			null -> ExitTransition.None
			RouteManagerState.Navigating.BACK ->
				currentScreen.popExitTransition(this)

			RouteManagerState.Navigating.FORWARD ->
				currentScreen.exitTransition(this)
		}
	}

	enum class Navigating {
		FORWARD,
		BACK
	}
}
