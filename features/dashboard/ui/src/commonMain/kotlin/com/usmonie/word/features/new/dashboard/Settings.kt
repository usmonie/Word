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
fun Settings(
    onSettingsMenuItemPressed: () -> Unit,
    onChangeColorsPressed: () -> Unit,
    onChangeFontsPressed: () -> Unit,
    onClearRecentPressed: () -> Unit,
    onPointerInput: suspend PointerInputScope.() -> Unit,
    query: String,
    showSettings: Boolean
) {
    VerticalAnimatedVisibility(query.isBlank()) {
        Column {
            SettingsMenuItem(
                onSettingsMenuItemPressed,
                Modifier.fillMaxWidth().pointerInput(Unit, onPointerInput)
            )
            SettingsItems(
                onChangeColorsPressed,
                onChangeFontsPressed,
                onClearRecentPressed,
                onPointerInput,
                showSettings,
            )
        }
    }
}

@Composable
private fun SettingsItems(
    onChangeColorsPressed: () -> Unit,
    onChangeFontsPressed: () -> Unit,
    onClearRecentPressed: () -> Unit,
    onPointerInput: suspend PointerInputScope.() -> Unit,
    showSettings: Boolean,
) {
    VerticalAnimatedVisibility(showSettings) {
        Column {
            ChangeThemeMenuItem(
                onChangeColorsPressed,
                Modifier.fillMaxWidth().pointerInput(Unit, onPointerInput)
            )
            ChangeFontMenuItem(
                onChangeFontsPressed,
                Modifier.fillMaxWidth().pointerInput(Unit, onPointerInput)
            )
            ClearRecentHistoryMenuItem(
                onClearRecentPressed,
                Modifier.fillMaxWidth().pointerInput(Unit, onPointerInput)
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