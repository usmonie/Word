package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordCombined
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

/**
 * Use case for retrieving search history.
 */
interface GetSearchHistoryUseCase :
    CoroutineUseCase<GetSearchHistoryUseCase.Param, List<WordCombined>> {
    /**
     * Data class representing the parameters required to retrieve search history.
     *
     * @property offset The offset for pagination.
     * @property limit The maximum number of search history entries to return.
     */
    data class Param(val offset: Long, val limit: Long)
}

class GetSearchHistoryUseCaseImpl(private val wordRepository: WordRepository) : GetSearchHistoryUseCase {
    /**
     * Invokes the use case to retrieve the search history from the repository based on pagination parameters.
     *
     * @param input The `Param` object containing the pagination parameters.
     * @return A list of `WordCombined` objects that represent the search history.
     */
    override suspend fun invoke(input: GetSearchHistoryUseCase.Param): List<WordCombined> {
        return wordRepository.getSearchHistory()
    }
}
