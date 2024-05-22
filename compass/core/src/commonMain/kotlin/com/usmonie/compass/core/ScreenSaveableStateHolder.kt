package com.usmonie.compass.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import com.usmonie.compass.core.ui.Screen

/**
 * Allows to save the state defined with [rememberSaveable] for the subtree before disposing it
 * to make it possible to compose it back next time with the restored state. It allows different
 * navigation patterns to keep the ui state like scroll position for the currently not composed
 * screens from the backstack.
 *
 * @sample androidx.compose.runtime.saveable.samples.SimpleNavigationWithSaveableStateSample
 *
 * The content should be composed using [SaveableStateProvider] while providing a key representing
 * this content. Next time [SaveableStateProvider] will be used with the same key its state will be
 * restored.
 */
interface ScreenSaveableStateHolder {
    /**
     * Put your content associated with a [screen] inside the [content]. This will automatically
     * save all the states defined with [rememberSaveable] before disposing the content and will
     * restore the states when you compose with this screen again.
     *
     * @param screen to be used for saving and restoring the states for the subtree. Note that on
     * Android you can only use types which can be stored inside the Bundle.
     */
    @Composable
    fun SaveableStateProvider(screen: Screen, content: @Composable () -> Unit)

    /**
     * Removes the saved state associated with the passed [key].
     */
    fun removeState(key: String)
}
