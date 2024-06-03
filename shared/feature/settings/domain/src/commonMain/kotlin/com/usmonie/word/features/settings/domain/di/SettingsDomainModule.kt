package com.usmonie.word.features.settings.domain.di

import com.usmonie.word.features.settings.domain.usecase.AddUserHintsCountUseCase
import com.usmonie.word.features.settings.domain.usecase.AddUserHintsCountUseCaseImpl
import com.usmonie.word.features.settings.domain.usecase.ChangeThemeUseCase
import com.usmonie.word.features.settings.domain.usecase.ChangeThemeUseCaseImpl
import com.usmonie.word.features.settings.domain.usecase.CurrentThemeUseCase
import com.usmonie.word.features.settings.domain.usecase.CurrentThemeUseCaseImpl
import com.usmonie.word.features.settings.domain.usecase.GetUserHintsCountUseCase
import com.usmonie.word.features.settings.domain.usecase.GetUserHintsCountUseCaseImpl
import com.usmonie.word.features.settings.domain.usecase.UseUserHintsCountUseCase
import com.usmonie.word.features.settings.domain.usecase.UseUserHintsCountUseCaseImpl
import org.koin.dsl.module

val settingsDomainModule = module {
    factory<GetUserHintsCountUseCase> { GetUserHintsCountUseCaseImpl(get()) }
    factory<AddUserHintsCountUseCase> { AddUserHintsCountUseCaseImpl(get()) }
    factory<UseUserHintsCountUseCase> { UseUserHintsCountUseCaseImpl(get()) }
    factory<ChangeThemeUseCase> { ChangeThemeUseCaseImpl(get()) }
    factory<CurrentThemeUseCase> { CurrentThemeUseCaseImpl(get()) }
    factory<UseUserHintsCountUseCase> { UseUserHintsCountUseCaseImpl(get()) }
}
