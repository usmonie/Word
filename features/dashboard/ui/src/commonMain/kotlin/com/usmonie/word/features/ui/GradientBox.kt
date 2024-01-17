package com.usmonie.word.features.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.usmonie.word.features.gradientBackground

@Composable
fun GradientBox(
    modifier: Modifier = Modifier,
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer
    ),
    insets: PaddingValues = PaddingValues(),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier.gradientBackground(colors).padding(insets),
        content = content
    )
}