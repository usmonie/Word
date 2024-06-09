package com.usmonie.word.features.games.domain

import com.usmonie.word.features.games.domain.usecases.GetEnigmaLevelUseCase
import com.usmonie.word.features.games.domain.usecases.GetEnigmaLevelUseCaseImpl
import com.usmonie.word.features.games.domain.usecases.GetEnigmaQuoteUseCase
import com.usmonie.word.features.games.domain.usecases.GetEnigmaQuoteUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gamesDomainUseCase = module {
    singleOf(::GetEnigmaQuoteUseCaseImpl) {
        binds(listOf(GetEnigmaQuoteUseCase::class))
    }

    singleOf(::GetEnigmaLevelUseCaseImpl) {
        bind<GetEnigmaLevelUseCase>()
    }
}
