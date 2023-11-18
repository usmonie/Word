package com.usmonie.word.features.dashboard.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.ui.MenuItem
import com.usmonie.word.features.ui.VerticalAnimatedVisibility

@Composable
fun About(
    onAboutMenuItemPressed: () -> Unit,
    query: String,
    showAbout: Boolean,
    localFocusManager: FocusManager
) {
    VerticalAnimatedVisibility(query.isBlank()) {
        Column {
            AboutMenuItem(
                onAboutMenuItemPressed,
                Modifier.fillMaxWidth().pointerInput(Unit) {
                    detectTapGestures(onTap = { localFocusManager.clearFocus() })
                }
            )
            AboutItems(showAbout)
        }
    }
}

@Composable
private fun AboutItems(
    showSettings: Boolean,
) {
    VerticalAnimatedVisibility(showSettings) {
        Column {

        }
    }
}

@Composable
fun AboutMenuItem(onClick: () -> Unit, modifier: Modifier = Modifier) {
    MenuItem(onClick, "About", modifier)
}

@Composable
fun AboutItem(onClick: () -> Unit, title: String, modifier: Modifier) {
    Surface(onClick, modifier = modifier, color = MaterialTheme.colorScheme.primary) {
        Text(
            title,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 36.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}