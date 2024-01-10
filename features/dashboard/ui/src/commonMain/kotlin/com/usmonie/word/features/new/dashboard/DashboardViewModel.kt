package com.usmonie.word.features.new.dashboard

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.analytics.DashboardAnalyticsEvents
import com.usmonie.word.features.dashboard.domain.usecase.ClearRecentUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetSearchHistoryUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetWordOfTheDayUseCase
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SearchWordsUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.new.models.toDomain
import com.usmonie.word.features.new.models.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wtf.speech.core.ui.BaseViewModel
import wtf.speech.core.ui.ConnectionErrorState
import wtf.speech.core.ui.ContentState
import wtf.word.core.domain.Analytics
import wtf.word.core.domain.tools.fastMap

@Immutable
class DashboardViewModel(
    private val searchWordsUseCase: SearchWordsUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val getWordOfTheDayUseCase: GetWordOfTheDayUseCase,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getRandomWordUseCase: RandomWordUseCase,
    private val clearRecentUseCase: ClearRecentUseCase,
    private val analytics: Analytics,
) : BaseViewModel<DashboardState, DashboardAction, DashboardEvent, DashboardEffect>(DashboardState()) {

    private var searchJob: Job? = null

    init {
        handleAction(DashboardAction.Initial)
    }

    override fun DashboardState.reduce(event: DashboardEvent) = when (event) {
        is DashboardEvent.RandomWordLoading -> this.copy(randomWord = ContentState.Loading())
        is DashboardEvent.UpdatedFavourites -> this.updateFavourite(event.word)
        is DashboardEvent.FoundWords -> this.copy(
            query = event.query,
            foundWords = ContentState.Success(event.foundWords)
        )

        is DashboardEvent.InputQuery -> {
            if (event.query.text == this.query.text) this
            else this.copy(
                query = event.query,
                foundWords = ContentState.Loading()
            )
        }

        is DashboardEvent.InitialData -> copy(
            wordOfTheDay = event.wordOfTheDay,
            recentSearch = event.recentSearch,
            randomWord = event.randomWord,
            showRandomWord = event.wordOfTheDay is ContentState.Error<*, *>
        )

        DashboardEvent.UpdateMenuItemState.About -> this.openAbout()
        DashboardEvent.UpdateMenuItemState.RandomWord -> this.openRandomWord()
        DashboardEvent.UpdateMenuItemState.Games -> this.openGames()
        DashboardEvent.BackToMain -> this.copy(query = TextFieldValue())
        else -> this
    }

    override suspend fun processAction(action: DashboardAction) = when (action) {
        is DashboardAction.InputQuery -> search(action.query)

        DashboardAction.OnMenuItemClick.RandomWord -> {
            analytics.log(DashboardAnalyticsEvents.OpenFavourites)
            DashboardEvent.UpdateMenuItemState.RandomWord
        }

        DashboardAction.OnMenuItemClick.Favourites -> {
            analytics.log(DashboardAnalyticsEvents.OpenFavourites)
            DashboardEvent.UpdateMenuItemState.Favourites
        }

        DashboardAction.OnMenuItemClick.Settings -> {
            analytics.log(DashboardAnalyticsEvents.OpenSettings)

            DashboardEvent.UpdateMenuItemState.Settings
        }

        is DashboardAction.OpenWord -> {
            analytics.log(DashboardAnalyticsEvents.OpenWord(action.word))

            DashboardEvent.OpenWord(action.word)
        }

        is DashboardAction.UpdateFavourite -> updateFavourite(action)
        DashboardAction.Initial -> initialData()
        DashboardAction.ClearRecentHistory -> clearRecentHistory()
        DashboardAction.BackToMain -> DashboardEvent.BackToMain
        DashboardAction.Refresh -> {
            updateData()
            DashboardEvent.RandomWordLoading
        }

        DashboardAction.UpdateRandomWord -> initialData()
        DashboardAction.OnGamesItemClick.Hangman -> DashboardEvent.OpenGame.Hangman
        DashboardAction.OnMenuItemClick.Games -> DashboardEvent.UpdateMenuItemState.Games
        DashboardAction.OnMenuItemClick.About -> DashboardEvent.UpdateMenuItemState.About
        DashboardAction.OnMenuItemClick.Telegram -> DashboardEvent.UpdateMenuItemState.Telegram
    }

    override suspend fun handleEvent(event: DashboardEvent) = when (event) {
        is DashboardEvent.UpdateMenuItemState.Favourites -> DashboardEffect.OpenFavourites()
        is DashboardEvent.UpdateMenuItemState.Settings -> DashboardEffect.OpenSettings()
        is DashboardEvent.OpenWord -> DashboardEffect.OpenWord(event.word)
        is DashboardEvent.OpenGame.Hangman -> DashboardEffect.OpenHangman()
        is DashboardEvent.UpdateMenuItemState.Telegram ->
            DashboardEffect.OpenUrl("https://t.me/nieabout")

        else -> null
    }

    private suspend fun clearRecentHistory(): DashboardEvent {
        clearRecentUseCase(Unit)
        updateData()
        return DashboardEvent.RandomWordLoading
    }

    private suspend fun search(query: TextFieldValue, offset: Long = 0): DashboardEvent {
        if (query.text == state.value.query.text && query.text.isNotBlank()) {
            return DashboardEvent.InputQuery(query)
        }

        searchJob?.cancel()
        if (query.text.isBlank()) {
            updateData()
            return DashboardEvent.InputQuery(query)
        }

        launchSearch(query, offset)
        return DashboardEvent.InputQuery(query)
    }

    private suspend fun launchSearch(
        query: TextFieldValue,
        offset: Long,
        limit: Long = DEFAULT_LIMIT,
        exactly: Boolean = false
    ) {
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            val found = searchWordsUseCase(
                SearchWordsUseCase.Param(query.text.trim(), offset, limit, exactly)
            ).fastMap {
                ensureActive()
                it.toUi()
            }

            withContext(Dispatchers.Main) {
                analytics.log(DashboardAnalyticsEvents.Search(query.text))
                ensureActive()
                handleState(DashboardEvent.FoundWords(query, found))
            }
        }
    }

    private suspend fun updateFavourite(
        action: DashboardAction.UpdateFavourite
    ): DashboardEvent.UpdatedFavourites {
        updateFavouriteUseCase(UpdateFavouriteUseCase.Param(action.word.toDomain()))

        return DashboardEvent.UpdatedFavourites(
            action.word.copy(isFavorite = !action.word.isFavorite),
        )
    }

    private suspend fun initialData(): DashboardEvent {
        loadData(0)
        return DashboardEvent.RandomWordLoading
    }

    private suspend fun updateData() = loadData(0, true)

    private suspend fun loadData(offset: Long, update: Boolean = false): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            val recentSearch = getSearchHistoryUseCase(
                GetSearchHistoryUseCase.Param(
                    offset,
                    START_LIMIT
                )
            ).fastMap { it.toUi() }

            val wordOfTheDay = if (update) {
                state.value.wordOfTheDay
            } else {
                try {
                    val word = getWordOfTheDayUseCase(GetWordOfTheDayUseCase.Param).toUi()
                    ContentState.Success(Pair(word.wordEtymology.random().words.random(), word))
                } catch (e: Exception) {
                    e.printStackTrace()
                    ContentState.Error(ConnectionErrorState(e))
                }
            }

            val randomWord = if (update) {
                state.value.wordOfTheDay
            } else {
                try {
                    val word = getRandomWordUseCase(RandomWordUseCase.Param(30)).toUi()
                    ContentState.Success(Pair(word.wordEtymology.random().words.random(), word))
                } catch (e: Exception) {
                    e.printStackTrace()
                    ContentState.Error(ConnectionErrorState(e))
                }
            }

            handleState(
                DashboardEvent.InitialData(
                    recentSearch = recentSearch,
                    wordOfTheDay = wordOfTheDay,
                    randomWord = randomWord
                )
            )
        }
    }

    fun onRandomWordMenuClick() = handleAction(DashboardAction.OnMenuItemClick.RandomWord)
    fun onOpenWord(word: WordCombinedUi) = handleAction(DashboardAction.OpenWord(word))
    fun onUpdateFavouritesPressed(word: WordCombinedUi) =
        handleAction(DashboardAction.UpdateFavourite(word))

    fun onUpdateRandomCard() = handleAction(DashboardAction.UpdateRandomWord)
    fun onGamesClicked() = handleAction(DashboardAction.OnMenuItemClick.Games)
    fun onHangman() = handleAction(DashboardAction.OnGamesItemClick.Hangman)
    fun onQueryChanged(query: TextFieldValue) = handleAction(DashboardAction.InputQuery(query))
    fun onFavouritesItemClicked() = handleAction(DashboardAction.OnMenuItemClick.Favourites)
    fun onSettingsItemClicked() = handleAction(DashboardAction.OnMenuItemClick.Settings)
    fun onAboutItemClicked() = handleAction(DashboardAction.OnMenuItemClick.About)
    fun onTelegramItemClicked() = handleAction(DashboardAction.OnMenuItemClick.Telegram)
    fun onBackClick() = handleAction(DashboardAction.BackToMain)


    companion object {
        const val DEFAULT_LIMIT = 20L
        const val START_LIMIT = 20L
    }
}
