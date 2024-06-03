package com.usmonie.word.features.settings.ui.di

import com.usmonie.word.features.settings.domain.di.settingsDomainModule
import com.usmonie.word.features.settings.ui.SettingsScreenFactory
import com.usmonie.word.features.settings.ui.usecases.UserSelectedThemeUseCase
import com.usmonie.word.features.settings.ui.usecases.UserSelectedThemeUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val settingsUiModule = module {
    includes(settingsDomainModule)
    factoryOf(::SettingsScreenFactory)
    factory<UserSelectedThemeUseCase> { UserSelectedThemeUseCaseImpl(get(), get()) }
}
