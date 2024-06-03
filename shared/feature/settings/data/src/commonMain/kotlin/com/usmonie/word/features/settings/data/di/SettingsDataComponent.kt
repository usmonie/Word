package com.usmonie.word.features.settings.data.di

import com.usmonie.word.features.settings.data.repository.AppSettingsRepositoryImpl
import com.usmonie.word.features.settings.data.repository.UserRepositoryImpl
import com.usmonie.word.features.settings.domain.repository.AppSettingsRepository
import com.usmonie.word.features.settings.domain.repository.UserRepository
import org.koin.dsl.module

val settingsDataModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<AppSettingsRepository> { AppSettingsRepositoryImpl(get()) }
}
