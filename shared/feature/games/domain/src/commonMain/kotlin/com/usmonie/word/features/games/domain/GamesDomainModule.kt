package com.usmonie.word.features.games.domain

import com.usmonie.word.features.games.domain.usecases.*
import com.usmonie.word.features.games.domain.usecases.GetEnigmaLevelUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gamesDomainUseCase = module {
    singleOf(::GetCryptogramQuoteUseCaseImpl) {
        binds(listOf(GetCryptogramQuoteUseCase::class))
    }

    singleOf(::UserProgressRepositoryImpl) {
        binds(listOf(UserProgressRepository::class))
    }

    singleOf(::DifficultyAdjuster)

    singleOf(::GetEnigmaLevelUseCaseImpl) {
        bind<GetEnigmaLevelUseCase>()
    }
}
