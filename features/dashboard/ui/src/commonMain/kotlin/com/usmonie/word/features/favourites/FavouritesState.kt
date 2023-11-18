package com.usmonie.word.features.favourites

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.models.WordUi
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

sealed class FavouritesState : ScreenState {

    class Empty : FavouritesState()
    class Loading : FavouritesState()

    @Stable
    @Immutable
    data class Items(val favourites: List<WordUi>) : FavouritesState() {

        fun updateFavourite(updatedWord: WordUi): FavouritesState {
            val newWords = favourites.map { mappedWord ->
                mapNewWord(mappedWord, updatedWord)
            }
            return copy(favourites = newWords)
        }


        private fun mapNewWord(mappedWord: WordUi, updatedWord: WordUi) =
            if (mappedWord.id == updatedWord.id) {
                updatedWord
            } else {
                mappedWord
            }
    }
}

sealed class FavouritesAction : ScreenAction {
    data class OpenWord(val word: WordUi) : FavouritesAction()
    data class UpdateFavouriteWord(val word: WordUi) : FavouritesAction()
    data object Initial : FavouritesAction()

    data object OnBack : FavouritesAction()
}

sealed class FavouritesEvent : ScreenEvent {
    data object Back : FavouritesEvent()
    data object Loading : FavouritesEvent()
    data class UpdatedWord(val wordUi: WordUi) : FavouritesEvent()
    data class OpenWord(val wordUi: WordUi) : FavouritesEvent()

    data class Updated(val favourites: List<WordUi>) : FavouritesEvent()

    data class Next(val favourites: List<WordUi>, val offset: Int) : FavouritesEvent()
}

sealed class FavouritesEffect : ScreenEffect {
    class OnBack : FavouritesEffect()

    data class OpenWord(val wordUi: WordUi): FavouritesEffect()
}

