package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.models.WordCombined
import com.usmonie.word.features.dictionary.domain.repository.WordRepository

interface GetRandomWordUseCase : CoroutineUseCase<GetRandomWordUseCase.Param, WordCombined> {
    data class Param(val maxSymbolsCount: Int = Int.MAX_VALUE)
}

internal class GetRandomWordUseCaseImpl(private val wordRepository: WordRepository) :
    GetRandomWordUseCase {

    override suspend fun invoke(input: GetRandomWordUseCase.Param): WordCombined {
        return wordRepository.getRandomWord(input.maxSymbolsCount)
    }
}
