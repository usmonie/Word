package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface CheckIsFavoriteUseCase : CoroutineUseCase<String, Boolean>

class CheckIsFavoriteUseCaseImpl(private val wordRepository: WordRepository) :
    CheckIsFavoriteUseCase {
    override suspend fun invoke(input: String): Boolean {
        return wordRepository.isFavorite(input)
    }

}