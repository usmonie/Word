package com.usmonie.word.features.dashboard.ui.dashboard

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.dashboard.ui.models.LearningStatus
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.models.WordUi
import com.usmonie.word.features.dashboard.ui.ui.subscription.SubscriptionSaleState
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState

internal sealed class DashboardState() : ScreenState {
    open val subscriptionSaleState: SubscriptionSaleState? = null
    open val showSubscriptionAd: Boolean = false

    @Immutable
    data class Success(
        override val subscriptionSaleState: SubscriptionSaleState? = null,
        override val showSubscriptionAd: Boolean = true,
        val hasFocus: Boolean = false,
        val query: TextFieldValue = TextFieldValue(),
        val wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>> = ContentState.Loading(),
        val randomWord: ContentState<Pair<WordUi, WordCombinedUi>> = ContentState.Loading(),
        val foundWords: ContentState<List<WordCombinedUi>> = ContentState.Loading(),
        val recentSearch: List<WordCombinedUi> = listOf(),
        val learnedWordsStatus: LearningStatus,
        val practiceWordsStatus: LearningStatus,
        val newWordsStatus: LearningStatus,
        val streakDaysStatus: LearningStatus,
        val subscriptionStatus: SubscriptionStatus,
    ) : DashboardState()

    class Loading : DashboardState()
    class Error : DashboardState()
}

internal sealed class DashboardAction : ScreenAction {
    data object ExpandSubscriptionAd: DashboardAction()
    data object CollapseSubscriptionAd: DashboardAction()
    data object ClearQuery : DashboardAction()
    data object Initial : DashboardAction()
    data object Refresh : DashboardAction()
    data class NextRandomWord(val state: DashboardState.Success) : DashboardAction()
    data class UpdateFavorite(val word: WordCombinedUi) : DashboardAction()
    data class InputQuery(val query: TextFieldValue) : DashboardAction()
    data class QueryFieldFocusChange(val isFocus: Boolean) : DashboardAction()
    data class OpenWord(val word: WordCombinedUi) : DashboardAction()
    data class SubscriptionStatusUpdated(val subscriptionStatus: SubscriptionStatus) :
        DashboardAction()

    sealed class OnMenuItemClicked : DashboardAction() {
        data object Favorites : OnMenuItemClicked()
        data object Games : OnMenuItemClicked()
        data object Settings : OnMenuItemClicked()
        data object About : OnMenuItemClicked()
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
        val subscriptionStatus: SubscriptionStatus
    ) : DashboardEvent()

    data class InputQuery(val query: TextFieldValue) : DashboardEvent()
    data class QueryFocusChanged(val hasFocus: Boolean) : DashboardEvent()
    data class OpenWord(val word: WordCombinedUi) : DashboardEvent()
    data class SubscriptionUpdated(val status: SubscriptionStatus) : DashboardEvent()

    sealed class MenuItemOpen : DashboardEvent() {
        data object Favorites : MenuItemOpen()
        data object Games : MenuItemOpen()
        data object Settings : MenuItemOpen()
        data object About : MenuItemOpen()
    }
}

internal sealed class DashboardEffect : ScreenEffect {
    data class OpenWord(val word: WordCombinedUi) : DashboardEffect()

    class OpenFavorites : DashboardEffect()
    class OpenGames : DashboardEffect()
    class OpenSettings : DashboardEffect()
    class OpenAbout : DashboardEffect()
}
