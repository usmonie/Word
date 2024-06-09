package com.usmonie.word.features.games.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.games.domain.repository.WordsRepository

interface ClearRecentUseCase : CoroutineUseCase<Unit, Unit>

internal class ClearRecentUseCaseImpl(private val wordsRepository: WordsRepository) :
    ClearRecentUseCase {
    override suspend fun invoke(input: Unit) {
        wordsRepository.clearSearchHistory()
    }
}
