package wtf.speech.compass.core

import androidx.compose.runtime.Immutable

sealed class NavigationState(val targetScreen: Screen) {

    @Immutable
    data class Back(
        val currentScreen: Screen,
        val previousScreen: Screen
    ) : NavigationState(previousScreen)

    @Immutable
    data class Next(
        val currentScreen: Screen,
        val nextScreen: Screen
    ) : NavigationState(nextScreen)

    @Immutable
    data class CurrentScreen(val currentScreen: Screen) : NavigationState(currentScreen)
}

@Immutable
sealed class BackGesture(
    open val currentScreen: Screen,
    open val previousScreen: Screen
) : NavigationState(previousScreen) {
    data class Dragging(
        override val currentScreen: Screen,
        override val previousScreen: Screen,
        val screenWidth: Int
    ) : BackGesture(currentScreen, previousScreen)

    data class Ended(
        override val currentScreen: Screen,
        override val previousScreen: Screen,
        val screenWidth: Int
    ) : BackGesture(currentScreen, previousScreen)
}