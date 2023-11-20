package com.usmonie.word.features.dashboard.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.models.WordUi
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.WordTypography

@Stable
@Immutable
data class DashboardState(
    val query: TextFieldValue = TextFieldValue(),
    val wordOfTheDay: ContentState<WordUi> = ContentState.Loading(),
    val foundWords: ContentState<List<WordUi>> = ContentState.Loading(),
    val recentSearch: List<WordUi> = listOf(),
    val showWordOfTheDay: Boolean = true,
    val showSettings: Boolean = false,
    val showGames: Boolean = false,
    val subscribed: Boolean = false
) : ScreenState {

    fun updateFavourite(updatedWord: WordUi): DashboardState {
        val newWordOfTheDay = if (updatedWord.id == wordOfTheDay.item?.id) {
            ContentState.Success(updatedWord)
        } else {
            wordOfTheDay
        }

        val newFoundWords = foundWords.apply {
            item?.map { mappedWord ->
                mapNewWord(mappedWord, updatedWord)
            }
        }

        val newRecentSearch = recentSearch.map { mappedWord ->
            mapNewWord(mappedWord, updatedWord)
        }

        return copy(
            wordOfTheDay = newWordOfTheDay,
            foundWords = newFoundWords,
            recentSearch = newRecentSearch,
        )
    }

    fun loadNextRecent(next: List<WordUi>) =
        this.copy(recentSearch = ArrayList(recentSearch + next))

    fun loadNextFoundWords(next: List<WordUi>) = this.copy(
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
        showGames = false
    )

    fun openWordOfTheDay() = this.copy(
        showWordOfTheDay = true,
        showSettings = false,
    )

    fun openGames() = this.copy(showSettings = false, showGames = !showGames)

    private fun mapNewWord(
        mappedWord: WordUi,
        updatedWord: WordUi
    ) = if (mappedWord.id == updatedWord.id) {
        updatedWord
    } else {
        mappedWord
    }
}

sealed class DashboardAction : ScreenAction {
    data object BackToMain : DashboardAction()
    data object Initial : DashboardAction()
    data object ChangeColors : DashboardAction()
    data object ChangeFonts : DashboardAction()
    data object ClearRecentHistory : DashboardAction()
    data object Update : DashboardAction()
    data class UpdateFavourite(val word: WordUi) : DashboardAction()
    data class InputQuery(val query: TextFieldValue) : DashboardAction()
    data class OpenWord(val word: WordUi) : DashboardAction()

    sealed class NextItems : DashboardAction() {
        data object FoundWords : NextItems()
        data object RecentSearch : NextItems()
    }

    sealed class OnMenuItemClick : DashboardAction() {
        data object WordOfTheDay : OnMenuItemClick()
        data object Favourites : OnMenuItemClick()
        data object Settings : OnMenuItemClick()
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
        val recentSearch: List<WordUi>,
        val wordOfTheDay: ContentState<WordUi>,
        val colors: WordColors,
        val typography: WordTypography
    ) : DashboardEvent()

    data class UpdateData(
        val recentSearch: List<WordUi>,
        val wordOfTheDay: ContentState<WordUi>,
    ) : DashboardEvent()

    data class ChangeTheme(
        val colors: WordColors,
        val typography: WordTypography
    ) : DashboardEvent()

    data class InputQuery(val query: TextFieldValue) : DashboardEvent()
    data class FoundWords(val query: TextFieldValue, val foundWords: List<WordUi>) :
        DashboardEvent()

    data class UpdatedFavourites(val word: WordUi) : DashboardEvent()
    data class OpenWord(val word: WordUi) : DashboardEvent()

    sealed class NextItemsLoaded : DashboardEvent() {
        data class FoundWord(val newWords: List<WordUi>) : NextItemsLoaded()
        data class RecentSearch(val newWords: List<WordUi>) : NextItemsLoaded()
    }

    sealed class UpdateMenuItemState : DashboardEvent() {
        data object WordOfTheDay : UpdateMenuItemState()
        data object Favourites : UpdateMenuItemState()
        data object Settings : UpdateMenuItemState()
        data object Games : UpdateMenuItemState()
    }

    sealed class OpenGame : DashboardEvent() {
        data class Hangman(val word: WordUi) : OpenGame()

    }
}

sealed class DashboardEffect : ScreenEffect {
    data class ChangeTheme(val wordColors: WordColors, val wordTypography: WordTypography) :
        DashboardEffect()

    class OpenFavourites : DashboardEffect()

    data class OpenHangman(val word: WordUi): DashboardEffect()

    data class OpenWord(val word: WordUi) : DashboardEffect()
}
