package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.qutoes.domain.models.Quote

interface GetEnigmaQuoteUseCase : CoroutineUseCase<GetEnigmaQuoteUseCase.Param, Quote> {

    class Param
}

internal class GetEnigmaQuoteUseCaseImpl : GetEnigmaQuoteUseCase {
    override suspend fun invoke(input: GetEnigmaQuoteUseCase.Param): Quote {
        return Quote(
            1,
            "If you look at what you have in life, you'll always have more. " +
                "If you look at what you don't have in life, you'll never have enough.",
            "Usman Akhmedov",
            listOf(),
            true
        )
    }
}
