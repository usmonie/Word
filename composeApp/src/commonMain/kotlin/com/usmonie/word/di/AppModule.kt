package com.usmonie.word.di

import com.usmonie.compass.core.GraphId
import com.usmonie.compass.core.NavigationGraph
import com.usmonie.compass.core.getRouteManager
import com.usmonie.word.features.dashboard.ui.screen.DashboardScreenFactory
import com.usmonie.word.features.details.ui.pos.PosDetailsScreenFactory
import com.usmonie.word.features.details.ui.word.WordDetailsScreenFactory
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainGraphId = GraphId("MainGraph")

val appModule = module {
    factory(named(mainGraphId.id)) { dashboardFactory ->
        mainGraph(dashboardFactory.get(), get(), get())
    }

    factory {
        val routeManager = getRouteManager()
        val openWord: (WordCombinedUi) -> Unit = {
            routeManager.navigateTo(
                WordDetailsScreenFactory.ID,
                extras = WordDetailsScreenFactory.WordDetailsExtra(it)
            )
        }

        val factory: DashboardScreenFactory = get { parametersOf(openWord) }

        routeManager.registerGraph(
            get(qualifier = named(mainGraphId.id),) {
                parametersOf(factory)
            }
        )

        routeManager
    }
}

fun mainGraph(
    dashboardScreenFactory: DashboardScreenFactory,
    detailsScreenFactory: WordDetailsScreenFactory,
    posDetailsScreenFactory: PosDetailsScreenFactory,
) = NavigationGraph(mainGraphId, dashboardScreenFactory).apply {
    register(detailsScreenFactory)
    register(posDetailsScreenFactory)
}
