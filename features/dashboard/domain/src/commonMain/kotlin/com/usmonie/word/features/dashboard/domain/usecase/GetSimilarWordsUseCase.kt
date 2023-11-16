package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordDomain
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface GetSimilarWordsUseCase :
    CoroutineUseCase<GetSimilarWordsUseCase.Param, List<WordDomain>> {
    data class Param(val word: WordDomain, val offset: Int, val limit: Int)
}

class GetSimilarWordsUseCaseImpl(
    private val wordRepository: WordRepository
) : GetSimilarWordsUseCase {
    override suspend fun invoke(input: GetSimilarWordsUseCase.Param): List<WordDomain> {
        return wordRepository.searchSynonymsForWord(
            input.word.word,
            input.offset.toLong(),
            input.limit.toLong()
        )
    }
}