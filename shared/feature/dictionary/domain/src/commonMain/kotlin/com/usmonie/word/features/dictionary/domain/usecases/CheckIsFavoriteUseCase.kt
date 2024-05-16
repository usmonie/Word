package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.repository.WordRepository

interface CheckIsFavoriteUseCase : CoroutineUseCase<String, Boolean>

internal class CheckIsFavoriteUseCaseImpl(private val wordRepository: WordRepository) :
    CheckIsFavoriteUseCase {
    override suspend fun invoke(input: String): Boolean {
        return wordRepository.isFavorite(input)
    }
}
