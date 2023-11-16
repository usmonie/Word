package wtf.speech.core.ui

import androidx.compose.runtime.Stable

/**
 * A class represents content state
 */
@Stable
sealed class ContentState<T>(val item: T?) {
    data class Success<T>(val data: T) : ContentState<T>(data)
    data class Error<T, E : ErrorState>(val error: E) : ContentState<T>(null)
    class Loading<T> : ContentState<T>(null)
}

/**
 * A base class for representing the states of a screen in MVI architecture
 */
@Stable
interface ScreenState

/**
 * A subclass of ScreenState that represents an error state
 */
@Stable
abstract class ErrorState(error: Throwable) {

    /**
     * A value to get the error message
     * @return the error message or "Unknown error" if the message is null
     */
    val message = error.message ?: "Unknown error"
}

/**
 * A base class for representing the events that occur on a screen in MVI architecture
 */
interface ScreenEvent

/**
 * A base class for representing the actions that are performed on a screen in MVI architecture
 */
interface ScreenAction

/**
 *  A base class for representing the effects that are triggered on a screen in MVI architecture
 */
interface ScreenEffect
