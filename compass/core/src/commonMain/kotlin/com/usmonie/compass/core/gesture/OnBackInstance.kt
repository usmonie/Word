package com.usmonie.compass.core.gesture

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

internal class OnBackInstance(
    scope: CoroutineScope,
    val isPredictiveBack: Boolean,
    onBack: suspend (progress: Flow<Gesture>) -> Unit,
) {
    val channel = Channel<Gesture>(capacity = BUFFERED, onBufferOverflow = BufferOverflow.SUSPEND)
    val job = scope.launch {
        var completed = false
        onBack(
            channel
                .consumeAsFlow()
                .onCompletion { completed = true }
        )
        check(completed) {
            "You must collect the progress flow"
        }
    }

    fun send(backEvent: Gesture) = channel.trySend(backEvent)

    // idempotent if invoked more than once
    fun close() = channel.close()

    fun cancel() {
        channel.cancel(CancellationException("onBack cancelled"))
        job.cancel()
    }
}
