package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordDomain
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface RandomWordUseCase : CoroutineUseCase<RandomWordUseCase.Param, WordDomain> {
    data class Param(val maxSymbolsCount: Int = Int.MAX_VALUE)
}

class RandomWordUseCaseImpl(private val wordRepository: WordRepository) : RandomWordUseCase {

    override suspend fun invoke(input: RandomWordUseCase.Param): WordDomain {
        return wordRepository.getRandomWord(input.maxSymbolsCount)
    }
}
