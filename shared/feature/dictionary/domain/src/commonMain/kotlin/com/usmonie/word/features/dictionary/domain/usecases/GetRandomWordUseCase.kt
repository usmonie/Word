package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.models.WordCombined
import com.usmonie.word.features.dictionary.domain.repository.WordsRepository

interface GetRandomWordUseCase : CoroutineUseCase<GetRandomWordUseCase.Param, WordCombined> {
    data class Param(val maxSymbolsCount: Int = Int.MAX_VALUE)
}

internal class GetRandomWordUseCaseImpl(
    private val wordsRepository: WordsRepository
) : GetRandomWordUseCase {

    override suspend fun invoke(input: GetRandomWordUseCase.Param): WordCombined {
        return wordsRepository.getRandomWord(input.maxSymbolsCount)
    }
}
