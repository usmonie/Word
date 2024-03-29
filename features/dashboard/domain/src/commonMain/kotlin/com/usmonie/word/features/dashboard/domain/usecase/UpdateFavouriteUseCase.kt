package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

/**
 * Use case for adding a word to favorites.
 */
interface UpdateFavouriteUseCase : CoroutineUseCase<UpdateFavouriteUseCase.Param, Unit> {

    /**
     * Data class representing the parameters required to add a word to favorites.
     *
     * @property word The `WordDomain` object to be added to favorites.
     */
    data class Param(val word: String, val isFavorite: Boolean)
}

class UpdateFavouriteUseCaseImpl(
    private val wordRepository: WordRepository
) : UpdateFavouriteUseCase {

    /**
     * Invokes the use case to add a word to the favorites in the repository.
     *
     * @param input The `Param` object containing the word to be added to favorites.
     */
    override suspend fun invoke(input: UpdateFavouriteUseCase.Param) {
        if (input.isFavorite) {
            wordRepository.deleteFavorite(input.word)
        } else {
            wordRepository.addFavorite(input.word)
        }
    }
}