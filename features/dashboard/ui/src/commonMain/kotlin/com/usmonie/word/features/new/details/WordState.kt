package com.usmonie.word.features.new.details

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.new.models.WordCombinedUi
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

@OptIn(ExperimentalMaterial3Api::class)
@Stable
@Immutable
data class WordState(
    val word: WordCombinedUi,
    val selectedEtymologyIndex: Int = 0,
    val selectedPosIndex: Int = 0,
    val sensesExpanded: Boolean = false,
    val similarWords: ContentState<List<WordCombinedUi>> = ContentState.Loading(),
    val listState: LazyListState,
    val appBarState: TopAppBarState
): ScreenState

sealed class WordAction: ScreenAction {
    data class OpenWord(val word: WordCombinedUi): WordAction()
    data class UpdateFavourite(val word: WordCombinedUi): WordAction()
    data class Initial(val word: WordCombinedUi): WordAction()

    data class SelectEtymology(val index: Int) : WordAction()
    data class SelectPos(val index: Int) : WordAction()
}
sealed class WordEvent: ScreenEvent {
    data class UpdateWord(val word: WordCombinedUi): WordEvent()
    data class SimilarWords(val words: List<WordCombinedUi>): WordEvent()
    data class OpenWord(val word: WordCombinedUi): WordEvent()

    data class SelectEtymology(val index: Int) : WordEvent()
    data class SelectPos(val index: Int) : WordEvent()
}

sealed class WordEffect: ScreenEffect {
    data class OpenWord(val word: WordCombinedUi): WordEffect()
}