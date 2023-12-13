package com.usmonie.word.features.new.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.ui.MenuItem
import com.usmonie.word.features.ui.VerticalAnimatedVisibility

@Composable
fun About(
    onAboutMenuItemPressed: () -> Unit,
    onAboutDeveloperPressed: () -> Unit,
    onTelegramPressed: () -> Unit,
    onDonatePressed: () -> Unit,
    onPointerInput: suspend PointerInputScope.() -> Unit,
    query: String,
    showAbout: Boolean,
) {
    VerticalAnimatedVisibility(query.isBlank()) {
        Column {
            AboutMenuItem(
                onAboutMenuItemPressed,
                Modifier.fillMaxWidth().pointerInput(Unit, onPointerInput)
            )
            AboutItems(onAboutDeveloperPressed, onTelegramPressed, onDonatePressed, onPointerInput, showAbout)
        }
    }
}

@Composable
private fun AboutItems(
    onAboutDeveloperPressed: () -> Unit,
    onTelegramPressed: () -> Unit,
    onDonatePressed: () -> Unit,
    onPointerInput: suspend PointerInputScope.() -> Unit,
    showSettings: Boolean,
) {
    VerticalAnimatedVisibility(showSettings) {
        Column {
            if (false) AboutItem(onAboutDeveloperPressed, "About Me", Modifier.pointerInput(Unit, onPointerInput))
            AboutItem(onTelegramPressed, "Telegram", Modifier.pointerInput(Unit, onPointerInput))
            if (false) AboutItem(onDonatePressed, "Donate", Modifier.pointerInput(Unit, onPointerInput))
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