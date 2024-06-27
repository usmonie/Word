package com.usmonie.word.features.dashboard.ui.screen

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.compass.viewmodel.ConnectionErrorState
import com.usmonie.compass.viewmodel.ContentState
import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.word.features.games.domain.usecases.GetRandomWordUseCase
import com.usmonie.word.features.games.domain.usecases.GetSearchHistoryUseCase
import com.usmonie.word.features.games.domain.usecases.GetWordOfTheDayUseCase
import com.usmonie.word.features.games.domain.usecases.MoveToNewDatabaseUseCase
import com.usmonie.word.features.games.domain.usecases.SearchWordsUseCase
import com.usmonie.word.features.games.domain.usecases.UpdateFavouriteWordUseCase
import com.usmonie.word.features.games.domain.usecases.UpdateSearchHistory
import com.usmonie.word.features.games.ui.models.WordCombinedUi
import com.usmonie.word.features.games.ui.models.toUi
import com.usmonie.word.features.quotes.domain.models.Quote
import com.usmonie.word.features.quotes.domain.usecases.GetRandomQuoteUseCase
import com.usmonie.word.features.quotes.domain.usecases.InitiateQuotesUseCase
import com.usmonie.word.features.quotes.domain.usecases.UpdateFavoriteQuoteUseCase
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Immutable
@Suppress("TooManyFunctions", "MagicNumber", "LongParameterList")
internal class DashboardViewModel(
    private val searchWordsUseCase: SearchWordsUseCase,
    private val searchHistoryUseCase: GetSearchHistoryUseCase,
    private val getRandomWordUseCase: GetRandomWordUseCase,
    private val updateSearchHistory: UpdateSearchHistory,
    private val wordOfTheDayUseCase: GetWordOfTheDayUseCase,
    private val updateFavouriteWordUseCase: UpdateFavouriteWordUseCase,
    private val moveToNewDatabaseUseCase: MoveToNewDatabaseUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val updateFavouriteQuoteUseCase: UpdateFavoriteQuoteUseCase,
    private val initiateQuotesUseCase: InitiateQuotesUseCase
//    private val analytics: Analytics
) : StateViewModel<DashboardState, DashboardAction, DashboardEvent, DashboardEffect>(
    DashboardState()
) {

    private var searchJob: Job = Job()

    init {
        init()
        viewModelScope.launchSafe {
            initiateQuotesUseCase().collect { initiated ->
                if (initiated && state.value.randomQuote == null) {
                    handleAction(DashboardAction.OnNextRandomQuote)
                }
            }
        }
        viewModelScope.launchSafe {
            moveToNewDatabaseUseCase()
        }
    }

    override fun DashboardState.reduce(event: DashboardEvent) = when (event) {
        DashboardEvent.BackToMain -> copy(searchFieldState = SearchFieldState())
        DashboardEvent.ContentLoading -> copy(randomWord = ContentState.Loading())
        is DashboardEvent.Content -> event.toState()
        is DashboardEvent.Init -> event.toState()

        is DashboardEvent.InputQuery -> copy(
            searchFieldState = searchFieldState.copy(searchFieldValue = event.query)
        )

        is DashboardEvent.QueryFocusChanged -> copy(
            searchFieldState = searchFieldState.copy(hasFocus = event.hasFocus)
        )

        is DashboardEvent.UpdateWord -> copy()
        else -> this
    }

    override suspend fun processAction(action: DashboardAction): DashboardEvent {
        return when (action) {
            DashboardAction.OnBackToMain -> {
//                analytics.log()
                DashboardEvent.BackToMain
            }

            DashboardAction.OnLoadData -> {
                loadData()

                DashboardEvent.ContentLoading
            }

            DashboardAction.OnNextRandomWord -> {
                val randomWord = newRandomWord()
                val state = state.value
                DashboardEvent.Content(
                    state.recentSearch,
                    state.wordOfTheDay,
                    randomWord,
                    state.foundWords,
                    state.subscriptionStatus,
                    state.randomQuote
                )
            }

            is DashboardAction.OnInputQuery -> search(action.query)
            is DashboardAction.OnMenuItemClicked -> DashboardEvent.OnMenuItemClicked(action.menuItem)
            is DashboardAction.OnOpenWord -> DashboardEvent.OpenWord(action.wordCombined)
            is DashboardAction.OnFavoriteWord -> updateFavourite(action.wordCombined, state.value)
            is DashboardAction.OnQueryFieldFocusChange -> DashboardEvent.QueryFocusChanged(action.focused)
            is DashboardAction.OnOpenSearchWord -> {
                updateSearchHistory(UpdateSearchHistory.Param(action.wordCombined.word))
                DashboardEvent.OpenWord(action.wordCombined)
            }

            is DashboardAction.OnFavoriteQuote -> {
                updateFavouriteQuoteUseCase(action.quote)
                state.value.toContentEvent(randomQuote = action.quote.copy(favorite = !action.quote.favorite))
            }
            DashboardAction.OnNextRandomQuote -> {
                state.value.toContentEvent(randomQuote = getRandomQuote())
            }

            DashboardAction.Init -> {
                loadData(true)
                DashboardEvent.ContentLoading
            }
        }
    }

    override suspend fun handleEvent(event: DashboardEvent): DashboardEffect? {
        return when (event) {
            is DashboardEvent.OnMenuItemClicked -> DashboardEffect.OnMenuItemClicked(event.menuItem)
            is DashboardEvent.OpenWord -> DashboardEffect.OpenWord(event.word)
            is DashboardEvent.Init -> DashboardEffect.Init()
            else -> null
        }
    }

    private suspend fun loadData(initial: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val searchHistory = searchHistoryUseCase(
                GetSearchHistoryUseCase.Param(
                    0,
                    START_LIMIT
                )
            ).fastMap { it.toUi() }

            val randomWord = newRandomWord()

            val quote = getRandomQuote()
            handleState(
                if (initial) {
                    DashboardEvent.Init(
                        searchHistory,
                        randomWord = randomWord,
                        foundWords = state.value.foundWords,
                        subscriptionStatus = state.value.subscriptionStatus,
                        randomQuote = quote
                    )
                } else {
                    DashboardEvent.Content(
                        searchHistory,
                        randomWord = randomWord,
                        foundWords = state.value.foundWords,
                        subscriptionStatus = state.value.subscriptionStatus,
                        randomQuote = quote
                    )
                }
            )
        }
    }

    private suspend fun DashboardViewModel.getRandomQuote() = try {
        getRandomQuoteUseCase()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    private suspend fun newRandomWord(): ContentState<WordCombinedUi> {
        val randomWord = try {
            val word = getRandomWordUseCase(GetRandomWordUseCase.Param(30)).toUi()
            ContentState.Success(word)
        } catch (e: Exception) {
            e.printStackTrace()
            ContentState.Error(ConnectionErrorState(e))
        }

        return randomWord
    }

    private suspend fun search(query: TextFieldValue, offset: Long = 0): DashboardEvent {
        val currentState = state.value

        if (query.text == currentState.searchFieldState.searchFieldValue.text && query.text.isNotBlank()) {
            return DashboardEvent.InputQuery(query)
        }
        searchJob.cancel()
        if (query.text.isBlank()) {
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
        val state = state.value
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(400L)
            ensureActive()

            val found = searchWordsUseCase(
                SearchWordsUseCase.Param(query.text.trim(), offset, limit, exactly)
            ).fastMap { it.toUi() }

            withContext(Dispatchers.Main) {
                ensureActive()

                handleState(
                    DashboardEvent.Content(
                        state.recentSearch,
                        state.wordOfTheDay,
                        state.randomWord,
                        ContentState.Success(found),
                        SubscriptionStatus.None,
                        state.randomQuote
                    )
                )
            }
        }
    }

    private suspend fun updateFavourite(
        word: WordCombinedUi,
        state: DashboardState
    ): DashboardEvent.Content {
        updateFavouriteWordUseCase(UpdateFavouriteWordUseCase.Param(word.word, word.isFavorite))

        return state.toContentEventWithUpdatedWord(word.copy(isFavorite = !word.isFavorite))
    }

    companion object {
        private const val DEFAULT_LIMIT = 20L
        private const val START_LIMIT = DEFAULT_LIMIT * 2
    }
}

