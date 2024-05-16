package com.usmonie.word

import androidx.compose.runtime.Composable
import com.usmonie.compass.core.RouteManager
import com.usmonie.compass.core.ui.Root
import com.usmonie.core.kit.design.themes.WordTheme
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.Friendly

@Composable
fun App(
    routeManager: RouteManager,
    appState: AppState = AppState(WordThemes.DEEP_INDIGO, Friendly)
) {
    WordTheme(appState.themes, appState.typography) {
        Root(
            routeManager,
            isGestureNavigationEnabled = true
        )
    }
}
