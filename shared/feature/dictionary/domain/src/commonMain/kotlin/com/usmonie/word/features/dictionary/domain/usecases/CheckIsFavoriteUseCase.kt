package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.repository.WordsRepository

interface CheckIsFavoriteUseCase : CoroutineUseCase<String, Boolean>

internal class CheckIsFavoriteUseCaseImpl(private val wordsRepository: WordsRepository) :
    CheckIsFavoriteUseCase {
    override suspend fun invoke(input: String): Boolean {
        return wordsRepository.isFavorite(input)
    }
}
