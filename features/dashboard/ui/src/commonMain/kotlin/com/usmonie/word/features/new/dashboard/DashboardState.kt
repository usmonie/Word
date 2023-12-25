package com.usmonie.word.features.new.dashboard

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.new.models.WordUi
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState
import wtf.word.core.domain.tools.fastMap

@Stable
data class DashboardState(
    val query: TextFieldValue = TextFieldValue(),
    val wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>> = ContentState.Loading(),
    val foundWords: ContentState<List<WordCombinedUi>> = ContentState.Loading(),
    val recentSearch: List<WordCombinedUi> = listOf(),
    val showWordOfTheDay: Boolean = true,
    val showSettings: Boolean = false,
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

        val newFoundWords: ContentState<List<WordCombinedUi>> = when (foundWords) {
            is ContentState.Error<*, *> -> foundWords
            is ContentState.Loading -> foundWords
            is ContentState.Success -> {
                println(updatedWord)
                val found: ContentState<List<WordCombinedUi>> = ContentState.Success(
                    foundWords.data.fastMap { mappedWord ->
                        mapNewWord(mappedWord, updatedWord)
                    }
                )

                found
            }
        }

        val newRecentSearch = recentSearch.fastMap { mappedWord ->
            mapNewWord(mappedWord, updatedWord)
        }

        return copy(
            wordOfTheDay = newWordOfTheDay,
            foundWords = newFoundWords,
            recentSearch = newRecentSearch,
        )
    }

    fun loadNextRecent(next: List<WordCombinedUi>) =
        this.copy(recentSearch = ArrayList(recentSearch + next))

    fun loadNextFoundWords(next: List<WordCombinedUi>) = this.copy(
        foundWords = foundWords.let {
            if (it is ContentState.Success) {
                ContentState.Success(ArrayList(it.data + next))
            } else {
                foundWords
            }
        }
    )

    fun openSettings() = this.copy(
        showSettings = !showSettings,
        showAbout = false,
        showGames = false
    )

    fun openAbout() = this.copy(
        showSettings = false,
        showAbout = !showAbout,
        showGames = false
    )

    fun openWordOfTheDay() = this.copy(
        showWordOfTheDay = true,
        showAbout = false,
        showGames = false,
        showSettings = false,
    )

    fun openGames() = this.copy(showSettings = false, showGames = !showGames, showAbout = false)

    private fun mapNewWord(
        mappedWord: WordCombinedUi,
        updatedWord: WordCombinedUi
    ) = if (mappedWord.word == updatedWord.word) updatedWord else mappedWord
}

sealed class DashboardAction : ScreenAction {
    data object BackToMain : DashboardAction()
    data object Initial : DashboardAction()
    data object ClearRecentHistory : DashboardAction()
    data object UpdateRandomWord : DashboardAction()
    data object Update : DashboardAction()

    data class UpdateFavourite(val word: WordCombinedUi) : DashboardAction()
    data class InputQuery(val query: TextFieldValue) : DashboardAction()
    data class OpenWord(val word: WordCombinedUi) : DashboardAction()

    sealed class NextItems : DashboardAction() {
        data object FoundWords : NextItems()
        data object RecentSearch : NextItems()
    }

    sealed class OnMenuItemClick : DashboardAction() {
        data object WordOfTheDay : OnMenuItemClick()
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
        val wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>>
    ) : DashboardEvent()

    data class InputQuery(val query: TextFieldValue) : DashboardEvent()
    data class FoundWords(val query: TextFieldValue, val foundWords: List<WordCombinedUi>) :
        DashboardEvent()

    data class UpdatedFavourites(val word: WordCombinedUi) : DashboardEvent()
    data class OpenWord(val word: WordCombinedUi) : DashboardEvent()

    sealed class NextItemsLoaded : DashboardEvent() {
        data class FoundWord(val newWords: List<WordCombinedUi>) : NextItemsLoaded()
        data class RecentSearch(val newWords: List<WordCombinedUi>) : NextItemsLoaded()
    }

    sealed class UpdateMenuItemState : DashboardEvent() {
        data object WordOfTheDay : UpdateMenuItemState()
        data object Favourites : UpdateMenuItemState()
        data object About : UpdateMenuItemState()
        data object Telegram : UpdateMenuItemState()
        data object Settings : UpdateMenuItemState()
        data object Games : UpdateMenuItemState()
    }

    sealed class OpenGame : DashboardEvent() {
        data class Hangman(val word: WordCombinedUi) : OpenGame()

    }
}

sealed class DashboardEffect : ScreenEffect {
    class OpenFavourites : DashboardEffect()
    class OpenSettings : DashboardEffect()

    data class OpenHangman(val word: WordCombinedUi) : DashboardEffect()

    data class OpenWord(val word: WordCombinedUi) : DashboardEffect()

    class OpenUrl(val url: String) : DashboardEffect()
}
