package com.usmonie.word.di

import com.usmonie.compass.core.GraphId
import com.usmonie.compass.core.NavigationGraph
import com.usmonie.compass.core.RouteManager
import com.usmonie.compass.core.getRouteManager
import com.usmonie.core.domain.AppConfig
import com.usmonie.word.features.dashboard.ui.di.dashboardUiModule
import com.usmonie.word.features.dashboard.ui.screen.DashboardMenuItem
import com.usmonie.word.features.dashboard.ui.screen.DashboardMenuItem.FAVORITES
import com.usmonie.word.features.dashboard.ui.screen.DashboardMenuItem.GAMES
import com.usmonie.word.features.dashboard.ui.screen.DashboardMenuItem.SETTINGS
import com.usmonie.word.features.dashboard.ui.screen.DashboardScreenFactory
import com.usmonie.word.features.details.ui.di.wordDetailsUiModule
import com.usmonie.word.features.details.ui.pos.PosDetailsScreenFactory
import com.usmonie.word.features.details.ui.word.WordDetailsScreenFactory
import com.usmonie.word.features.games.data.di.dictionaryDataModule
import com.usmonie.word.features.games.domain.di.dictionaryDomainUseCase
import com.usmonie.word.features.games.ui.models.WordCombinedUi
import com.usmonie.word.features.favorites.ui.FavoritesScreenFactory
import com.usmonie.word.features.favorites.ui.di.favoritesUiModule
import com.usmonie.word.features.games.ui.GamesScreenFactory
import com.usmonie.word.features.games.ui.di.gamesUiModule
import com.usmonie.word.features.games.ui.enigma.EnigmaGameScreenFactory
import com.usmonie.word.features.games.ui.hangman.HangmanGameScreenFactory
import com.usmonie.word.features.quotes.data.di.quotesDataModule
import com.usmonie.word.features.qutoes.domain.di.quotesDomainModule
import com.usmonie.word.features.settings.data.di.settingsDataModule
import com.usmonie.word.features.settings.ui.SettingsScreenFactory
import com.usmonie.word.features.settings.ui.di.settingsUiModule
import com.usmonie.word.features.subscription.data.di.subscriptionDataModule
import com.usmonie.word.features.subscriptions.ui.subscriptionsUiModule
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val mainGraphId = GraphId("MainGraph")

val appModule = module {
    includes(
        dashboardUiModule,
        dictionaryDataModule,
        dictionaryDomainUseCase,
        favoritesUiModule,
        gamesUiModule,
        subscriptionDataModule,
        subscriptionsUiModule,
        settingsDataModule,
        settingsUiModule,
        wordDetailsUiModule,
        quotesDataModule,
        quotesDomainModule
    )

    single { if (isRelease) AppConfig.Release else AppConfig.Debug }

    single { (routeManager: RouteManager) ->
        val openWord: (WordCombinedUi) -> Unit = {
            routeManager.navigateTo(
                WordDetailsScreenFactory.ID,
                extras = WordDetailsScreenFactory.WordDetailsExtra(it)
            )
        }

        openWord
    }

    factory {
        val routeManager = getRouteManager()

        val openMenuItems: (DashboardMenuItem) -> Unit = {
            routeManager.navigateTo(
                when (it) {
                    FAVORITES -> FavoritesScreenFactory.ID
                    SETTINGS -> SettingsScreenFactory.ID
                    GAMES -> GamesScreenFactory.ID
                }
            )
        }

        val dashboardFactory: DashboardScreenFactory =
            get { parametersOf(get<((WordCombinedUi) -> Unit)> { parametersOf(routeManager) }, openMenuItems) }
        val favoritesFactory: FavoritesScreenFactory =
            get { parametersOf(get<((WordCombinedUi) -> Unit)> { parametersOf(routeManager) }) }
        val hangmanFactory: HangmanGameScreenFactory =
            get { parametersOf(get<((WordCombinedUi) -> Unit)> { parametersOf(routeManager) }) }

        routeManager.registerGraph(
            mainGraph(dashboardFactory, get(), favoritesFactory, get(), get(), hangmanFactory, get(), get())
        )

        routeManager
    }
}

@Suppress("LongParameterList")
fun mainGraph(
    dashboardScreenFactory: DashboardScreenFactory,
    detailsScreenFactory: WordDetailsScreenFactory,
    favoritesScreenFactory: FavoritesScreenFactory,
    gamesScreenFactory: GamesScreenFactory,
    enigmaGameScreenFactory: EnigmaGameScreenFactory,
    hangmanGameScreenFactory: HangmanGameScreenFactory,
    posDetailsScreenFactory: PosDetailsScreenFactory,
    settingsScreenFactory: SettingsScreenFactory,
) = NavigationGraph(mainGraphId, dashboardScreenFactory).apply {
    register(detailsScreenFactory)
    register(favoritesScreenFactory)
    register(gamesScreenFactory)
    register(enigmaGameScreenFactory)
    register(hangmanGameScreenFactory)
    register(posDetailsScreenFactory)
    register(settingsScreenFactory)
}

expect val isRelease: Boolean
