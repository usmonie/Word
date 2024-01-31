package com.usmonie.word.features

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode

@Composable
expect fun OpenBrowser(url: Url)

class Url(val url: String)

@Composable
fun Modifier.gradientBackground(
    colors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.primaryContainer
    )
): Modifier {
    return this then background(
        Brush.verticalGradient(colors, tileMode = TileMode.Decal)
    )
}