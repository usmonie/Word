package com.usmonie.word.features.dashboard.ui.dashboard

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.dashboard.domain.usecase.GetSearchHistoryUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetWordOfTheDayUseCase
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SearchWordsUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.dashboard.ui.analytics.DashboardAnalyticsEvents
import com.usmonie.word.features.dashboard.ui.dashboard.DashboardEvent.*
import com.usmonie.word.features.dashboard.ui.models.LearningStatus
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.models.WordUi
import com.usmonie.word.features.dashboard.ui.models.toUi
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus.*
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wtf.speech.core.ui.BaseViewModel
import wtf.speech.core.ui.ConnectionErrorState
import wtf.speech.core.ui.ContentState
import wtf.word.core.domain.Analytics
import wtf.word.core.domain.tools.fastMap

@Immutable
internal class DashboardViewModel(
    private val searchWordsUseCase: SearchWordsUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val getWordOfTheDayUseCase: GetWordOfTheDayUseCase,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getRandomWordUseCase: RandomWordUseCase,
    private val subscriptionStatusUseCase: SubscriptionStatusUseCase,
    private val analytics: Analytics
) : BaseViewModel<DashboardState, DashboardAction, DashboardEvent, DashboardEffect>(DashboardState.Loading()) {

    private var searchJob: Job? = null

    init {
        onTryAgain()

        viewModelScope.launchSafe {
            subscriptionStatusUseCase(Unit).collect {
                processAction(DashboardAction.SubscriptionStatusUpdated(it))
            }
        }
    }

    override fun DashboardState.reduce(event: DashboardEvent) = when (event) {
        ContentLoading -> DashboardState.Loading()
        BackToMain -> when (this) {
            is DashboardState.Error -> this
            is DashboardState.Loading -> this
            is DashboardState.Success -> copy(hasFocus = false, query = TextFieldValue())
        }

        is Content -> when (this) {
            is DashboardState.Success -> copy(
                wordOfTheDay = event.wordOfTheDay,
                randomWord = event.randomWord,
                recentSearch = event.recentSearch,
                foundWords = event.foundWords,
                learnedWordsStatus = LearningStatus(
                    title = "Learned",
                    status = event.learnedWordsStatus,
                    description = "Review"
                ),
                practiceWordsStatus = LearningStatus(
                    title = "Pending",
                    status = event.practiceWordsStatus,
                    description = "Practice Words"
                ),
                newWordsStatus = LearningStatus(
                    title = "New",
                    status = event.newWordsStatus,
                    description = "Learn New Words"
                ),
                streakDaysStatus = LearningStatus(
                    title = "Streak",
                    status = event.streakDaysStatus,
                    description = "Days"
                ),
            )

            else -> DashboardState.Success(
                wordOfTheDay = event.wordOfTheDay,
                randomWord = event.randomWord,
                recentSearch = event.recentSearch,
                foundWords = event.foundWords,
                learnedWordsStatus = LearningStatus(
                    title = "Learned",
                    status = event.learnedWordsStatus,
                    description = "Review"
                ),
                practiceWordsStatus = LearningStatus(
                    title = "Pending",
                    status = event.practiceWordsStatus,
                    description = "Practice Words"
                ),
                newWordsStatus = LearningStatus(
                    title = "New",
                    status = event.newWordsStatus,
                    description = "Learn New Words"
                ),
                streakDaysStatus = LearningStatus(
                    title = "Streak",
                    status = event.streakDaysStatus,
                    description = "Days"
                ),
                subscriptionStatus = event.subscriptionStatus
            )
        }

        is InputQuery -> when (this) {
            is DashboardState.Error -> this
            is DashboardState.Loading -> this
            is DashboardState.Success -> copy(
                query = event.query,
                foundWords = ContentState.Loading()
            )
        }

        is QueryFocusChanged -> when (this) {
            is DashboardState.Error -> this
            is DashboardState.Loading -> this
            is DashboardState.Success -> copy(hasFocus = event.hasFocus)
        }

        else -> this
    }

    override suspend fun processAction(action: DashboardAction): DashboardEvent {
        return when (action) {
            DashboardAction.ClearQuery -> BackToMain
            DashboardAction.Initial -> {
                loadData(0L)
                ContentLoading
            }

            is DashboardAction.InputQuery -> search(action.query, 0)
            is DashboardAction.NextRandomWord -> {
                val currentState = state.value

                if (currentState !is DashboardState.Success) return ContentLoading
                val newRandomWord = newRandomWord(false, currentState)

                Content(
                    currentState.recentSearch,
                    currentState.wordOfTheDay,
                    newRandomWord,
                    currentState.foundWords,
                    currentState.learnedWordsStatus.status,
                    currentState.practiceWordsStatus.status,
                    currentState.newWordsStatus.status,
                    currentState.streakDaysStatus.status,
                    currentState.query,
                    Sale()
                )
            }

            is DashboardAction.OpenWord -> OpenWord(action.word)

            is DashboardAction.QueryFieldFocusChange ->
                QueryFocusChanged(action.isFocus)

            DashboardAction.Refresh -> {
                updateData()
                ContentLoading
            }

            is DashboardAction.UpdateFavorite -> {
                val currentState = state.value

                if (currentState !is DashboardState.Success) return ContentLoading
                updateFavourite(action.word, currentState)
            }

            DashboardAction.OnMenuItemClicked.About -> MenuItemOpen.About
            DashboardAction.OnMenuItemClicked.Favorites -> MenuItemOpen.Favorites
            DashboardAction.OnMenuItemClicked.Games -> MenuItemOpen.Games
            DashboardAction.OnMenuItemClicked.Settings -> MenuItemOpen.Settings
            is DashboardAction.SubscriptionStatusUpdated -> SubscriptionUpdated(action.subscriptionStatus)
            DashboardAction.CollapseSubscriptionAd -> TODO()
            DashboardAction.ExpandSubscriptionAd -> TODO()
        }
    }

    override suspend fun handleEvent(event: DashboardEvent) = when (event) {
        is OpenWord -> DashboardEffect.OpenWord(event.word)
        is MenuItemOpen.Favorites -> DashboardEffect.OpenFavorites()
        is MenuItemOpen.Games -> DashboardEffect.OpenGames()
        is MenuItemOpen.Settings -> DashboardEffect.OpenSettings()
        is MenuItemOpen.About -> DashboardEffect.OpenAbout()
        else -> null
    }

    fun onFavoritesClick() = handleAction(DashboardAction.OnMenuItemClicked.Favorites)
    fun onSettingsClick() = handleAction(DashboardAction.OnMenuItemClicked.Settings)
    fun onGamesClick() = handleAction(DashboardAction.OnMenuItemClicked.Games)
    fun onAboutClick() = handleAction(DashboardAction.OnMenuItemClicked.About)
    fun onQueryChanged(query: TextFieldValue) = handleAction(DashboardAction.InputQuery(query))

    fun onQueryFieldFocusChanged(isFocus: Boolean) =
        handleAction(DashboardAction.QueryFieldFocusChange(isFocus))

    fun onBackClick() = handleAction(DashboardAction.ClearQuery)

    fun onOpenWord(wordCombined: WordCombinedUi) =
        handleAction(DashboardAction.OpenWord(wordCombined))

    fun onUpdateFavorite(wordCombined: WordCombinedUi) =
        handleAction(DashboardAction.UpdateFavorite(wordCombined))

    fun onNextRandomWord(state: DashboardState.Success) =
        handleAction(DashboardAction.NextRandomWord(state))

    fun onLearnClick(wordCombined: WordCombinedUi) {

    }

    fun onTryAgain() {
        handleAction(DashboardAction.Initial)
    }

    private suspend fun search(query: TextFieldValue, offset: Long = 0): DashboardEvent {
        val currentState = state.value
        if (currentState !is DashboardState.Success) return BackToMain

        if (query.text == currentState.query.text && query.text.isNotBlank()) {
            return InputQuery(query)
        }
        searchJob?.cancel()
        if (query.text.isBlank()) {
            updateData()
            return InputQuery(query)
        }

        launchSearch(currentState, query, offset)
        return InputQuery(query)
    }

    private suspend fun launchSearch(
        state: DashboardState.Success,
        query: TextFieldValue,
        offset: Long,
        limit: Long = DEFAULT_LIMIT,
        exactly: Boolean = false
    ) {
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(400L)
            ensureActive()

            val found = searchWordsUseCase(
                SearchWordsUseCase.Param(query.text.trim(), offset, limit, exactly)
            ).fastMap {
                ensureActive()
                it.toUi()
            }

            withContext(Dispatchers.Main) {
                ensureActive()
                analytics.log(DashboardAnalyticsEvents.Search(query.text))
                handleState(
                    Content(
                        state.recentSearch,
                        state.wordOfTheDay,
                        state.randomWord,
                        ContentState.Success(found),
                        state.learnedWordsStatus.status,
                        state.practiceWordsStatus.status,
                        state.newWordsStatus.status,
                        state.streakDaysStatus.status,
                        state.query,
                        SubscriptionStatus.None()
                    )
                )
            }
        }
    }

    private suspend fun updateData() = loadData(0, true)

    private suspend fun loadData(offset: Long, update: Boolean = false) {
        val currentState = state.value
        viewModelScope.launch(Dispatchers.IO) {
            val recentSearch = getSearchHistoryUseCase(
                GetSearchHistoryUseCase.Param(
                    offset,
                    START_LIMIT
                )
            ).fastMap { it.toUi() }

            val wordOfTheDay: ContentState<Pair<WordUi, WordCombinedUi>> =
                if (update && currentState is DashboardState.Success) {
//                currentState.wordOfTheDay

                    ContentState.Error(ConnectionErrorState(Exception()))
                } else {
                    try {

                        ContentState.Error(ConnectionErrorState(Exception()))
//                    val word = getWordOfTheDayUseCase(GetWordOfTheDayUseCase.Param).toUi()
//                    ContentState.Success(Pair(word.wordEtymology.random().words.random(), word))
                    } catch (e: Exception) {
                        e.printStackTrace()
                        ContentState.Error(ConnectionErrorState(e))
                    }
                }

            val randomWord = newRandomWord(update, currentState)

            handleState(
                if (currentState is DashboardState.Success) {
                    Content(
                        recentSearch = recentSearch,
                        wordOfTheDay = wordOfTheDay,
                        randomWord = randomWord,
                        foundWords = currentState.foundWords,
                        learnedWordsStatus = currentState.learnedWordsStatus.status,
                        practiceWordsStatus = currentState.practiceWordsStatus.status,
                        newWordsStatus = currentState.newWordsStatus.status,
                        streakDaysStatus = currentState.streakDaysStatus.status,
                        query = currentState.query,
                        SubscriptionStatus.Sale()
                    )
                } else {
                    Content(
                        recentSearch = recentSearch,
                        wordOfTheDay = wordOfTheDay,
                        randomWord = randomWord,
                        foundWords = ContentState.Success(listOf()),
                        learnedWordsStatus = 0, //"currentState.learnedWordsStatus.status",
                        practiceWordsStatus = 24, // "currentState.practiceWordsStatus.status",
                        newWordsStatus = 8, //"currentState.learnedWordsStatus.status",
                        streakDaysStatus = 0, //"currentState.streakDaysStatus.status",
                        query = TextFieldValue(),
                        SubscriptionStatus.Sale()
                    )
                }
            )
        }
    }

    private suspend fun DashboardViewModel.newRandomWord(
        update: Boolean,
        currentState: DashboardState
    ): ContentState<Pair<WordUi, WordCombinedUi>> {
        val randomWord = if (update && currentState is DashboardState.Success) {
            currentState.randomWord
        } else {
            try {
                val word = getRandomWordUseCase(RandomWordUseCase.Param(30)).toUi()
                ContentState.Success(Pair(word.wordEtymology.random().words.random(), word))
            } catch (e: Exception) {
                e.printStackTrace()
                ContentState.Error(ConnectionErrorState(e))
            }
        }
        return randomWord
    }

    private suspend fun updateFavourite(
        word: WordCombinedUi,
        state: DashboardState.Success
    ): Content {
        updateFavouriteUseCase(UpdateFavouriteUseCase.Param(word.word, word.isFavorite))

        return state.updateFavourite(word.copy(isFavorite = !word.isFavorite))
    }

    private fun DashboardState.Success.updateFavourite(updatedWord: WordCombinedUi): Content {
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
            is ContentState.Success -> if (updatedWord.word == randomWord.data.second.word) {
                ContentState.Success(randomWord.data.copy(second = updatedWord))
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

        val newRecentSearch = recentSearch.fastMap { mappedWord ->
            mapNewWord(mappedWord, updatedWord)
        }

        return Content(
            wordOfTheDay = newWordOfTheDay,
            foundWords = newFoundWords,
            randomWord = newRandomWord,
            recentSearch = newRecentSearch,
            learnedWordsStatus = this.learnedWordsStatus.status,
            practiceWordsStatus = this.practiceWordsStatus.status,
            newWordsStatus = this.newWordsStatus.status,
            streakDaysStatus = this.streakDaysStatus.status,
            query = this.query,
            subscriptionStatus = SubscriptionStatus.Sale()
        )
    }

    private fun mapNewWord(
        mappedWord: WordCombinedUi,
        updatedWord: WordCombinedUi
    ) = if (mappedWord.word == updatedWord.word) updatedWord else mappedWord

    companion object {
        private const val DEFAULT_LIMIT = 20L
        private const val START_LIMIT = DEFAULT_LIMIT * 2
    }
}