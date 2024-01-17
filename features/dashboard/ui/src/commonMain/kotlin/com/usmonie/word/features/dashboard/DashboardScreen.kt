package com.usmonie.word.features.dashboard

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.usmonie.word.features.OpenBrowser
import com.usmonie.word.features.Url
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.ClearRecentUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetSearchHistoryUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.GetWordOfTheDayUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.SearchWordsUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCaseImpl
import com.usmonie.word.features.details.WordDetailsScreen
import com.usmonie.word.features.favorites.FavoritesScreen
import com.usmonie.word.features.games.hangman.HangmanGameScreen
import com.usmonie.word.features.settings.SettingsScreen
import com.usmonie.word.features.ui.AdMob
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.LocalRouteManager
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.word.core.domain.Analytics

@Stable
class DashboardScreen(
    private val dashboardViewModel: DashboardViewModel,
    private val adMob: AdMob
) : Screen(dashboardViewModel) {

    override val id: String = ID

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        DashboardEffects(dashboardViewModel)
        DashboardContent(dashboardViewModel, scrollBehavior, adMob)
    }

    companion object {
        const val ID = "DASHBOARD_SCREEN"
    }

    class Builder(
        private val wordRepository: WordRepository,
        private val adMob: AdMob,
        private val analytics: Analytics
    ) : ScreenBuilder {
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?) = DashboardScreen(
            DashboardViewModel(
                SearchWordsUseCaseImpl(wordRepository),
                GetSearchHistoryUseCaseImpl(wordRepository),
                GetWordOfTheDayUseCaseImpl(wordRepository),
                UpdateFavouriteUseCaseImpl(wordRepository),
                RandomWordUseCaseImpl(wordRepository),
                ClearRecentUseCaseImpl(wordRepository),
                analytics
            ), adMob
        )
    }
}

@Composable
private fun OpenTelegram(effect: DashboardEffect.OpenUrl) {
    OpenBrowser(Url(effect.url))
}


@Composable
private fun DashboardEffects(viewModel: DashboardViewModel) {
    val effect by viewModel.effect.collectAsState(null)

    (effect as? DashboardEffect.OpenUrl)?.let { openUrl -> OpenTelegram(openUrl) }
    LaunchEffect(effect)
}

@Composable
private fun LaunchEffect(effect: DashboardEffect?) {
    val routeManager = LocalRouteManager.current

    LaunchedEffect(effect) {
        when (effect) {
            is DashboardEffect.OpenFavourites -> routeManager.navigateTo(FavoritesScreen.ID)
            is DashboardEffect.OpenWord -> routeManager.navigateTo(
                WordDetailsScreen.ID, extras = WordDetailsScreen.Companion.WordExtra(effect.word)
            )

            is DashboardEffect.OpenHangman -> routeManager.navigateTo(HangmanGameScreen.ID)
            is DashboardEffect.OpenSettings -> routeManager.navigateTo(SettingsScreen.ID)
            else -> Unit
        }
    }
}
