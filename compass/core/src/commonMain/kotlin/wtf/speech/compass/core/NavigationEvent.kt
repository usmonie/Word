package wtf.speech.compass.core

import androidx.compose.runtime.Immutable

sealed class NavigationEvent {
    @Immutable
    data class BackGesture(
        val currentScreenOffsetX: Int,
        val previousScreenOffsetX: Int,
        val currentScreen: Screen,
        val previousScreen: Screen
    ) : NavigationEvent()

    @Immutable
    data class Back(
        val currentScreen: Screen,
        val previousScreen: Screen?
    ) : NavigationEvent()

    @Immutable
    data class Next(
        val currentScreen: Screen,
        val nextScreen: Screen
    ) : NavigationEvent()
}