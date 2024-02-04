package com.usmonie.word.features.dashboard.ui.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MenuItem(onClick: () -> Unit, title: String, modifier: Modifier) {
    Surface(onClick, modifier = modifier, color = Color.Transparent) {
        MenuItemText(title)
    }
}

@Composable
fun MenuItemText(title: String, modifier: Modifier = Modifier) {
    Text(
        title,
        modifier = modifier.padding(vertical = 10.dp, horizontal = 24.dp),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
fun SubtitleItemText(title: String, modifier: Modifier = Modifier) {
    Text(
        title,
        modifier = modifier.padding(vertical = 10.dp, horizontal = 20.dp),
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.titleMedium
    )
}