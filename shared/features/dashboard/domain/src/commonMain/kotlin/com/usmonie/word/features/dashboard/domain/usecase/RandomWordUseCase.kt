package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordCombined
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface RandomWordUseCase : CoroutineUseCase<RandomWordUseCase.Param, WordCombined> {
    data class Param(val maxSymbolsCount: Int = Int.MAX_VALUE)
}

class RandomWordUseCaseImpl(private val wordRepository: WordRepository) : RandomWordUseCase {

    override suspend fun invoke(input: RandomWordUseCase.Param): WordCombined {
        return wordRepository.getRandomWord(input.maxSymbolsCount)
    }
}
