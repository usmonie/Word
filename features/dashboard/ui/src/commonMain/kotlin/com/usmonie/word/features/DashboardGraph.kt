package com.usmonie.word.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.ui.DashboardScreen
import com.usmonie.word.features.detail.WordScreen
import com.usmonie.word.features.favourites.FavouritesScreen
import wtf.speech.compass.core.NavigationGraph
import wtf.speech.compass.core.Route
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.WordTypography

@Composable
fun getDashboardGraph(
    onCurrentColorsChanged: (WordColors) -> Unit,
    onCurrentFontsChanged: (WordTypography) -> Unit,
    userRepository: UserRepository,
    wordRepository: WordRepository
): NavigationGraph {
    val dashboardScreen = remember {
        DashboardScreen.Builder(
            onCurrentColorsChanged,
            onCurrentFontsChanged,
            userRepository,
            wordRepository
        )
    }

    return NavigationGraph("DASHBOARD_GRAPH", dashboardScreen).apply {
        register(Route(FavouritesScreen.ID, FavouritesScreen.Builder(wordRepository)))
        register(Route(WordScreen.ID, WordScreen.Builder(wordRepository)))
    }
}