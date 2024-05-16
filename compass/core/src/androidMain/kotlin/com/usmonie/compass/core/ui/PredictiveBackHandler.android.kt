package com.usmonie.compass.core.ui

import androidx.activity.BackEventCompat
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.usmonie.compass.core.gesture.Gesture
import com.usmonie.compass.core.gesture.OnBackInstance
import kotlinx.coroutines.flow.Flow

/**
 * An effect for handling predictive system back gestures.
 *
 * Calling this in your composable adds the given lambda to the [OnBackPressedDispatcher] of the
 * [LocalOnBackPressedDispatcherOwner]. The lambda passes in a Flow<Gesture> where each
 * [Gesture] reflects the progress of current gesture back. The lambda content should
 * follow this structure:
 *
 * ```
 * PredictiveBackHandler { progress: Flow<BackEventCompat> ->
 *      // code for gesture back started
 *      try {
 *         progress.collect { backevent ->
 *              // code for progress
 *         }
 *         // code for completion
 *      } catch (e: CancellationException) {
 *         // code for cancellation
 *      }
 * }
 * ```
 *
 * If this is called by nested composables, if enabled, the inner most composable will consume
 * the call to system back and invoke its lambda. The call will continue to propagate up until it
 * finds an enabled BackHandler.
 *
 * @sample androidx.activity.compose.samples.PredictiveBack
 *
 * @param enabled if this BackHandler should be enabled, true by default
 * @param onBack the action invoked by back gesture
 */
@Composable
actual fun PredictiveBackHandler(
    enabled: Boolean,
    onBack: suspend (progress: Flow<Gesture>) -> Unit,
    onBackPressed: () -> Unit,
    getDraggingOffset: () -> Float,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(content = content)
    // ensure we don't re-register callbacks when onBack changes
    val currentOnBack by rememberUpdatedState(onBack)
    val onBackScope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current

    val screenWidth = with(LocalDensity.current) { configuration.screenWidthDp.dp.toPx() }

    val backCallBack = remember {
        object : OnBackPressedCallback(enabled) {
            var onBackInstance: OnBackInstance? = null

            override fun handleOnBackStarted(backEvent: BackEventCompat) {
                super.handleOnBackStarted(backEvent)
                // in case the previous onBackInstance was started by a normal back gesture
                // we want to make sure it's still cancelled before we start a predictive
                // back gesture
                onBackInstance?.cancel()

                onBackInstance = OnBackInstance(
                    onBackScope,
                    backEvent.touchX != 0f ||
                        backEvent.touchY != 0f ||
                        backEvent.progress != 0f,
                    currentOnBack
                )
            }

            override fun handleOnBackProgressed(backEvent: BackEventCompat) {
                super.handleOnBackProgressed(backEvent)
                onBackInstance?.send(backEvent.toGestureEvent())
            }

            override fun handleOnBackPressed() {
                // handleOnBackPressed could be called by regular back to restart
                // a new back instance. If this is the case (where current back instance
                // was NOT started by handleOnBackStarted) then we need to reset the previous
                // regular back.
                onBackInstance?.apply {
                    if (!isPredictiveBack) {
                        cancel()
                        onBackInstance = null
                    } else {
                        send(Gesture.End(screenWidth))
                    }
                }
                if (onBackInstance == null) {
                    onBackInstance = OnBackInstance(onBackScope, false, currentOnBack)
                    onBackPressed()
                }

                // finally, we close the channel to ensure no more events can be sent
                // but let the job complete normally
                onBackInstance?.close()
            }

            override fun handleOnBackCancelled() {
                super.handleOnBackCancelled()
                // cancel will purge the channel of any sent events that are yet to be received
                onBackInstance?.send(Gesture.End(screenWidth))
            }
        }
    }

    LaunchedEffect(enabled) {
        backCallBack.isEnabled = enabled
    }

    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, backDispatcher) {
        backDispatcher.addCallback(lifecycleOwner, backCallBack)

        onDispose { backCallBack.remove() }
    }
}

fun BackEventCompat.toGestureEvent() = Gesture.Sliding(
    touchX,
    touchY,
    progress,
)
