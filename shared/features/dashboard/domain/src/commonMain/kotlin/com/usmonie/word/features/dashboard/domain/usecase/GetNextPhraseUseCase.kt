package com.usmonie.word.features.dashboard.domain.usecase

import wtf.word.core.domain.usecases.CoroutineUseCase

interface GetNextPhraseUseCase : CoroutineUseCase<Unit, String>

 class GetNextPhraseUseCaseImpl : GetNextPhraseUseCase {
    override suspend fun invoke(input: Unit): String {
        return "If you look at what you have in life, you'll always have more. " +
                "If you look at what you don't have in life, you'll never have enough."
    }
}