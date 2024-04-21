package wtf.speech.compass.core

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


interface RouteManager {
    val currentGraph: StateFlow<NavigationGraph>

    val currentScreen: StateFlow<Screen>
    val previousScreen: StateFlow<Screen?>

    val currentState: NavigationState?
    val draggingOffset: Animatable<Float, AnimationVector1D>


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

    suspend fun gestureBackOffset(offset: Float, screenWidth: Int)
    suspend fun gestureBackEnded(offset: Float, screenWidth: Int)

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

@OptIn(ExperimentalCoroutinesApi::class)
class RouteManagerImpl(initialGraph: NavigationGraph) : RouteManager {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val graphs =
        mutableMapOf<String, NavigationGraph>().apply { put(initialGraph.id, initialGraph) }
    private val graphStack = mutableListOf<NavigationGraph>().apply { add(initialGraph) }

    private val currentGraphState: MutableStateFlow<NavigationGraph> =
        MutableStateFlow(graphs.getValue(initialGraph.id))

    override val currentGraph: StateFlow<NavigationGraph> = currentGraphState

    override val draggingOffset: Animatable<Float, AnimationVector1D> = Animatable(0f)

    private val _currentScreen = MutableStateFlow(currentGraph.value.currentScreen.value.screen)
    private val _previousScreen = MutableStateFlow(
        currentGraph.value.previousScreen.value?.screen
            ?: graphStack.getOrNull(graphStack.lastIndex - 1)?.previousScreen?.value?.screen
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentScreen: StateFlow<Screen> = _currentScreen
    override val previousScreen: StateFlow<Screen?> = _previousScreen

    private var _currentState: NavigationState? = null
    override val currentState: NavigationState?
        get() = _currentState

    init {
        coroutineScope.launch {
            currentGraph
                .flatMapConcat { it.currentScreen.map { it.screen } }
                .collect { _currentScreen.value = it }
        }

        coroutineScope.launch {
            currentGraph.flatMapConcat { it.previousScreen.map { it?.screen } }.collect {
                _previousScreen.value = it ?: graphStack.getOrNull(graphStack.lastIndex - 1)?.previousScreen?.value?.screen
            }
        }
    }

    override fun registerGraph(graph: NavigationGraph) {
        graphs[graph.id] = graph
    }

    override fun switchToGraph(graphId: String): Boolean {
        val graph = graphs[graphId] ?: return false

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
        val nextScreen = currentGraph.value.findScreen(screenId, params, extras)
        if (nextScreen != null) {
            _currentState = NavigationState.Next(nextScreen)
            val navigated = currentGraph.value.navigateTo(nextScreen, params, extras)
            return navigated
        }

        updateActiveGraph()

        return false
    }

    override fun navigateBack(): Boolean {
        val previousScreen = previousScreen.value
        if (previousScreen != null) _currentState = NavigationState.Back(previousScreen)

        if (currentGraph.value.navigateBack()) return true

        return closeActiveGraph()
    }

    override suspend fun gestureBackOffset(offset: Float, screenWidth: Int) {
        if (previousScreen.value != null) {
            _currentState = null
            draggingOffset.snapTo(offset)
        }
    }

    override suspend fun gestureBackEnded(offset: Float, screenWidth: Int) {
        _currentState = null
        val isDragCanceled = offset < screenWidth * .20
        if (isDragCanceled) {
            draggingOffset.animateTo(0f)
        } else {
            val result = draggingOffset.animateTo(screenWidth.toFloat())

            if (result.endState.value == screenWidth.toFloat()) {
                if (!currentGraph.value.navigateBack()) {
                    closeActiveGraph()
                }
                draggingOffset.snapTo(0f)
            }
        }

        updateActiveGraph()
    }

    override fun closeActiveGraph(): Boolean {
        val currentStack = graphStack
        if (currentStack.size > 1) {
            currentStack.removeLast() // Remove current graph

            // Set the previous graph as the active graph
            currentGraphState.value = currentStack.last()

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
        currentGraphState.value = graphStack.last()
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
