package com.usmonie.word.features.dashboard.ui.details

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Immutable
import com.usmonie.word.features.dashboard.ui.analytics.DashboardAnalyticsEvents
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import wtf.speech.core.ui.BaseViewModel
import wtf.speech.core.ui.ContentState
import wtf.word.core.domain.Analytics

@OptIn(ExperimentalMaterial3Api::class)
@Immutable
class WordViewModel(
    extra: WordDetailsScreen.Companion.WordExtra,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val analytics: Analytics,
) : BaseViewModel<WordState, WordAction, WordEvent, WordEffect>(
    WordState(extra.word,)
) {

    init {
        handleAction(WordAction.Initial(extra.word))
    }

    fun onUpdateFavouritePressed(word: WordCombinedUi) =
        handleAction(WordAction.UpdateFavourite(word))

    fun onSharePressed(word: WordCombinedUi) = handleAction(WordAction.UpdateFavourite(word))
    fun onSenseExpand() = handleAction(WordAction.OnExpandSense)
    fun onWordPressed(word: WordCombinedUi) = handleAction(WordAction.OpenWord(word))
    fun selectEtymology(index: Int) = handleAction(WordAction.SelectEtymology(index))
    fun selectPos(index: Int) = handleAction(WordAction.SelectPos(index))

    override fun WordState.reduce(event: WordEvent) = when (event) {
        is WordEvent.OpenWord -> this
        is WordEvent.UpdateWord -> this.copy(word = event.word)
        is WordEvent.SimilarWords -> this.copy(similarWords = ContentState.Success(event.words))
        is WordEvent.SelectEtymology -> this.copy(
            selectedEtymologyIndex = event.index,
            selectedPosIndex = 0
        )

        is WordEvent.SelectPos -> this.copy(selectedPosIndex = event.index)
        WordEvent.OnExpandSense -> this.copy(sensesExpanded = !sensesExpanded)
    }

    override suspend fun processAction(action: WordAction): WordEvent = when (action) {
        is WordAction.OpenWord -> {
            analytics.log(DashboardAnalyticsEvents.OpenWord(action.word))
            WordEvent.OpenWord(action.word)
        }

        is WordAction.UpdateFavourite -> updateFavourite(action.word)
        is WordAction.Initial -> loadSimilar(action.word)
        is WordAction.SelectEtymology -> WordEvent.SelectEtymology(action.index)
        is WordAction.SelectPos -> WordEvent.SelectPos(action.index)
        WordAction.OnExpandSense -> WordEvent.OnExpandSense
    }

    override suspend fun handleEvent(event: WordEvent): WordEffect? {
        return when (event) {
            is WordEvent.OpenWord -> WordEffect.OpenWord(event.word)
            else -> null
        }
    }

    private suspend fun updateFavourite(word: WordCombinedUi): WordEvent {
        updateFavouriteUseCase(UpdateFavouriteUseCase.Param(word.word, word.isFavorite))
        return WordEvent.UpdateWord(word.copy(isFavorite = !word.isFavorite))
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