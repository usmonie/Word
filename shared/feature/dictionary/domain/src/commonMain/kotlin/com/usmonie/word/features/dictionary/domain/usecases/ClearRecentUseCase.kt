package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.repository.WordRepository

interface ClearRecentUseCase : CoroutineUseCase<Unit, Unit>

internal class ClearRecentUseCaseImpl(private val wordRepository: WordRepository) :
    ClearRecentUseCase {
    override suspend fun invoke(input: Unit) {
        wordRepository.clearSearchHistory()
    }
}
