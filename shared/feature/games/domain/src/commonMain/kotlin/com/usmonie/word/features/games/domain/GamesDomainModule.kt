package com.usmonie.word.features.games.domain

import com.usmonie.word.features.games.domain.repositories.*
import com.usmonie.word.features.games.domain.usecases.*
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gamesDomainUseCase = module {
    singleOf(::GetCryptogramQuoteUseCaseImpl) {
        bind<GetCryptogramQuoteUseCase>()
    }

    singleOf(::UserProgressRepositoryImpl) {
        binds(listOf(UserProgressRepository::class))
    }

    singleOf(::DifficultyAdjuster)

    singleOf(::GetEnigmaLevelUseCaseImpl) {
        bind<GetEnigmaLevelUseCase>()
    }
}
