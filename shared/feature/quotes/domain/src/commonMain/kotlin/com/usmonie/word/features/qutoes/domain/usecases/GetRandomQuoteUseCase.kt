package com.usmonie.word.features.qutoes.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.qutoes.domain.models.Quote

interface GetRandomQuoteUseCase: CoroutineUseCase<GetRandomQuoteUseCase.Param, Quote> {

    class Param
}

internal class GetRandomQuoteUseCaseImpl : GetRandomQuoteUseCase {
    override suspend fun invoke(input: GetRandomQuoteUseCase.Param): Quote {
        TODO("Not yet implemented")
    }

}