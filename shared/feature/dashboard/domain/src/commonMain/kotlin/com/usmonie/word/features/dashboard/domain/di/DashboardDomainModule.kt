package com.usmonie.word.features.dashboard.domain.di

import com.usmonie.word.features.dashboard.domain.usecase.ChangeThemeUseCase
import com.usmonie.word.features.dashboard.domain.usecase.ChangeThemeUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCase
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.UseUserHintsCountUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UseUserHintsCountUseCaseImpl
import org.koin.dsl.module

val dashboardDomainModule = module {
    factory<ChangeThemeUseCase> { ChangeThemeUseCaseImpl(get()) }
    factory<CurrentThemeUseCase> { CurrentThemeUseCaseImpl(get()) }
    factory<UseUserHintsCountUseCase> { UseUserHintsCountUseCaseImpl(get()) }
}
