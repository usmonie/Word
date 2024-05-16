package com.usmonie.word.features.dashboard.ui.di

import com.usmonie.word.features.dashboard.ui.screen.DashboardScreenFactory
import com.usmonie.word.features.dashboard.ui.screen.DashboardViewModel
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dashboardScreensModule = module {
    factoryOf(::DashboardViewModel)
    single { (params: ((WordCombinedUi) -> Unit)) ->
        DashboardScreenFactory(get(), get(), get(), get(), get(), get(), params)
    }
}
