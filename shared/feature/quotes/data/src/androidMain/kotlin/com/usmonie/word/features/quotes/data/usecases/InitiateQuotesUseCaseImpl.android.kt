package com.usmonie.word.features.quotes.data.usecases

import com.usmonie.word.features.qutoes.domain.usecases.InitiateQuotesUseCase

actual class InitiateQuotesUseCaseImpl : InitiateQuotesUseCase {
    override suspend fun invoke(input: Unit) = Unit
}