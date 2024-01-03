package wtf.speech.compass.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


/**
 * Interface defining the core functionalities of the navigation system.
 */
@Immutable
interface RouteManager {

    /**
     * The currently active [NavigationGraph].
     */
    val activeGraph: State<NavigationGraph>
    val events: Flow<NavigationEvent?>

    /**
     * The currently displayed screen.
     */
    val currentScreen: Screen

    /**
     * Registers a new [NavigationGraph] with the manager.
     *
     * @param graph The navigation graph to register.
     */
    fun registerGraph(graph: NavigationGraph)

    /**
     * Switches to a different navigation graph and navigates to its initial screen.
     *
     * @param graphId The ID of the graph to switch to.
     * @return True if the switch was successful, false otherwise.
     */
    fun switchToGraph(graphId: String): Boolean

    /**
     * Navigates to a specified screen.
     *
     * @param screenId The ID of the screen to navigate to.
     * @param params Optional parameters to pass to the screen.
     * @param extras Optional extras to pass to the screen.
     * @return True if the navigation was successful, false otherwise.
     */
    fun navigateTo(
        screenId: String,
        params: Map<String, String>? = null,
        extras: Extra? = null
    ): Boolean

    /**
     * Navigates back to the previous screen or closes the current graph if there's no previous screen.
     *
     * @return True if the back navigation was successful, false otherwise.
     */
    fun navigateBack(): Boolean

    fun gestureBack()

    /**
     * Closes the currently active navigation graph.
     *
     * @return True if the graph was closed successfully, false otherwise.
     */
    fun closeActiveGraph(): Boolean

    /**
     * Handles deep link navigation.
     *
     * @param deepLink The deep link URI.
     * @return True if the deep link was handled successfully, false otherwise.
     */
    fun handleDeepLink(deepLink: String): Boolean
}

/**
 * Implementation of [RouteManager].
 *
 * @param initialGraph The initial navigation graph to start with.
 */
class RouteManagerImpl(initialGraph: NavigationGraph) : RouteManager {
    private val graphs =
        mutableMapOf<String, NavigationGraph>().apply { put(initialGraph.id, initialGraph) }
    private val graphStack = mutableListOf<NavigationGraph>().apply { add(initialGraph) }

    override val events: MutableStateFlow<NavigationEvent?> = MutableStateFlow(null)
    override val activeGraph: MutableState<NavigationGraph> =
        mutableStateOf(graphs.getValue(initialGraph.id))
    override val currentScreen: Screen
        get() = activeGraph.value.currentScreen.value.screen

    override fun registerGraph(graph: NavigationGraph) {
        graphs[graph.id] = graph
    }

    override fun switchToGraph(graphId: String): Boolean {
        val graph = graphs[graphId] ?: return false
        events.tryEmit(NavigationEvent.Next(currentScreen, graph.currentScreen.value.screen))

        with(graphStack) {
            lastOrNull()?.let { if (!it.storeInBackStack) removeLastOrNull() }
            add(graph)
        }

        updateActiveGraph()
        return true
    }

    override fun navigateTo(
        screenId: String,
        params: Map<String, String>?,
        extras: Extra?
    ): Boolean {
        val nextScreen = activeGraph.value.findScreen(screenId, params, extras)
        if (nextScreen != null) {
            events.tryEmit(NavigationEvent.Next(currentScreen, nextScreen))
            return activeGraph.value.navigateTo(nextScreen, params, extras)
        }
        return false
    }

    override fun navigateBack(): Boolean {
        if (!events.tryEmit(
                NavigationEvent.Back(
                    currentScreen,
                    activeGraph.value.previousScreen.value?.screen
                )
            )
        ) return false
        val navigatedBack = activeGraph.value.navigateBack()

        if (!navigatedBack) return closeActiveGraph()

        return navigatedBack
    }

    override fun gestureBack() {
        TODO("Not yet implemented")
    }

    override fun closeActiveGraph(): Boolean {
        val currentStack = graphStack
        if (currentStack.size > 1) {
            currentStack.removeLast() // Remove current graph

            // Set the previous graph as the active graph
            activeGraph.value = currentStack.last()

            return true
        }
        return false
    }

    override fun handleDeepLink(deepLink: String): Boolean {
        val matchingScreenBuilder = graphs.values
            .asSequence()
            .flatMap { it.routes.values }
            .map { it }
            .filterIsInstance<DeepLinkScreenBuilder>()
            .firstOrNull { it.matches(deepLink) }

        matchingScreenBuilder?.let {
            val parameters = it.extractParameters(deepLink)
            navigateTo(matchingScreenBuilder.id, parameters, null)
            return true
        }
        return false
    }

    /**
     * Updates the currently active navigation graph.
     */
    private fun updateActiveGraph() {
        activeGraph.value = graphStack.last()
    }
}

@Composable
fun rememberRouteManager(initialGraph: NavigationGraph): RouteManager {
    return remember { getRouteManager(initialGraph) }
}

fun getRouteManager(initialGraph: NavigationGraph): RouteManager {
    return RouteManagerImpl(initialGraph)
}

val LocalRouteManager = compositionLocalOf<RouteManager> {
    error("No RouteManager provided!")
}
