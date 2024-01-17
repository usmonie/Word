package com.usmonie.word.features.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.usmonie.word.features.ui.MenuItem
import com.usmonie.word.features.ui.VerticalAnimatedVisibility

@Composable
fun About(
    onAboutMenuItemPressed: () -> Unit,
    onAboutDeveloperPressed: () -> Unit,
    onTelegramPressed: () -> Unit,
    onDonatePressed: () -> Unit,
    showAbout: Boolean,
) {
    Column {
        AboutMenuItem(
            onAboutMenuItemPressed,
            Modifier.fillMaxWidth()
        )
        AboutItems(
            onAboutDeveloperPressed,
            onTelegramPressed,
            onDonatePressed,
            showAbout
        )
    }
}

@Composable
private fun AboutItems(
    onAboutDeveloperPressed: () -> Unit,
    onTelegramPressed: () -> Unit,
    onDonatePressed: () -> Unit,
    showSettings: Boolean,
) {
    VerticalAnimatedVisibility(showSettings) {
        Column {
            if (false) AboutItem(
                onAboutDeveloperPressed,
                "About Me",
                )
            AboutItem(
                onTelegramPressed,
                "Telegram",
                Modifier.fillMaxWidth()
            )
            if (false) AboutItem(
                onDonatePressed,
                "Donate",
            )
        }
    }
}

@Composable
fun AboutMenuItem(onClick: () -> Unit, modifier: Modifier = Modifier) {
    MenuItem(onClick, "About", modifier)
}

@Composable
fun AboutItem(onClick: () -> Unit, title: String, modifier: Modifier = Modifier) {
    Surface(onClick, modifier = modifier, color = Color.Transparent) {
        Text(
            title,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 36.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )
    }
}