package wtf.speech.compass.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed class NavigationState(val targetScreen: Screen) {

    @Immutable
    data class Back(
        val previousScreen: Screen
    ) : NavigationState(previousScreen)

    @Immutable
    data class Next(
        val nextScreen: Screen
    ) : NavigationState(nextScreen)
}