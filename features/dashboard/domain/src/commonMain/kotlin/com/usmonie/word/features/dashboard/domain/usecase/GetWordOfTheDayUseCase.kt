package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordCombined
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface GetWordOfTheDayUseCase : CoroutineUseCase<GetWordOfTheDayUseCase.Param, WordCombined> {
    object Param
}

class GetWordOfTheDayUseCaseImpl(private val wordRepository: WordRepository) : GetWordOfTheDayUseCase {
    override suspend fun invoke(input: GetWordOfTheDayUseCase.Param): WordCombined {
        return wordRepository.getWordOfTheDay()
    }
}
