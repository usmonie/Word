package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.models.WordCombined
import com.usmonie.word.features.dictionary.domain.repository.WordsRepository

interface GetWordOfTheDayUseCase : CoroutineUseCase<GetWordOfTheDayUseCase.Param, WordCombined> {
    object Param
}

internal class GetWordOfTheDayUseCaseImpl(private val wordsRepository: WordsRepository) :
    GetWordOfTheDayUseCase {
    override suspend fun invoke(input: GetWordOfTheDayUseCase.Param): WordCombined {
        return wordsRepository.getWordOfTheDay()
    }
}
