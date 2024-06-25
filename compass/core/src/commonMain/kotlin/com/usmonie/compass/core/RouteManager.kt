package com.usmonie.compass.core

import androidx.collection.ScatterMap
import androidx.collection.emptyScatterMap
import androidx.compose.runtime.Immutable
import com.usmonie.compass.core.gesture.Gesture
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenId
import kotlinx.coroutines.flow.StateFlow

@Immutable
interface RouteManager : ScreenSaveableStateHolder {

	val state: StateFlow<RouteManagerState>

	/**
	 * Registers a new [NavigationGraphFactory] with the manager.
	 *
	 * @param graph The navigation graph to register.
	 */
	fun registerGraph(graph: NavigationGraphFactory)

	/**
	 * Registers a new [NavigationGraph] with the manager.
	 *
	 * @param graph The navigation graph to register.
	 */
	fun navigateTo(graph: NavigationGraph)

	/**
	 * Navigates to a different navigation graph and navigates to its initial screen.
	 *
	 * @param graphId The ID of the graph to switch to.
	 * @return True if the switch was successful, false otherwise.
	 */
	fun navigateTo(
		graphId: GraphId,
		storeInBackStack: Boolean = true,
		params: ScatterMap<String, String> = emptyScatterMap(),
		extras: Extra? = null
	): Boolean

	/**
	 * Navigates to a specified screen.
	 *
	 * @param screen The screen to navigate to.
	 * @return True if the navigation was successful, false otherwise.
	 */
	fun navigateTo(screen: Screen): Boolean

	/**
	 * Navigates to a specified screen.
	 *
	 * @param screenId The ID of the screen to navigate to.
	 * @param params Optional parameters to pass to the screen. Deep links as example
	 * @param extras Optional extras to pass to the screen.
	 * @return True if the navigation was successful, false otherwise.
	 */
	fun navigateTo(
		screenId: ScreenId,
		storeInBackStack: Boolean = true,
		params: ScatterMap<String, String>? = null,
		extras: Extra? = null
	): Boolean

	/**
	 * Navigates back to the previous screen or closes the current graph if there's no previous screen.
	 *
	 * @return True if the back navigation was successful, false otherwise.
	 */
	fun popBackstack(): Boolean
	fun popUntil(screen: Screen): Boolean
	fun popUntil(screenId: ScreenId): Boolean
	fun popGraph(): Boolean

	/**
	 * Handles deep link navigation.
	 *
	 * @param deepLink The deep link URI.
	 * @return True if the deep link was handled successfully, false otherwise.
	 */
	fun handleDeepLink(deepLink: String): Boolean

	suspend fun gestureHandle(gesture: Gesture): Boolean
}
