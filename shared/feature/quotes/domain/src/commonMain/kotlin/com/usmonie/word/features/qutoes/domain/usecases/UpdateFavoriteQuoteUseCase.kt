package com.usmonie.word.features.qutoes.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.qutoes.domain.models.Quote
import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository

interface UpdateFavoriteQuoteUseCase : CoroutineUseCase<Quote, Unit>

internal class UpdateFavoriteQuoteUseCaseImpl(private val quotesRepository: QuotesRepository) : UpdateFavoriteQuoteUseCase {
    override suspend fun invoke(input: Quote) {
        if (input.favorite) {
            quotesRepository.unfavorite(input)
        } else {
            quotesRepository.favorite(input)
        }
    }
}
