package com.usmonie.word.di

import com.usmonie.compass.core.GraphId
import com.usmonie.compass.core.NavigationGraph
import com.usmonie.compass.core.getRouteManager
import com.usmonie.word.features.dashboard.data.di.dashboardDataModule
import com.usmonie.word.features.dashboard.ui.di.dashboardUiModule
import com.usmonie.word.features.dashboard.ui.screen.DashboardMenuItem
import com.usmonie.word.features.dashboard.ui.screen.DashboardMenuItem.FAVORITES
import com.usmonie.word.features.dashboard.ui.screen.DashboardMenuItem.GAMES
import com.usmonie.word.features.dashboard.ui.screen.DashboardMenuItem.SETTINGS
import com.usmonie.word.features.dashboard.ui.screen.DashboardScreenFactory
import com.usmonie.word.features.details.ui.di.wordDetailsUiModule
import com.usmonie.word.features.details.ui.pos.PosDetailsScreenFactory
import com.usmonie.word.features.details.ui.word.WordDetailsScreenFactory
import com.usmonie.word.features.dictionary.data.di.dictionaryDataModule
import com.usmonie.word.features.dictionary.domain.di.dictionaryDomainUseCase
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.favorites.ui.FavoritesScreenFactory
import com.usmonie.word.features.favorites.ui.di.favoritesUiModule
import com.usmonie.word.features.settings.data.di.settingsDataModule
import com.usmonie.word.features.settings.ui.SettingsScreenFactory
import com.usmonie.word.features.settings.ui.di.settingsUiModule
import com.usmonie.word.features.subscription.data.di.subscriptionDataModule
import com.usmonie.word.features.subscriptions.ui.subscriptionsUiModule
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainGraphId = GraphId("MainGraph")

val appModule = module {
    includes(
        dictionaryDomainUseCase,
        dictionaryDataModule,
        dashboardDataModule,
        dashboardUiModule,
        favoritesUiModule,
        wordDetailsUiModule,
        subscriptionDataModule,
        subscriptionsUiModule,
        settingsDataModule,
        settingsUiModule,
    )
    factory(named(mainGraphId.id)) { (dashboard: DashboardScreenFactory, favorites: FavoritesScreenFactory) ->
        mainGraph(dashboard, get(), get(), favorites, get())
    }

    factory {
        val routeManager = getRouteManager()
        val openWord: (WordCombinedUi) -> Unit = {
            routeManager.navigateTo(
                WordDetailsScreenFactory.ID,
                extras = WordDetailsScreenFactory.WordDetailsExtra(it)
            )
        }

        val openMenuItems: (DashboardMenuItem) -> Unit = {
            routeManager.navigateTo(
                when (it) {
                    FAVORITES -> FavoritesScreenFactory.ID
                    SETTINGS -> SettingsScreenFactory.ID
                    GAMES -> TODO()
                }
            )
        }

        val dashboardFactory: DashboardScreenFactory = get { parametersOf(openWord, openMenuItems) }
        val favoritesFactory: FavoritesScreenFactory = get { parametersOf(openWord) }

        routeManager.registerGraph(
            get(qualifier = named(mainGraphId.id)) {
                parametersOf(dashboardFactory, favoritesFactory)
            }
        )

        routeManager
    }
}

fun mainGraph(
    dashboardScreenFactory: DashboardScreenFactory,
    detailsScreenFactory: WordDetailsScreenFactory,
    posDetailsScreenFactory: PosDetailsScreenFactory,
    favoritesScreenFactory: FavoritesScreenFactory,
    settingsScreenFactory: SettingsScreenFactory,
) = NavigationGraph(mainGraphId, dashboardScreenFactory).apply {
    register(detailsScreenFactory)
    register(posDetailsScreenFactory)
    register(favoritesScreenFactory)
    register(settingsScreenFactory)
}
