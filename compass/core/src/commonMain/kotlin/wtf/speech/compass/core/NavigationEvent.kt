package wtf.speech.compass.core

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State

@Stable
data class NavigationState(
    val activeGraph: State<NavigationGraph>,
    val currentScreen: Screen,
    val previousScreen: Screen?,
    val offset: Float,
    val currentEvent: State<NavigationEvent?>
)

@Stable
sealed class NavigationEvent(val targetScreen: Screen) {

    @Immutable
    data class Back(
        val previousScreen: Screen
    ) : NavigationEvent(previousScreen)

    @Immutable
    data class Next(
        val nextScreen: Screen
    ) : NavigationEvent(nextScreen)

    @Immutable
    sealed class BackGesture(
        previousScreen: Screen
    ) : NavigationEvent(previousScreen) {
        @Stable
        data class Dragging(
            val previousScreen: Screen,
            val currentScreen: Screen,
            val offset: Float,
            val screenWidth: Int
        ) : BackGesture(previousScreen)

        @Immutable
        sealed class Ended(
            previousScreen: Screen,
        ) : BackGesture(previousScreen) {
            data class Success(
                val previousScreen: Screen,
                val currentScreen: Screen,
                val screenWidth: Int
            ) : Ended(previousScreen)

            data class Cancel(val previousScreen: Screen) : Ended(previousScreen)
        }
    }
}