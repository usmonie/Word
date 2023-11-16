package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordDomain
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

/**
 * Use case for adding a word to favorites.
 */
interface UpdateFavouriteUseCase : CoroutineUseCase<UpdateFavouriteUseCase.Param, WordDomain> {

    /**
     * Data class representing the parameters required to add a word to favorites.
     *
     * @property word The `WordDomain` object to be added to favorites.
     */
    data class Param(val word: WordDomain)
}

class UpdateFavouriteUseCaseImpl(
    private val wordRepository: WordRepository
) : UpdateFavouriteUseCase {

    /**
     * Invokes the use case to add a word to the favorites in the repository.
     *
     * @param input The `Param` object containing the word to be added to favorites.
     */
    override suspend fun invoke(input: UpdateFavouriteUseCase.Param): WordDomain {
        if (input.word.isFavourite) {
            wordRepository.deleteFromFavourites(input.word)
        } else {
            wordRepository.addToFavourites(input.word)
        }

        return input.word.copy(isFavourite = !input.word.isFavourite)
    }
}