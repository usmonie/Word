package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordDomain
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface GetAllFavouritesUseCase : CoroutineUseCase<Unit, List<WordDomain>>

class GetAllFavouritesUseCaseImpl(
    private val wordRepository: WordRepository
) : GetAllFavouritesUseCase {
    override suspend fun invoke(input: Unit): List<WordDomain> {
        return wordRepository.getAllFavourites()
    }
}