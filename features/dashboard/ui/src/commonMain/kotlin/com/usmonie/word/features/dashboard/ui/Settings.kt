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
fun Settings(
    onSettingsMenuItemPressed: () -> Unit,
    onChangeColorsPressed: () -> Unit,
    onChangeFontsPressed: () -> Unit,
    onClearRecentPressed: () -> Unit,
    query: String,
    showSettings: Boolean,
    localFocusManager: FocusManager
) {
    VerticalAnimatedVisibility(query.isBlank()) {
        Column {
            SettingsMenuItem(
                onSettingsMenuItemPressed,
                Modifier.fillMaxWidth().pointerInput(Unit) {
                    detectTapGestures(onTap = { localFocusManager.clearFocus() })
                }
            )
            SettingsItems(
                showSettings,
                onChangeColorsPressed,
                localFocusManager,
                onChangeFontsPressed,
                onClearRecentPressed
            )
        }
    }
}

@Composable
private fun SettingsItems(
    showSettings: Boolean,
    onChangeColorsPressed: () -> Unit,
    localFocusManager: FocusManager,
    onChangeFontsPressed: () -> Unit,
    onClearRecentPressed: () -> Unit
) {
    VerticalAnimatedVisibility(showSettings) {
        Column {
            ChangeThemeMenuItem(
                onChangeColorsPressed,
                Modifier.fillMaxWidth()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = { localFocusManager.clearFocus() })
                    }
            )
            ChangeFontMenuItem(
                onChangeFontsPressed,
                Modifier.fillMaxWidth().pointerInput(Unit) {
                    detectTapGestures(onTap = { localFocusManager.clearFocus() })
                }
            )
            ClearRecentHistoryMenuItem(
                onClearRecentPressed,
                Modifier.fillMaxWidth().pointerInput(Unit) {
                    detectTapGestures(onTap = { localFocusManager.clearFocus() })
                }
            )
        }
    }
}

@Composable
fun ChangeThemeMenuItem(onClick: () -> Unit, modifier: Modifier = Modifier) {
    SettingsItem(onClick, "Change theme", modifier)
}

@Composable
fun ChangeFontMenuItem(onClick: () -> Unit, modifier: Modifier = Modifier) {
    SettingsItem(onClick, "Change font", modifier)
}

@Composable
fun ClearRecentHistoryMenuItem(onClick: () -> Unit, modifier: Modifier = Modifier) {
    SettingsItem(onClick, "Clear recent history", modifier)
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