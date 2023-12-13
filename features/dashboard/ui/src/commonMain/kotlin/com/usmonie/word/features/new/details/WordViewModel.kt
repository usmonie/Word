package com.usmonie.word.features.new.details

import com.usmonie.word.features.dashboard.domain.usecase.GetSimilarWordsUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.new.models.WordCombinedUi
import wtf.speech.core.ui.BaseViewModel
import wtf.speech.core.ui.ContentState
import wtf.word.core.domain.Analytics

class WordViewModel(
    private val extra: WordDetailsScreen.Companion.WordExtra,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getSimilarWordsUseCase: GetSimilarWordsUseCase,
    private val analytics: Analytics
) : BaseViewModel<WordState, WordAction, WordEvent, WordEffect>(WordState(extra.word)) {

    fun onUpdateFavouritePressed(word: WordCombinedUi) = handleAction(WordAction.UpdateFavourite(word))
    fun onSharePressed(word: WordCombinedUi) = handleAction(WordAction.UpdateFavourite(word))
//    fun onFindSynonymPressed(synonym: SynonymUi) {}
    fun onWordPressed(word: WordCombinedUi) = handleAction(WordAction.OpenWord(word))

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
        is WordAction.OpenWord -> {
//            analytics.log(DashboardAnalyticsEvents.OpenWord(action.word))
            WordEvent.OpenWord(action.word)
        }
        is WordAction.UpdateFavourite -> updateFavourite(action.word)
        is WordAction.Initial -> loadSimilar(action.word)
    }

    override suspend fun handleEvent(event: WordEvent): WordEffect? {
        return when (event) {
            is WordEvent.OpenWord -> WordEffect.OpenWord(event.word)
            else -> null
        }
    }

    private suspend fun updateFavourite(word: WordCombinedUi): WordEvent {
        return WordEvent.UpdateWord(
//            updateFavouriteUseCase(UpdateFavouriteUseCase.Param(word = word\)).to(),
            word
        )
    }

    private suspend fun loadSimilar(word: WordCombinedUi): WordEvent {
//        val found = getSimilarWordsUseCase(
//                GetSimilarWordsUseCase.Param(
//                    word.toDomain(),
//                    0,
//                    20
//                )
//            ).fastMap { it.to() }

        return WordEvent.SimilarWords(listOf())
    }
}