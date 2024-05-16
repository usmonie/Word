package com.usmonie.compass.viewmodel

import androidx.compose.runtime.Stable

/**
 * A base class for representing the states of a screen in MVI architecture
 */
@Stable
interface ScreenState

/**
 * A subclass of ScreenState that represents an error state
 */
@Stable
abstract class ErrorState(error: Throwable) : ScreenState {

    /**
     * A value to get the error message
     * @return the error message or "Unknown error" if the message is null
     */
    val message = error.message ?: "Unknown error"
}

@Stable
data class ConnectionErrorState(val error: Throwable) : ErrorState(error)

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
