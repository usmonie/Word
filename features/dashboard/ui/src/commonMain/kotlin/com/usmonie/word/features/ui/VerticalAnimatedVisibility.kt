package com.usmonie.word.features.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable

@Composable
fun VerticalAnimatedVisibility(
    showItem: Boolean,
    content: @Composable (AnimatedVisibilityScope.() -> Unit)
) {
    AnimatedVisibility(
        showItem,
        enter = expandVertically(),
        exit = shrinkVertically(),
        content = content
    )
}
