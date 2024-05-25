package com.usmonie.word.features.settings.data.di

import com.usmonie.word.features.settings.data.repository.AppSettingsRepositoryImpl
import com.usmonie.word.features.settings.domain.repository.AppSettingsRepository
import org.koin.dsl.module

val settingsDataModule = module {
    single<AppSettingsRepository> { AppSettingsRepositoryImpl(get()) }
}
