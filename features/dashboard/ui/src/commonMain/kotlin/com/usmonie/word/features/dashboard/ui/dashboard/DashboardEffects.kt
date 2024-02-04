package com.usmonie.word.features.dashboard.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.usmonie.word.features.dashboard.ui.details.WordDetailsScreen
import com.usmonie.word.features.dashboard.ui.favorites.FavoritesScreen
import com.usmonie.word.features.dashboard.ui.games.GamesScreen
import com.usmonie.word.features.dashboard.ui.settings.SettingsScreen
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.RouteManager

@Composable
internal fun DashboardEffects(dashboardViewModel: DashboardViewModel) {
    val routeManager = LocalRouteManager.current
    val effect by dashboardViewModel.effect.collectAsState(null)
    EffectHandler(effect, routeManager)
}

@Composable
private fun EffectHandler(
    effect: DashboardEffect?,
    routeManager: RouteManager
) {
    LaunchedEffect(effect) {
        when (effect) {
            is DashboardEffect.OpenWord -> routeManager.navigateTo(
                WordDetailsScreen.ID,
                extras = WordDetailsScreen.Companion.WordExtra(effect.word)
            )

            is DashboardEffect.OpenAbout -> Unit
            is DashboardEffect.OpenFavorites -> routeManager.navigateTo(FavoritesScreen.ID)
            is DashboardEffect.OpenGames -> routeManager.navigateTo(GamesScreen.ID)
            is DashboardEffect.OpenSettings -> routeManager.navigateTo(SettingsScreen.ID)
            null -> Unit
        }
    }
}