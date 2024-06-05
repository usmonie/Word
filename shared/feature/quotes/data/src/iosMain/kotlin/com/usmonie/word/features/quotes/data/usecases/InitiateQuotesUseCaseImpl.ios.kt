package com.usmonie.word.features.quotes.data.usecases

import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository
import com.usmonie.word.features.qutoes.domain.usecases.InitiateQuotesUseCase

actual class InitiateQuotesUseCaseImpl(private val quotesRepository: QuotesRepository) : InitiateQuotesUseCase {
    override suspend fun invoke(input: Unit) {
        val count = quotesRepository.getQuotesCount()

        if (count == QUOTES_COUNT) return

        TODO("add implementation for parsing quotes and store them in database")
    }
}

private const val QUOTES_COUNT: Long = 493790