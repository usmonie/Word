package com.usmonie.word.features.qutoes.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase

interface GetNextPhraseUseCase : CoroutineUseCase<Unit, String>

internal class GetNextPhraseUseCaseImpl : GetNextPhraseUseCase {
    override suspend fun invoke(input: Unit): String {
        return "If you look at what you have in life, you'll always have more. " +
            "If you look at what you don't have in life, you'll never have enough."
    }
}
