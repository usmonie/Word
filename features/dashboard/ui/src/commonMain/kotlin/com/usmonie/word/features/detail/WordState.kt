package com.usmonie.word.features.detail

import com.usmonie.word.features.models.WordUi
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

data class WordState(
    val word: WordUi,
    val similarWords: ContentState<List<WordUi>> = ContentState.Loading()
): ScreenState

sealed class WordAction: ScreenAction {
    data class OpenWord(val word: WordUi): WordAction()
    data class UpdateFavourite(val word: WordUi): WordAction()
    data class Initial(val word: WordUi): WordAction()
}

sealed class WordEvent: ScreenEvent {
    data class UpdateWord(val word: WordUi): WordEvent()
    data class SimilarWords(val words: List<WordUi>): WordEvent()

    data class OpenWord(val word: WordUi): WordEvent()
}

sealed class WordEffect: ScreenEffect {
    data class OpenWord(val word: WordUi): WordEffect()
}