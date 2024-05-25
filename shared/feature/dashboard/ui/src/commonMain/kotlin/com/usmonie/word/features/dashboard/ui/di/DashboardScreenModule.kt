package com.usmonie.word.features.dashboard.ui.di

import com.usmonie.word.features.ads.ui.AdMob
import com.usmonie.word.features.ads.ui.AdMobState
import com.usmonie.word.features.dashboard.domain.di.dashboardDomainModule
import com.usmonie.word.features.dashboard.ui.screen.DashboardMenuItem
import com.usmonie.word.features.dashboard.ui.screen.DashboardScreenFactory
import com.usmonie.word.features.dashboard.ui.screen.DashboardViewModel
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dashboardUiModule = module {
    includes(dashboardDomainModule)
    factoryOf(::DashboardViewModel)
    factory { (openWord: ((WordCombinedUi) -> Unit), openMenuItem: ((DashboardMenuItem) -> Unit)) ->
        DashboardScreenFactory(
            get(),
            get(),
            openWord,
            openMenuItem,
            AdMob({}, { _, _ -> }, {}, get(), MutableStateFlow(AdMobState()))
        )
    }
}
