package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase


interface ClearRecentUseCase : CoroutineUseCase<Unit, Unit>

class ClearRecentUseCaseImpl(private val wordRepository: WordRepository) : ClearRecentUseCase {
    override suspend fun invoke(input: Unit) {
        wordRepository.clearSearchHistory()
    }
}