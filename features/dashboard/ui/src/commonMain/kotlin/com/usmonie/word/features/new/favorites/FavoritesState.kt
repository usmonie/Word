package com.usmonie.word.features.new.favorites

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.new.models.WordCombinedUi
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState
import wtf.word.core.domain.tools.fastMap

sealed class FavoritesState : ScreenState {

    class Empty : FavoritesState()

    class Loading : FavoritesState()

    @Stable
    @Immutable
    data class Items(val favourites: List<WordCombinedUi>) : FavoritesState() {

        fun updateFavourite(updatedWord: WordCombinedUi): FavoritesState {
            val newWords = favourites.fastMap { mappedWord ->
                mapNewWord(mappedWord, updatedWord)
            }
            return copy(favourites = newWords)
        }


        private fun mapNewWord(mappedWord: WordCombinedUi, updatedWord: WordCombinedUi) =
            if (mappedWord.word == updatedWord.word) {
                updatedWord
            } else {
                mappedWord
            }
    }
}

sealed class FavoritesAction : ScreenAction {
    data class OpenWord(val word: WordCombinedUi) : FavoritesAction()
    data class UpdateFavouriteWord(val word: WordCombinedUi) : FavoritesAction()
    data object Initial : FavoritesAction()

    data object OnBack : FavoritesAction()
}

sealed class FavoritesEvent : ScreenEvent {
    data object Back : FavoritesEvent()
    data object Loading : FavoritesEvent()
    data class UpdatedWord(val wordUi: WordCombinedUi) : FavoritesEvent()
    data class OpenWord(val wordUi: WordCombinedUi) : FavoritesEvent()

    data class Updated(val favourites: List<WordCombinedUi>) : FavoritesEvent()

    data class Next(val favourites: List<WordCombinedUi>, val offset: Int) : FavoritesEvent()
}

@Stable
sealed class FavoritesEffect : ScreenEffect {
    class OnBack : FavoritesEffect()

    data class OpenWord(val wordUi: WordCombinedUi): FavoritesEffect()
}

