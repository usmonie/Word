package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.WordCombined
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface SearchWordsUseCase : CoroutineUseCase<SearchWordsUseCase.Param, List<WordCombined>> {

    data class Param(val query: String, val offset: Long, val limit: Long, val exactly: Boolean)
}

class SearchWordsUseCaseImpl(private val wordRepository: WordRepository) : SearchWordsUseCase {
    override suspend fun invoke(input: SearchWordsUseCase.Param): List<WordCombined> {
        /*        val foundWords = when (SearchWordQuery.match(input.query)) {
                    SearchWordQuery.InsideDescriptionWord -> {
                        wordRepository.searchInDescription(
                            input.query.drop(2),
                            input.offset,
                            input.limit
                        )
                    }

                    SearchWordQuery.InsideWord -> {
                        wordRepository.searchInside(
                            input.query.drop(2),
                            input.offset,
                            input.limit
                        )
                    }

                    SearchWordQuery.SynonymsForWord -> {
                        wordRepository.searchSynonymsForWord(
                            input.query.drop(2),
                            input.offset,
                            input.limit
                        )
                    }

                    null -> {
                        if (input.exactly) {
                            wordRepository.searchExactly(input.query, input.offset, input.limit)
                        } else {
                            wordRepository.search(input.query, input.offset, input.limit)
                        }
                    }
                }*/

        return wordRepository.searchWords(input.query, input.limit.toInt(), input.offset.toInt())
    }

    sealed class SearchWordQuery(private val regex: Regex) {
        data object InsideWord : SearchWordQuery(Regex("\\*\\*\\p{L}+"))
        data object SynonymsForWord : SearchWordQuery(Regex("\\#\\#\\p{L}+"))
        data object InsideDescriptionWord : SearchWordQuery(Regex(">\\p{L}+(\\s\\p{L}+)*"))

        fun matches(query: String): Boolean {
            return regex.matches(query)
        }

        companion object {
            fun match(query: String): SearchWordQuery? {
                return when {
                    InsideWord.matches(query) -> InsideWord
                    SynonymsForWord.matches(query) -> SynonymsForWord
                    InsideDescriptionWord.matches(query) -> InsideDescriptionWord
                    else -> null
                }
            }
        }
    }
}


