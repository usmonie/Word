package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.models.WordCombined
import com.usmonie.word.features.dictionary.domain.repository.WordRepository

interface GetWordOfTheDayUseCase : CoroutineUseCase<GetWordOfTheDayUseCase.Param, WordCombined> {
    object Param
}

internal class GetWordOfTheDayUseCaseImpl(private val wordRepository: WordRepository) :
    GetWordOfTheDayUseCase {
    override suspend fun invoke(input: GetWordOfTheDayUseCase.Param): WordCombined {
        return wordRepository.getWordOfTheDay()
    }
}
