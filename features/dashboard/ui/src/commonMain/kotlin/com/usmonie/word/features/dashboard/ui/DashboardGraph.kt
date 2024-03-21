package com.usmonie.word.features.dashboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.ui.dashboard.DashboardScreen
import com.usmonie.word.features.dashboard.ui.details.WordDetailsScreen
import com.usmonie.word.features.dashboard.ui.favorites.FavoritesScreen
import com.usmonie.word.features.dashboard.ui.games.GamesScreen
import com.usmonie.word.features.dashboard.ui.games.hangman.HangmanGameScreen
import com.usmonie.word.features.dashboard.ui.settings.SettingsScreen
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import com.usmonie.word.features.subscription.domain.repository.SubscriptionRepository
import wtf.speech.compass.core.NavigationGraph
import wtf.speech.compass.core.Route
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.WordTypography
import wtf.word.core.domain.Analytics

@Composable
fun rememberDashboardGraph(
    onCurrentColorsChanged: (WordColors) -> Unit,
    onCurrentFontsChanged: (WordTypography) -> Unit,
    subscriptionRepository: SubscriptionRepository,
    userRepository: UserRepository,
    wordRepository: WordRepository,
    adMob: AdMob,
    analytics: Analytics
): NavigationGraph {
    val dashboardScreen = remember {
        DashboardScreen.Builder(
            wordRepository,
            adMob,
            analytics
        )
    }

    return remember {
        NavigationGraph("DASHBOARD_GRAPH", Route(DashboardScreen.ID, dashboardScreen)).apply {
            register(
                Route(
                    FavoritesScreen.ID,
                    FavoritesScreen.Builder(wordRepository, adMob, analytics)
                )
            )
            register(
                Route(
                    WordDetailsScreen.ID,
                    WordDetailsScreen.Builder(wordRepository, analytics, adMob)
                )
            )
            register(Route(HangmanGameScreen.ID, HangmanGameScreen.Builder(wordRepository, adMob)))
            register(
                Route(
                    SettingsScreen.ID,
                    SettingsScreen.Builder(
                        onCurrentColorsChanged,
                        onCurrentFontsChanged,
                        subscriptionRepository,
                        userRepository,
                        wordRepository,
                        adMob
                    )
                )
            )
        }
    }
}

fun getDashboardGraph(
    onCurrentColorsChanged: (WordColors) -> Unit,
    onCurrentFontsChanged: (WordTypography) -> Unit,
    subscriptionRepository: SubscriptionRepository,
    userRepository: UserRepository,
    wordRepository: WordRepository,
    adMob: AdMob,
    analytics: Analytics
): NavigationGraph {
    val dashboardScreen = DashboardScreen.Builder(
        wordRepository,
        adMob,
        analytics
    )

    return NavigationGraph(DASHBOARD_GRAPH_ID, Route(DashboardScreen.ID, dashboardScreen)).apply {
        register(
            Route(
                FavoritesScreen.ID,
                FavoritesScreen.Builder(wordRepository, adMob, analytics)
            )
        )
        register(
            Route(
                WordDetailsScreen.ID,
                WordDetailsScreen.Builder(wordRepository, analytics, adMob)
            )
        )
        register(
            Route(
                GamesScreen.ID,
                GamesScreen
            )
        )
        register(Route(HangmanGameScreen.ID, HangmanGameScreen.Builder(wordRepository, adMob)))
        register(
            Route(
                SettingsScreen.ID,
                SettingsScreen.Builder(
                    onCurrentColorsChanged,
                    onCurrentFontsChanged,
                    subscriptionRepository,
                    userRepository,
                    wordRepository,
                    adMob
                )
            )
        )
    }
}

const val DASHBOARD_GRAPH_ID = "DASHBOARD_GRAPH"