package wtf.speech.compass.core

sealed class NavigationEvent {
    data class BackGesture(
        val currentScreenOffsetX: Int,
        val previousScreenOffsetX: Int,
        val currentScreen: Screen,
        val previousScreen: Screen
    ) : NavigationEvent()

    data class Back(
        val currentScreen: Screen,
        val previousScreen: Screen?
    ) : NavigationEvent()

    data class Next(
        val currentScreen: Screen,
        val nextScreen: Screen
    ) : NavigationEvent()
}