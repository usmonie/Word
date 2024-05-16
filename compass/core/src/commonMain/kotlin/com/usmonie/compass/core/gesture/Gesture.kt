package com.usmonie.compass.core.gesture

import androidx.compose.ui.input.pointer.PointerInputChange

sealed class Gesture {
    data class Start(
        val positionX: Float,
        val positionY: Float,
        val screenWidth: Float
    ) : Gesture()

    data class Sliding(
        val positionX: Float,
        val positionY: Float,
        val screenWidth: Float
    ) : Gesture()

    data class End(val screenWidth: Float) : Gesture()
}

sealed class ScreenGestureHandler {
    data object NoHandling : ScreenGestureHandler()

    data class Handling(val padding: Float) : ScreenGestureHandler()
}

fun PointerInputChange.toGesture(screenWidth: Int): Gesture {
    return Gesture.Sliding(
        positionX = position.x,
        positionY = position.y,
        screenWidth = screenWidth.toFloat()
    )
}
