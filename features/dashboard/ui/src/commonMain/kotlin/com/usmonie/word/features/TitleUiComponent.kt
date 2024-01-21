package com.usmonie.word.features

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TitleUiComponent(
    title: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimary
) {
    Text(
        title,
        modifier = modifier,
        color = color,
        style = MaterialTheme.typography.titleLarge
    )
}