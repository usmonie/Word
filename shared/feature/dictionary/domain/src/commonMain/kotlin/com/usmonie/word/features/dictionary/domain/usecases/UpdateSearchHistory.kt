package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.models.WordCombined
import com.usmonie.word.features.dictionary.domain.repository.WordsRepository

interface UpdateSearchHistory : CoroutineUseCase<UpdateSearchHistory.Param, List<WordCombined>> {

    data class Param(val word: String)
}

internal class UpdateSearchHistoryImpl(private val wordsRepository: WordsRepository) : UpdateSearchHistory {
    override suspend fun invoke(input: UpdateSearchHistory.Param): List<WordCombined> {
        return wordsRepository.addToSearchHistory(input.word)
    }
}
