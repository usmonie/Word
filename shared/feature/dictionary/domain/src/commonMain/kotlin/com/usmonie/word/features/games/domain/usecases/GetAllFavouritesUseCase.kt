package com.usmonie.word.features.games.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.games.domain.models.WordCombined
import com.usmonie.word.features.games.domain.repository.WordsRepository

interface GetAllFavouritesUseCase : CoroutineUseCase<Unit, List<WordCombined>>

internal class GetAllFavouritesUseCaseImpl(
    private val wordsRepository: WordsRepository
) : GetAllFavouritesUseCase {
    override suspend fun invoke(input: Unit): List<WordCombined> {
        return wordsRepository.getAllFavorites()
    }
}
