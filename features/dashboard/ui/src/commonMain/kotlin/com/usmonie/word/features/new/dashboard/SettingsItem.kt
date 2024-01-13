package com.usmonie.word.features.new.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.ui.MenuItem
import com.usmonie.word.features.ui.VerticalAnimatedVisibility

@Composable
fun Settings(
    onSettingsMenuItemPressed: () -> Unit,
    onPointerInput: () -> Unit,
    showItem: Boolean,
) {
    VerticalAnimatedVisibility(showItem) {
        Column {
            SettingsMenuItem(
                onSettingsMenuItemPressed,
                Modifier.fillMaxWidth().pointerInput(Unit) { onPointerInput() }
            )
        }
    }
}

@Composable
fun SettingsMenuItem(onClick: () -> Unit, modifier: Modifier = Modifier) {
    MenuItem(onClick, "Settings", modifier)
}

@Composable
fun SettingsItem(onClick: () -> Unit, title: String, modifier: Modifier) {
    Surface(onClick, modifier = modifier, color = MaterialTheme.colorScheme.primary) {
        Text(
            title,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 36.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}