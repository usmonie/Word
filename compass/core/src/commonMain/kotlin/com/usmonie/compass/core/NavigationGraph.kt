package com.usmonie.compass.core

import androidx.collection.MutableScatterMap
import androidx.collection.ScatterMap
import androidx.collection.emptyScatterMap
import androidx.collection.mutableScatterMapOf
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.core.utils.Stack
import kotlin.jvm.JvmInline

@JvmInline
value class GraphId(val id: String)

class NavigationGraph(
    val id: GraphId,
    val rootScreenBuilder: ScreenFactory,
    val extra: Extra? = null,
    val params: ScatterMap<String, String> = emptyScatterMap(),
    val storeInBackstack: Boolean = true
) {
    internal val screenFactories: MutableScatterMap<ScreenId, ScreenFactory> =
        mutableScatterMapOf(rootScreenBuilder.id to rootScreenBuilder)

    private val backstack: Stack<Screen> = Stack()

    val canPop: Boolean
        get() = backstack.size > 1

    val lastScreen: Screen
        get() {
            if (backstack.isEmpty()) {
                val rootScreen = rootScreenBuilder(params, extra)
                backstack.add(rootScreen)
            }

            return backstack.peek()
        }

    val previousScreen: Screen?
        get() {
            if (!canPop) return null

            return backstack[backstack.lastIndex - 1]
        }

    fun register(factory: ScreenFactory) {
        screenFactories[factory.id] = factory
    }

    fun findScreen(screenId: ScreenId, params: ScatterMap<String, String>?, extras: Extra?): Screen? {
        val factory = screenFactories[screenId] ?: return null
        return factory(params, extras)
    }

    fun navigateTo(screenId: ScreenId, params: ScatterMap<String, String>?, extras: Extra?): Boolean {
        val screen = findScreen(screenId, params, extras) ?: return false
        val currentScreen = lastScreen
        if (!currentScreen.storeInBackStack) backstack.remove()
        backstack.add(screen)

        return true
    }

    fun navigateTo(screen: Screen): Boolean {
        val currentScreen = lastScreen
        if (!currentScreen.storeInBackStack) backstack.remove()
        backstack.add(screen)
        return true
    }

    fun popBackstack(): Screen? {
        if (!canPop) return null
        return backstack.pop()?.apply { onCleared() }
    }

    fun popUntil(screenId: ScreenId): Boolean {
        backstack.removeUntil {
            val predicate = it.id == screenId && it.storeInBackStack
            if (!predicate) it.onCleared()
            predicate
        }

        return true
    }
}
