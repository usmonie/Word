package com.usmonie.compass.viewmodel

import androidx.compose.runtime.Immutable

/**
 * A class represents content state
 */
@Immutable
sealed class ContentState<T>(val item: T?) {
    data class Success<T>(val data: T) : ContentState<T>(data)
    data class Error<T, E : ErrorState>(val error: E) : ContentState<T>(null)
    class Loading<T> : ContentState<T>(null)
}

inline fun <T> ContentState<T>.updateData(
    onSuccess: (T) -> T
): ContentState<T> {
    return when (this) {
        is ContentState.Error<*, *> -> this
        is ContentState.Loading -> this
        is ContentState.Success -> ContentState.Success(onSuccess(data))
    }
}
