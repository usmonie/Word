package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordDomain
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

/**
 * Use case for filtering words.
 */
interface FilterWordsUseCase : CoroutineUseCase<FilterWordsUseCase.Param, List<WordDomain>> {
    /**
     * Data class representing the parameters required to filter words.
     *
     * @property char The character to filter the words by.
     * @property offset The offset for pagination.
     * @property limit The maximum number of words to return.
     */
    data class Param(val char: Char, val offset: Long, val limit: Long)
}

class FilterWordsUseCaseImpl(private val wordRepository: WordRepository): FilterWordsUseCase {
    /**
     * Invokes the use case to filter words from the repository based on the given character and pagination parameters.
     *
     * @param input The `Param` object containing the filter parameters.
     * @return A list of `WordDomain` objects that match the filter criteria.
     */
    override suspend fun invoke(input: FilterWordsUseCase.Param): List<WordDomain> {
        return wordRepository.filter(input.char, input.offset, input.limit)
    }
}