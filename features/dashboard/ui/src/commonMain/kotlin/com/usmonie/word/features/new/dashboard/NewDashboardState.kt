package com.usmonie.word.features.new.dashboard

import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.models.WordCombinedUi
import com.usmonie.word.features.models.WordUi
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

internal sealed class NewDashboardState : ScreenState {

    data class Success(
        val hasFocus: Boolean = false,
        val query: TextFieldValue = TextFieldValue(),
        val wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>> = ContentState.Loading(),
        val randomWord: ContentState<Pair<WordUi, WordCombinedUi>> = ContentState.Loading(),
        val foundWords: ContentState<List<WordCombinedUi>> = ContentState.Loading(),
        val recentSearch: List<WordCombinedUi> = listOf(),
        val learnedWordsStatus: LearningStatus,
        val practiceWordsStatus: LearningStatus,
        val newWordsStatus: LearningStatus,
        val streakDaysStatus: LearningStatus
    ) : NewDashboardState() {

        val showIdleItems: Boolean
            get() = query.text.isBlank()
    }

    class Loading : NewDashboardState()

    class Error : NewDashboardState()
}

internal sealed class NewDashboardAction : ScreenAction {
    data object ClearQuery : NewDashboardAction()
    data object Initial : NewDashboardAction()
    data object Refresh : NewDashboardAction()
    data class NextRandomWord(val state: NewDashboardState.Success) : NewDashboardAction()
    data class UpdateFavorite(val word: WordCombinedUi) : NewDashboardAction()
    data class InputQuery(val query: TextFieldValue) : NewDashboardAction()
    data class QueryFieldFocusChange(val isFocus: Boolean) : NewDashboardAction()
    data class OpenWord(val word: WordCombinedUi) : NewDashboardAction()

    sealed class OnMenuItemClicked: NewDashboardAction() {
        data object Favorites: OnMenuItemClicked()
        data object Games: OnMenuItemClicked()
        data object Settings: OnMenuItemClicked()
        data object About: OnMenuItemClicked()
    }
}

internal sealed class NewDashboardEvent : ScreenEvent {
    data object BackToMain : NewDashboardEvent()
    data object ContentLoading : NewDashboardEvent()
    data class Content(
        val recentSearch: List<WordCombinedUi>,
        val wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>>,
        val randomWord: ContentState<Pair<WordUi, WordCombinedUi>>,
        val foundWords: ContentState<List<WordCombinedUi>>,
        val learnedWordsStatus: String,
        val practiceWordsStatus: String,
        val newWordsStatus: String,
        val streakDaysStatus: String,
        val query: TextFieldValue,
    ) : NewDashboardEvent()

    data class InputQuery(val query: TextFieldValue) : NewDashboardEvent()
    data class QueryFocusChanged(val hasFocus: Boolean) : NewDashboardEvent()
    data class OpenWord(val word: WordCombinedUi) : NewDashboardEvent()


    sealed class MenuItemOpen: NewDashboardEvent() {
        data object Favorites: MenuItemOpen()
        data object Games: MenuItemOpen()
        data object Settings: MenuItemOpen()
        data object About: MenuItemOpen()
    }
}

internal sealed class NewDashboardEffect : ScreenEffect {
    data class OpenWord(val word: WordCombinedUi): NewDashboardEffect()

    class OpenFavorites: NewDashboardEffect()
    class OpenGames: NewDashboardEffect()
    class OpenSettings: NewDashboardEffect()
    class OpenAbout: NewDashboardEffect()
}
