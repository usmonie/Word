package com.usmonie.word.features.detail

import com.usmonie.word.features.dashboard.domain.usecase.GetSimilarWordsUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.models.SynonymUi
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.models.to
import com.usmonie.word.features.models.toDomain
import wtf.speech.core.ui.BaseViewModel
import wtf.speech.core.ui.ContentState

class WordViewModel(
    private val extra: WordScreen.Companion.WordExtra,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getSimilarWordsUseCase: GetSimilarWordsUseCase
) : BaseViewModel<WordState, WordAction, WordEvent, WordEffect>(WordState(extra.word)) {

    fun onUpdateFavouritePressed(word: WordUi) = handleAction(WordAction.UpdateFavourite(word))
    fun onSharePressed(word: WordUi) = handleAction(WordAction.UpdateFavourite(word))
    fun onFindSynonymPressed(synonym: SynonymUi) {}

    fun onWordPressed(word: WordUi) = handleAction(WordAction.OpenWord(word))

    init {
        handleAction(WordAction.Initial(extra.word))
    }

    override fun WordState.reduce(event: WordEvent): WordState {
        return when (event) {
            is WordEvent.OpenWord -> this
            is WordEvent.UpdateWord -> this.copy(word = event.word)
            is WordEvent.SimilarWords -> this.copy(similarWords = ContentState.Success(event.words))
        }
    }

    override suspend fun processAction(action: WordAction): WordEvent = when (action) {
        is WordAction.OpenWord -> WordEvent.OpenWord(action.word)
        is WordAction.UpdateFavourite -> updateFavourite(action.word)
        is WordAction.Initial -> loadSimilar(action.word)
    }

    override suspend fun handleEvent(event: WordEvent): WordEffect? {
        return when (event) {
            is WordEvent.OpenWord -> WordEffect.OpenWord(event.word)
            else -> null
        }
    }

    private suspend fun updateFavourite(word: WordUi): WordEvent {
        return WordEvent.UpdateWord(
            updateFavouriteUseCase(UpdateFavouriteUseCase.Param(word.toDomain())).to(),
        )
    }

    private suspend fun loadSimilar(word: WordUi): WordEvent {
        val found = getSimilarWordsUseCase(
                GetSimilarWordsUseCase.Param(
                    word.toDomain(),
                    0,
                    20
                )
            ).map { it.to() }

        println("searchSynonymsForWord ${found.size}")

        return WordEvent.SimilarWords(found)
    }
}