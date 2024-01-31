package wtf.speech.compass.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

internal data class NavigationEntry(
    val screen: Screen,
    val params: Map<String, String>?,
    val extras: Extra?
) {
    internal fun onClear() {
        screen.onCleared()
    }
}

/**
 * Represents a navigation graph with possible navigation paths, initial screen, and parameters.
 *
 * @property extra Extras for initial screen.
 * @property initialScreenBuilder The initial screen for this graph.
 * @property parameters Optional parameters associated with the graph.
 */
@Immutable
data class NavigationGraph(
    val id: String,
    val initialScreenBuilder: ScreenBuilder,
    val extra: Extra? = null,
    val parameters: Map<String, String> = emptyMap(),
    val storeInBackStack: Boolean = true
) {
    internal val routes = mutableMapOf<String, ScreenBuilder>()
    private val backStack by lazy {
        mutableListOf(
            NavigationEntry(
                initialScreenBuilder.build(parameters, extra),
                parameters,
                extra
            )
        )
    }

    internal val currentScreen: MutableState<NavigationEntry>
        get() = mutableStateOf(backStack.last())

    internal val previousScreen: MutableState<NavigationEntry?>
        get() = mutableStateOf(backStack.getOrNull(backStack.lastIndex - 1))

    fun register(route: Route) {
        routes[route.id] = route.screenBuilder
    }

    fun findScreen(screenId: String, params: Map<String, String>?, extras: Extra?): Screen? {
        val screenBuilder = routes[screenId]
        return screenBuilder?.build(params, extras)
    }

    fun navigateTo(screen: Screen, params: Map<String, String>?, extras: Extra?): Boolean {
        previousScreen.value = backStack.lastOrNull()
        backStack.add(NavigationEntry(screen, params, extras))
        updateCurrentScreen()
        return true
    }

    fun navigateTo(screenId: String, params: Map<String, String>?, extras: Extra?): Boolean {
        val screen = findScreen(screenId, params, extras)
        if (screen != null) {
            previousScreen.value = backStack.lastOrNull()
            backStack.add(NavigationEntry(screen, params, extras))
            updateCurrentScreen()
            return true
        }
        return false
    }

    fun navigateBack(): Boolean {
        if (backStack.size > 1) {
            backStack.removeLast().onClear()
            previousScreen.value = backStack.getOrNull(backStack.lastIndex - 1)
            updateCurrentScreen()
            return true
        }
        return false
    }

    private fun updateCurrentScreen() {
        currentScreen.value = backStack.last()
    }
}
