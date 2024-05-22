package com.usmonie.word.features.favorites.ui

import com.usmonie.compass.viewmodel.ContentState
import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi

data class FavoritesState(val words: ContentState<List<WordCombinedUi>>) : ScreenState

sealed class FavoritesAction : ScreenAction {
    data class LoadedWords(val words: List<WordCombinedUi>) : FavoritesAction()
    data class OpenWord(val wordCombined: WordCombinedUi) : FavoritesAction()
    data class FavoriteWord(val wordCombined: WordCombinedUi) : FavoritesAction()
}

sealed class FavoritesEvent : ScreenEvent {
    data class LoadedWords(val words: List<WordCombinedUi>) : FavoritesEvent()
    data class OpenWord(val wordCombined: WordCombinedUi) : FavoritesEvent()
    data class FavoriteWord(val wordCombined: WordCombinedUi) : FavoritesEvent()
}

sealed class FavoritesEffect : ScreenEffect {
    data class OpenWord(val wordCombined: WordCombinedUi): FavoritesEffect()
}
