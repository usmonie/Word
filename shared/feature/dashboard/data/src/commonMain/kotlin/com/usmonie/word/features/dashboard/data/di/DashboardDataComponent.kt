package com.usmonie.word.features.dashboard.data.di

import com.usmonie.word.features.dashboard.data.repository.AppSettingsRepositoryImpl
import com.usmonie.word.features.dashboard.data.repository.UserRepositoryImpl
import com.usmonie.word.features.dashboard.domain.repository.AppSettingsRepository
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import org.koin.dsl.module

val dashboardDataModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<AppSettingsRepository> { AppSettingsRepositoryImpl(get()) }
}
