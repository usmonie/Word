package com.usmonie.compass.core

import androidx.collection.MutableScatterMap
import androidx.collection.ScatterMap
import androidx.collection.emptyScatterMap
import androidx.collection.mutableScatterMapOf
import com.usmonie.compass.core.ui.Screen
import com.usmonie.compass.core.ui.ScreenFactory
import com.usmonie.compass.core.ui.ScreenId
import com.usmonie.compass.core.utils.Stack

data class GraphId(val id: String)

open class NavigationGraphFactory(
	val id: GraphId,
	private val rootScreenFactory: ScreenFactory,
	private val builder: NavigationGraph.() -> Unit
) {
	open operator fun invoke(
		params: ScatterMap<String, String> = emptyScatterMap(),
		extra: Extra? = null,
		storeInBackstack: Boolean
	): NavigationGraph {
		return NavigationGraph(id, rootScreenFactory, extra, params, storeInBackstack).apply(builder)
	}
}

data class NavigationGraph(
	val id: GraphId,
	val rootScreenFactory: ScreenFactory,
	val extra: Extra? = null,
	val params: ScatterMap<String, String> = emptyScatterMap(),
	val storeInBackstack: Boolean = true
) {
	private val backstack: Stack<Screen> = Stack()

	val canPop: Boolean
		get() = backstack.size > 1

	val lastScreen: Screen
		get() {
			if (backstack.isEmpty()) {
				val rootScreen = rootScreenFactory(true, params, extra)
				backstack.add(rootScreen)
			}

			return backstack.peek()
		}

	val previousScreen: Screen?
		get() {
			if (!canPop) return null

			return backstack[backstack.lastIndex - 1]
		}

	private val screenFactories: MutableScatterMap<ScreenId, ScreenFactory> =
		mutableScatterMapOf(rootScreenFactory.id to rootScreenFactory)

	fun register(factory: ScreenFactory) {
		screenFactories[factory.id] = factory
	}

	fun containsScreenInBackStack(screenId: ScreenId): Boolean {
		return backstack.find { it.id == screenId && it.storeInBackStack } != null
	}

	fun findScreen(
		screenId: ScreenId,
		storeInBackstack: Boolean,
		params: ScatterMap<String, String>?,
		extras: Extra?
	): Screen? {
		val factory = screenFactories[screenId] ?: return null
		return factory(storeInBackstack, params, extras)
	}

	fun navigateTo(
		screenId: ScreenId,
		storeInBackstack: Boolean,
		params: ScatterMap<String, String>?,
		extras: Extra?
	): Boolean {
		val screen = findScreen(screenId, storeInBackstack, params, extras) ?: return false
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

	fun popUntil(screenId: ScreenId, onPopped: (Screen) -> Unit): Boolean {
		backstack.removeUntil {
			val predicate = it.id == screenId && it.storeInBackStack
			if (!predicate) {
				it.onCleared()
				onPopped(it)
			}
			predicate
		}

		return true
	}
}
