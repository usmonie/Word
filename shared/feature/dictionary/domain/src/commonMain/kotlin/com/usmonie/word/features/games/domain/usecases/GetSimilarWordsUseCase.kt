package com.usmonie.word.features.games.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.games.domain.models.WordCombined
import com.usmonie.word.features.games.domain.repository.WordsRepository

interface GetSimilarWordsUseCase :
    CoroutineUseCase<GetSimilarWordsUseCase.Param, List<WordCombined>> {
    data class Param(val word: WordCombined, val offset: Int, val limit: Int)
}

class GetSimilarWordsUseCaseImpl(
    private val wordsRepository: WordsRepository
) : GetSimilarWordsUseCase {
    override suspend fun invoke(input: GetSimilarWordsUseCase.Param): List<WordCombined> {
        return listOf() /*wordRepository.searchSynonymsForWord(
            input.word.word,
            input.offset.toLong(),
            input.limit.toLong()
        )*/
    }
}
