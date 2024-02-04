package com.usmonie.word.features.dashboard.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EtymologyTitle() {
    TitleUiComponent(
        "Etymology",
        Modifier.padding(horizontal = 24.dp),
        color = MaterialTheme.colorScheme.onSurface,
    )
}
