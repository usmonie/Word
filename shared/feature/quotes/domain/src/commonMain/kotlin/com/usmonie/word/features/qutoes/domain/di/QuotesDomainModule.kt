package com.usmonie.word.features.qutoes.domain.di

import com.usmonie.word.features.qutoes.domain.usecases.GetRandomQuoteUseCase
import com.usmonie.word.features.qutoes.domain.usecases.GetRandomQuoteUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val quotesDomainModule = module {
    singleOf(::GetRandomQuoteUseCaseImpl) {
        bind<GetRandomQuoteUseCase>()
    }
}
