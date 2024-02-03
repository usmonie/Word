package com.usmonie.word.features.dashboard

import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.models.LearningStatus
import com.usmonie.word.features.models.WordCombinedUi
import com.usmonie.word.features.models.WordUi
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

internal sealed class DashboardState : ScreenState {

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
    ) : DashboardState() {

        val showIdleItems: Boolean
            get() = query.text.isBlank()
    }

    class Loading : DashboardState()

    class Error : DashboardState()
}

internal sealed class DashboardAction : ScreenAction {
    data object ClearQuery : DashboardAction()
    data object Initial : DashboardAction()
    data object Refresh : DashboardAction()
    data class NextRandomWord(val state: DashboardState.Success) : DashboardAction()
    data class UpdateFavorite(val word: WordCombinedUi) : DashboardAction()
    data class InputQuery(val query: TextFieldValue) : DashboardAction()
    data class QueryFieldFocusChange(val isFocus: Boolean) : DashboardAction()
    data class OpenWord(val word: WordCombinedUi) : DashboardAction()

    sealed class OnMenuItemClicked: DashboardAction() {
        data object Favorites: OnMenuItemClicked()
        data object Games: OnMenuItemClicked()
        data object Settings: OnMenuItemClicked()
        data object About: OnMenuItemClicked()
    }
}

internal sealed class DashboardEvent : ScreenEvent {
    data object BackToMain : DashboardEvent()
    data object ContentLoading : DashboardEvent()
    data class Content(
        val recentSearch: List<WordCombinedUi>,
        val wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>>,
        val randomWord: ContentState<Pair<WordUi, WordCombinedUi>>,
        val foundWords: ContentState<List<WordCombinedUi>>,
        val learnedWordsStatus: Int,
        val practiceWordsStatus: Int,
        val newWordsStatus: Int,
        val streakDaysStatus: Int,
        val query: TextFieldValue,
    ) : DashboardEvent()

    data class InputQuery(val query: TextFieldValue) : DashboardEvent()
    data class QueryFocusChanged(val hasFocus: Boolean) : DashboardEvent()
    data class OpenWord(val word: WordCombinedUi) : DashboardEvent()


    sealed class MenuItemOpen: DashboardEvent() {
        data object Favorites: MenuItemOpen()
        data object Games: MenuItemOpen()
        data object Settings: MenuItemOpen()
        data object About: MenuItemOpen()
    }
}

internal sealed class DashboardEffect : ScreenEffect {
    data class OpenWord(val word: WordCombinedUi): DashboardEffect()

    class OpenFavorites: DashboardEffect()
    class OpenGames: DashboardEffect()
    class OpenSettings: DashboardEffect()
    class OpenAbout: DashboardEffect()
}
