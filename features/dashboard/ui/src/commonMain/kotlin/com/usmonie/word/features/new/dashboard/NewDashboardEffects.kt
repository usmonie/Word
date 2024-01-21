package com.usmonie.word.features.new.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.usmonie.word.features.details.WordDetailsScreen
import com.usmonie.word.features.favorites.FavoritesScreen
import com.usmonie.word.features.settings.SettingsScreen
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.RouteManager

@Composable
internal fun NewDashboardEffects(dashboardViewModel: NewDashboardViewModel) {
    val routeManager = LocalRouteManager.current
    val effect by dashboardViewModel.effect.collectAsState(null)
    EffectHandler(effect, routeManager)
}

@Composable
private fun EffectHandler(
    effect: NewDashboardEffect?,
    routeManager: RouteManager
) {
    LaunchedEffect(effect) {
        when (effect) {
            is NewDashboardEffect.OpenWord -> routeManager.navigateTo(
                WordDetailsScreen.ID,
                extras = WordDetailsScreen.Companion.WordExtra(effect.word)
            )

            is NewDashboardEffect.OpenAbout -> Unit
            is NewDashboardEffect.OpenFavorites -> routeManager.navigateTo(FavoritesScreen.ID)
            is NewDashboardEffect.OpenGames -> Unit
            is NewDashboardEffect.OpenSettings -> routeManager.navigateTo(SettingsScreen.ID)
            null -> Unit
        }
    }
}