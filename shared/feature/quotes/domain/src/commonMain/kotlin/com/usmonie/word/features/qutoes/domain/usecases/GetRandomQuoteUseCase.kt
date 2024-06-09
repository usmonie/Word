package com.usmonie.word.features.qutoes.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.qutoes.domain.models.Quote
import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository

interface GetRandomQuoteUseCase : CoroutineUseCase<Unit, Quote>

internal class GetRandomQuoteUseCaseImpl(private val quotesRepository: QuotesRepository) : GetRandomQuoteUseCase {
    override suspend fun invoke(input: Unit): Quote {
        return quotesRepository.getRandomQuote()
    }
}
