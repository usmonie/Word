package com.usmonie.compass.core

import androidx.collection.MutableScatterMap
import androidx.collection.ScatterMap
import androidx.collection.mutableScatterMapOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.usmonie.compass.core.gesture.Gesture
import com.usmonie.compass.core.gesture.ScreenGestureHandler
import com.usmonie.compass.core.ui.DeepLinkScreenFactory
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.core.utils.Stack
import com.usmonie.core.domain.tools.fastLastOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val SCREEN_WIDTH_PORTION_FOR_BACK_GESTURE = .20

@Suppress("TooManyFunctions")
internal class BaseRouteManager : RouteManager() {
    private val graphsRegistered: MutableScatterMap<GraphId, NavigationGraph> = mutableScatterMapOf()
    private val graphsBackstack: Stack<NavigationGraph> = Stack(mutableListOf())

    private val currentGraph: NavigationGraph
        get() = graphsBackstack.peek()

    private val currentState by lazy { MutableStateFlow(RouteManagerState(currentGraph.lastScreen)) }
    override val state: StateFlow<RouteManagerState> by lazy { currentState.asStateFlow() }

    private val currentScreen: Screen
        get() = currentGraph.lastScreen

    private val previousScreen: Screen?
        get() {
            val currentGraphPrevious = currentGraph.previousScreen

            if (currentGraphPrevious != null) return currentGraphPrevious
            if (!canPop) return null

            return graphsBackstack
                .fastLastOrNull {
                    it.storeInBackstack &&
                        (it.lastScreen.storeInBackStack || it.previousScreen != null)
                }
                ?.let { if (it.lastScreen.storeInBackStack) it.lastScreen else it.previousScreen }
        }
    private val canPop: Boolean
        get() = currentGraph.canPop || graphsBackstack.size > 1

    override fun registerGraph(graph: NavigationGraph) {
        graphsRegistered[graph.id] = graph
        if (graphsBackstack.size == 0) {
            graphsBackstack.add(graph)
        }
    }

    override fun navigateTo(graphId: GraphId): Boolean {
        val graph = graphsRegistered[graphId] ?: return false
        if (!currentGraph.storeInBackstack) graphsBackstack.remove()
        graphsBackstack.add(graph)
        return true
    }

    override fun navigateTo(screen: Screen): Boolean {
        return currentGraph.navigateTo(screen)
    }

    override fun navigateTo(
        screenId: ScreenId,
        params: ScatterMap<String, String>?,
        extras: Extra?
    ): Boolean {
        val followingScreen = currentGraph.findScreen(screenId, params, extras) ?: return false
        currentState.value = currentState.value.copy(
            currentScreen = followingScreen,
            previousScreen = currentScreen,
            navigationState = RouteManagerState.Navigating.FORWARD
        )
        val navigated = currentGraph.navigateTo(followingScreen)

//        currentState.getAndUpdate { it.copy(navigationState = null) }
        return navigated
    }

    override fun popBackstack(): Boolean {
        val previousScreen = previousScreen
        if (!canPop || previousScreen == null) return false

        currentState.value = currentState.value.copy(
            currentScreen = previousScreen,
            previousScreen = null,
            navigationState = RouteManagerState.Navigating.BACK
        )

        val result = if (currentGraph.popBackstack() == null) popGraph() else true

//        if (result) {
//            currentState.value = currentState.value.copy(
//                previousScreen = previousScreen,
//                navigationState = null
//            )
//        }
        return result
    }

    override fun popUntil(screen: Screen): Boolean {
        if (!canPop) return false
        currentState.value = currentState.value.copy(
            navigationState = RouteManagerState.Navigating.BACK
        )

        return popUntil(screen.id)
    }

    override fun popUntil(screenId: ScreenId): Boolean {
        if (!canPop) return false

        graphsBackstack.removeUntil {
            it.findScreen(screenId, null, null) != null && it.storeInBackstack
        }

        return currentGraph.popUntil(screenId)
    }

    override fun popGraph(): Boolean {
        if (!canPop) return false

        return graphsBackstack.pop() != null
    }

    override fun handleDeepLink(deepLink: String): Boolean {
        val matchingScreenBuilder = graphsRegistered.asMap().values
            .asSequence()
            .flatMap { it.screenFactories.asMap().values }
            .filterIsInstance<DeepLinkScreenFactory>()
            .firstOrNull { it.matches(deepLink) }

        matchingScreenBuilder?.let {
            val parameters = it.extractParameters(deepLink)
            navigateTo(matchingScreenBuilder.id, parameters, null)
            return true
        }
        return false
    }

    override suspend fun gestureHandle(gesture: Gesture): Boolean {
        if (!canPop) return false
        val gestureStarting = when (val handler = currentScreen.screenGestureHandler) {
            is ScreenGestureHandler.Handling -> handler.padding
            else -> Float.MAX_VALUE
        }

        val backHandled = when (gesture) {
            is Gesture.End -> handleGestureEnd(gesture)
            is Gesture.Sliding -> handleSliding(gesture)
            is Gesture.Start -> gestureStarting >= gesture.positionX
        }

        return backHandled
    }

    private suspend fun handleGestureEnd(gesture: Gesture.End): Boolean {
        val state = currentState.value

        val offset = state.draggingOffset.value
        if (offset < gesture.screenWidth * SCREEN_WIDTH_PORTION_FOR_BACK_GESTURE) {
            state.draggingOffset.animateTo(-1f)
            return false
        }

        state.draggingOffset.animateTo(gesture.screenWidth)
        popBackstack()
        currentState.value = RouteManagerState(currentScreen, previousScreen)

        return true
    }

    private suspend fun handleSliding(gesture: Gesture.Sliding): Boolean {
        val state = currentState.value
        return if (state.previousScreen != null) {
            state.draggingOffset.snapTo(gesture.positionX)
            true
        } else {
            false
        }
    }
}

@Composable
fun rememberRouteManager(rootGraph: NavigationGraph): RouteManager {
    return remember { getRouteManager(rootGraph) }
}

fun getRouteManager(rootGraph: NavigationGraph): RouteManager {
    return BaseRouteManager().apply { registerGraph(rootGraph) }
}

fun getRouteManager(): RouteManager {
    return BaseRouteManager()
}

val LocalRouteManager = compositionLocalOf<RouteManager> {
    error("No RouteManager provided!")
}
