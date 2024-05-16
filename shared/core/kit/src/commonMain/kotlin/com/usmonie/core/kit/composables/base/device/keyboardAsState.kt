package com.usmonie.core.kit.composables.base.device

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.platform.LocalDensity

@Composable
fun keyboardAsState(): State<Boolean> {
    val bottom = WindowInsets.ime.getBottom(LocalDensity.current)
    return derivedStateOf { bottom > 0 }
}

@Composable
fun isKeyboardOpen(): Boolean {
    val bottom = WindowInsets.ime.getBottom(LocalDensity.current)
    return bottom > 0
}
