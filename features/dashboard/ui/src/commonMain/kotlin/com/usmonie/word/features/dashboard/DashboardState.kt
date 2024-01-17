package com.usmonie.word.features.dashboard

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.models.WordCombinedUi
import com.usmonie.word.features.models.WordUi
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState
import wtf.word.core.domain.tools.fastMap

@Immutable
data class DashboardState(
    val query: TextFieldValue = TextFieldValue(),
    val wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>> = ContentState.Loading(),
    val randomWord: ContentState<Pair<WordUi, WordCombinedUi>> = ContentState.Loading(),
    val foundWords: ContentState<List<WordCombinedUi>> = ContentState.Loading(),
    val recentSearch: WordsState = WordsState(listOf()),
    val showRandomWord: Boolean = wordOfTheDay is ContentState.Error<*, *>,
    val showGames: Boolean = false,
    val showAbout: Boolean = false,
) : ScreenState {
    fun updateFavourite(updatedWord: WordCombinedUi): DashboardState {
        val newWordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>> = when (wordOfTheDay) {
            is ContentState.Error<*, *> -> wordOfTheDay
            is ContentState.Loading -> wordOfTheDay
            is ContentState.Success -> if (updatedWord.word == wordOfTheDay.data.first.word) {
                ContentState.Success(Pair(wordOfTheDay.data.first, updatedWord))
            } else {
                wordOfTheDay
            }
        }

        val newRandomWord: ContentState<Pair<WordUi, WordCombinedUi>> = when (randomWord) {
            is ContentState.Error<*, *> -> randomWord
            is ContentState.Loading -> randomWord
            is ContentState.Success -> if (updatedWord.word == randomWord.data.first.word) {
                ContentState.Success(Pair(randomWord.data.first, updatedWord))
            } else {
                randomWord
            }
        }

        val newFoundWords: ContentState<List<WordCombinedUi>> = when (foundWords) {
            is ContentState.Error<*, *> -> foundWords
            is ContentState.Loading -> foundWords
            is ContentState.Success -> {
                val found: ContentState<List<WordCombinedUi>> = ContentState.Success(
                    foundWords.data.fastMap { mappedWord ->
                        mapNewWord(mappedWord, updatedWord)
                    }
                )

                found
            }
        }

        val newRecentSearch = recentSearch.words.fastMap { mappedWord ->
            mapNewWord(mappedWord, updatedWord)
        }

        return copy(
            wordOfTheDay = newWordOfTheDay,
            foundWords = newFoundWords,
            randomWord = newRandomWord,
            recentSearch = WordsState(newRecentSearch),
        )
    }

    fun openAbout() = this.copy(
        showRandomWord = wordOfTheDay is ContentState.Error<*, *> && showAbout,
        showAbout = !showAbout,
        showGames = false
    )

    fun openRandomWord() = this.copy(
        showRandomWord = true,
        showAbout = false,
        showGames = false,
    )

    fun openGames() = this.copy(
        showRandomWord = wordOfTheDay is ContentState.Error<*, *> && showGames,
        showGames = !showGames,
        showAbout = false
    )

    private fun mapNewWord(
        mappedWord: WordCombinedUi,
        updatedWord: WordCombinedUi
    ) = if (mappedWord.word == updatedWord.word) updatedWord else mappedWord

    override fun toString(): String {
        return "DashboardState(query=$query, showRandomWord=$showRandomWord, showGames=$showGames, showAbout=$showAbout)"
    }
}

sealed class DashboardAction : ScreenAction {
    data object BackToMain : DashboardAction()
    data object Initial : DashboardAction()
    data object ClearRecentHistory : DashboardAction()
    data object UpdateRandomWord : DashboardAction()
    data object Refresh : DashboardAction()
    data class UpdateFavourite(val word: WordCombinedUi) : DashboardAction()
    data class InputQuery(val query: TextFieldValue) : DashboardAction()
    data class OpenWord(val word: WordCombinedUi) : DashboardAction()

    sealed class OnMenuItemClick : DashboardAction() {
        data object RandomWord : OnMenuItemClick()
        data object Favourites : OnMenuItemClick()
        data object Settings : OnMenuItemClick()
        data object About : OnMenuItemClick()
        data object Telegram : OnMenuItemClick()
        data object Games : OnMenuItemClick()
    }

    sealed class OnGamesItemClick : DashboardAction() {
        data object Hangman : OnMenuItemClick()
    }
}

sealed class DashboardEvent : ScreenEvent {
    data object BackToMain : DashboardEvent()
    data object RandomWordLoading : DashboardEvent()

    data class InitialData(
        val recentSearch: List<WordCombinedUi>,
        val wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>>,
        val randomWord: ContentState<Pair<WordUi, WordCombinedUi>>
    ) : DashboardEvent()

    data class InputQuery(val query: TextFieldValue) : DashboardEvent()
    data class FoundWords(val query: TextFieldValue, val foundWords: List<WordCombinedUi>) :
        DashboardEvent()

    data class UpdatedFavourites(val word: WordCombinedUi) : DashboardEvent()
    data class OpenWord(val word: WordCombinedUi) : DashboardEvent()

    sealed class UpdateMenuItemState : DashboardEvent() {
        data object RandomWord : UpdateMenuItemState()
        data object Favourites : UpdateMenuItemState()
        data object About : UpdateMenuItemState()
        data object Telegram : UpdateMenuItemState()
        data object Settings : UpdateMenuItemState()
        data object Games : UpdateMenuItemState()
    }

    sealed class OpenGame : DashboardEvent() {
        data object Hangman : OpenGame()
    }
}

sealed class DashboardEffect : ScreenEffect {
    class OpenFavourites : DashboardEffect()
    class OpenSettings : DashboardEffect()

    class OpenHangman : DashboardEffect()

    data class OpenWord(val word: WordCombinedUi) : DashboardEffect()

    class OpenUrl(val url: String) : DashboardEffect()
}
