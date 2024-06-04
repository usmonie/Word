package com.usmonie.word.features.dictionary.domain.usecases

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dictionary.domain.repository.WordsRepository

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

internal class UpdateFavouriteUseCaseImpl(
    private val wordsRepository: WordsRepository
) : UpdateFavouriteUseCase {

    /**
     * Invokes the use case to add a word to the favorites in the repository.
     *
     * @param input The `Param` object containing the word to be added to favorites.
     */
    override suspend fun invoke(input: UpdateFavouriteUseCase.Param) {
        if (input.isFavorite) {
            wordsRepository.deleteFavorite(input.word)
        } else {
            wordsRepository.addFavorite(input.word)
        }
    }
}
