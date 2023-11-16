package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordDomain
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface GetWordOfTheDayUseCase : CoroutineUseCase<GetWordOfTheDayUseCase.Param, WordDomain> {
    object Param
}

class GetWordOfTheDayUseCaseImpl(private val wordRepository: WordRepository) : GetWordOfTheDayUseCase {
    override suspend fun invoke(input: GetWordOfTheDayUseCase.Param): WordDomain {
        return wordRepository.getWordOfTheDay()
    }
}
