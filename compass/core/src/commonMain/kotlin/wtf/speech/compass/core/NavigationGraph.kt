package wtf.speech.compass.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

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
    val initialRoute: Route,
    val extra: Extra? = null,
    val parameters: Map<String, String> = emptyMap(),
    val storeInBackStack: Boolean = true
) {
    internal val routes = mutableMapOf<String, ScreenBuilder>()

    private val initialEntry by lazy {
        NavigationEntry(
            initialRoute.screenBuilder.build(parameters, extra),
            parameters,
            extra
        )
    }

    private val backStack by lazy {
        mutableListOf<NavigationEntry>().apply {
            if (initialEntry.screen.storeInBackStack) {
                add(initialEntry)
            }
        }
    }

    private val currentNavigationEntryState: MutableStateFlow<NavigationEntry> =
        MutableStateFlow(if (backStack.isNotEmpty()) backStack.last() else initialEntry)

    internal val currentScreen: StateFlow<NavigationEntry> = currentNavigationEntryState

    internal val previousNavigationEntryState: MutableStateFlow<NavigationEntry?> =
        MutableStateFlow(backStack.getOrNull(backStack.lastIndex - 1))

    internal val previousScreen: StateFlow<NavigationEntry?> = previousNavigationEntryState


    fun register(route: Route) {
        routes[route.id] = route.screenBuilder
    }

    fun findScreen(screenId: String, params: Map<String, String>?, extras: Extra?): Screen? {
        val screenBuilder = routes[screenId]
        return screenBuilder?.build(params, extras)
    }

    fun navigateTo(screen: Screen, params: Map<String, String>?, extras: Extra?): Boolean {
        previousNavigationEntryState.value = backStack.lastOrNull()
        backStack.add(NavigationEntry(screen, params, extras))
        updateCurrentScreen()
        return true
    }

    fun navigateTo(screenId: String, params: Map<String, String>?, extras: Extra?): Boolean {
        val screen = findScreen(screenId, params, extras)
        if (screen != null) {
            previousNavigationEntryState.value = backStack.lastOrNull()
            backStack.add(NavigationEntry(screen, params, extras))
            updateCurrentScreen()
            return true
        }
        return false
    }

    fun navigateBack(): Boolean {
        if (backStack.size > 1) {
            backStack.removeLast().onClear()
            updateCurrentScreen()
            return true
        }
        return false
    }

    private fun updateCurrentScreen() {
        currentNavigationEntryState.value = backStack.last()
        previousNavigationEntryState.value = backStack.getOrNull(backStack.lastIndex - 1)
    }
}
