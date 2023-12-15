package com.usmonie.word.features.new.details

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.new.models.WordCombinedUi
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

@Stable
@Immutable
data class WordState(
    val word: WordCombinedUi,
    val similarWords: ContentState<List<WordCombinedUi>> = ContentState.Loading()
): ScreenState

sealed class WordAction: ScreenAction {
    data class OpenWord(val word: WordCombinedUi): WordAction()
    data class UpdateFavourite(val word: WordCombinedUi): WordAction()
    data class Initial(val word: WordCombinedUi): WordAction()
}
sealed class WordEvent: ScreenEvent {
    data class UpdateWord(val word: WordCombinedUi): WordEvent()
    data class SimilarWords(val words: List<WordCombinedUi>): WordEvent()
    data class OpenWord(val word: WordCombinedUi): WordEvent()
}

sealed class WordEffect: ScreenEffect {
    data class OpenWord(val word: WordCombinedUi): WordEffect()
}