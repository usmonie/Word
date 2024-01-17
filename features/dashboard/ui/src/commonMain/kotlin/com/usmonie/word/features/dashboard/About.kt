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
import com.theapache64.rebugger.Rebugger
import com.usmonie.word.features.ui.MenuItem
import com.usmonie.word.features.ui.VerticalAnimatedVisibility

@Composable
fun AboutMenuItems(
    onAboutMenuItemPressed: () -> Unit,
    onAboutDeveloperPressed: () -> Unit,
    onTelegramPressed: () -> Unit,
    onDonatePressed: () -> Unit,
    showAbout: () -> Boolean,
) {
    Rebugger(
        trackMap = mapOf(
            "onAboutMenuItemPressed" to onAboutMenuItemPressed,
            "onAboutDeveloperPressed" to onAboutDeveloperPressed,
            "onTelegramPressed" to onTelegramPressed,
            "onDonatePressed" to onDonatePressed,
            "showAbout" to showAbout,
        ),
        composableName = "About"
    )
    Column {
        AboutMenuItems(
            onAboutMenuItemPressed,
            fillMaxWidthModifier
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
    showAbout: () -> Boolean,
) {
    VerticalAnimatedVisibility(showAbout()) {
        Column {
            if (false) AboutItem(
                onAboutDeveloperPressed,
                "About Me",
            )
            AboutItem(
                onTelegramPressed,
                "Telegram",
                fillMaxWidthModifier
            )
            if (false) AboutItem(
                onDonatePressed,
                "Donate",
            )
        }
    }
}

@Composable
fun AboutMenuItems(onClick: () -> Unit, modifier: Modifier = Modifier) {
    MenuItem(onClick, "About", modifier)
}

@Composable
fun AboutItem(onClick: () -> Unit, title: String, modifier: Modifier = Modifier) {
    Surface(onClick, modifier = modifier, color = Color.Transparent) {
        Text(
            title,
            modifier = aboutItemModifier,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

private val aboutItemModifier = Modifier.padding(vertical = 10.dp, horizontal = 36.dp)
private val fillMaxWidthModifier = Modifier.fillMaxWidth()