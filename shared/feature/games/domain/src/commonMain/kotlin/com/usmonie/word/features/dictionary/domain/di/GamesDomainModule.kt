package com.usmonie.word.features.dictionary.domain.di

import com.usmonie.word.features.dictionary.domain.usecases.GetEnigmaQuoteUseCase
import com.usmonie.word.features.dictionary.domain.usecases.GetEnigmaQuoteUseCaseImpl
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gamesDomainUseCase = module {
    singleOf(::GetEnigmaQuoteUseCaseImpl) {
        binds(listOf(GetEnigmaQuoteUseCase::class))
    }
}
