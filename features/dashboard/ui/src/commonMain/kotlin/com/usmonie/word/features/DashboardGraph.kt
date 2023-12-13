package com.usmonie.word.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.new.dashboard.DashboardScreen
import com.usmonie.word.features.new.details.WordDetailsScreen
import com.usmonie.word.features.new.favorites.FavoritesScreen
import com.usmonie.word.features.new.games.hangman.HangmanGameScreen
import com.usmonie.word.features.ui.AdMob
import wtf.speech.compass.core.NavigationGraph
import wtf.speech.compass.core.Route
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.WordTypography
import wtf.word.core.domain.Analytics

@Composable
fun getDashboardGraph(
    onCurrentColorsChanged: (WordColors) -> Unit,
    onCurrentFontsChanged: (WordTypography) -> Unit,
    userRepository: UserRepository,
    wordRepository: WordRepository,
    adMob: AdMob,
    analytics: Analytics
): NavigationGraph {
    val dashboardScreen = remember {
        DashboardScreen.Builder(
            onCurrentColorsChanged,
            onCurrentFontsChanged,
            userRepository,
            wordRepository,
            adMob,
            analytics
        )
    }

    return NavigationGraph("DASHBOARD_GRAPH", dashboardScreen).apply {
        register(Route(FavoritesScreen.ID, FavoritesScreen.Builder(wordRepository, adMob, analytics)))
        register(Route(WordDetailsScreen.ID, WordDetailsScreen.Builder(wordRepository, analytics, adMob)))
        register(Route(HangmanGameScreen.ID, HangmanGameScreen.Builder(wordRepository, adMob)))
    }
}