internal fun DashboardViewModel.inputQuery(query: TextFieldValue) =
    handleAction(DashboardAction.OnInputQuery(query))

internal fun DashboardViewModel.queryFieldFocusChanged(focused: Boolean) =
    handleAction(DashboardAction.OnQueryFieldFocusChange(focused))

internal fun DashboardViewModel.openWord(wordCombined: WordCombinedUi) =
    handleAction(DashboardAction.OnOpenWord(wordCombined))

internal fun DashboardViewModel.openSearchWord(wordCombined: WordCombinedUi) =
    handleAction(DashboardAction.OnOpenSearchWord(wordCombined))

internal fun DashboardViewModel.favoriteWord(wordCombined: WordCombinedUi) =
    handleAction(DashboardAction.OnFavoriteWord(wordCombined))

internal fun DashboardViewModel.init() = handleAction(DashboardAction.Init)
internal fun DashboardViewModel.tryAgain() = handleAction(DashboardAction.OnLoadData)
internal fun DashboardViewModel.nextRandomWord() = handleAction(DashboardAction.OnNextRandomWord)

internal fun DashboardViewModel.openFavorites() =
    handleAction(DashboardAction.OnMenuItemClicked(DashboardMenuItem.FAVORITES))

internal fun DashboardViewModel.openGames() =
    handleAction(DashboardAction.OnMenuItemClicked(DashboardMenuItem.GAMES))

internal fun DashboardViewModel.openSettings() =
    handleAction(DashboardAction.OnMenuItemClicked(DashboardMenuItem.SETTINGS))

internal fun DashboardViewModel.onBack() =
    handleAction(DashboardAction.OnBackToMain)

internal fun DashboardViewModel.favoriteQuote(quote: Quote) = handleAction(DashboardAction.OnFavoriteQuote(quote))

internal fun DashboardViewModel.nextRandomQuote() = handleAction(DashboardAction.OnNextRandomQuote)
