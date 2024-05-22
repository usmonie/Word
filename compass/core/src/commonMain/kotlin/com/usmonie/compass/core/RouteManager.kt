package com.usmonie.compass.core

import androidx.collection.ScatterMap
import androidx.compose.runtime.Immutable
import com.usmonie.compass.core.gesture.Gesture
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenId
import kotlinx.coroutines.flow.StateFlow

@Immutable
abstract class RouteManager : ScreenSaveableStateHolder {

    abstract val state: StateFlow<RouteManagerState>

    internal abstract val screensSavedStates: MutableMap<String, Map<String, List<Any?>>>

    /**
     * Registers a new [NavigationGraph] with the manager.
     *
     * @param graph The navigation graph to register.
     */
    abstract fun registerGraph(graph: NavigationGraph)

    /**
     * Navigates to a different navigation graph and navigates to its initial screen.
     *
     * @param graphId The ID of the graph to switch to.
     * @return True if the switch was successful, false otherwise.
     */
    abstract fun navigateTo(graphId: GraphId): Boolean

    /**
     * Navigates to a specified screen.
     *
     * @param screen The screen to navigate to.
     * @return True if the navigation was successful, false otherwise.
     */
    abstract fun navigateTo(screen: Screen): Boolean

    /**
     * Navigates to a specified screen.
     *
     * @param screenId The ID of the screen to navigate to.
     * @param params Optional parameters to pass to the screen. Deep links as example
     * @param extras Optional extras to pass to the screen.
     * @return True if the navigation was successful, false otherwise.
     */
    abstract fun navigateTo(
        screenId: ScreenId,
        params: ScatterMap<String, String>? = null,
        extras: Extra? = null
    ): Boolean

    /**
     * Navigates back to the previous screen or closes the current graph if there's no previous screen.
     *
     * @return True if the back navigation was successful, false otherwise.
     */
    abstract fun popBackstack(): Boolean
    abstract fun popUntil(screen: Screen): Boolean
    abstract fun popUntil(screenId: ScreenId): Boolean
    abstract fun popGraph(): Boolean

    /**
     * Handles deep link navigation.
     *
     * @param deepLink The deep link URI.
     * @return True if the deep link was handled successfully, false otherwise.
     */
    abstract fun handleDeepLink(deepLink: String): Boolean

    abstract suspend fun gestureHandle(gesture: Gesture): Boolean
}
