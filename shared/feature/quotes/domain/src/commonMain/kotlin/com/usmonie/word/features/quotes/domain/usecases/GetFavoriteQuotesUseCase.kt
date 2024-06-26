package com.usmonie.word.features.quotes.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.quotes.domain.models.Quote
import com.usmonie.word.features.quotes.domain.repositories.QuotesRepository

interface GetFavoriteQuotesUseCase : CoroutineUseCase<Unit, List<Quote>>

internal class GetFavoriteQuotesUseCaseImpl(private val quotesRepository: QuotesRepository) : GetFavoriteQuotesUseCase {
    override suspend fun invoke(input: Unit): List<Quote> {
        return quotesRepository.getFavorites()
    }
}
