package com.usmonie.word.features.new.dashboard

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.analytics.DashboardAnalyticsEvents
import com.usmonie.word.features.dashboard.DashboardViewModel
import com.usmonie.word.features.dashboard.domain.usecase.GetSearchHistoryUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetWordOfTheDayUseCase
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SearchWordsUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.models.WordCombinedUi
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.models.toDomain
import com.usmonie.word.features.models.toUi
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
internal class NewDashboardViewModel(
    private val searchWordsUseCase: SearchWordsUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val getWordOfTheDayUseCase: GetWordOfTheDayUseCase,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getRandomWordUseCase: RandomWordUseCase,
    private val analytics: Analytics
) :
    BaseViewModel<NewDashboardState, NewDashboardAction, NewDashboardEvent, NewDashboardEffect>(
        NewDashboardState.Loading()
    ) {

    private var searchJob: Job? = null

    init {
        onTryAgain()
    }

    override fun NewDashboardState.reduce(event: NewDashboardEvent): NewDashboardState {
        return when (event) {
            NewDashboardEvent.ContentLoading -> NewDashboardState.Loading()
            NewDashboardEvent.BackToMain -> when (this) {
                is NewDashboardState.Error -> this
                is NewDashboardState.Loading -> this
                is NewDashboardState.Success -> copy(hasFocus = false, query = TextFieldValue())
            }

            is NewDashboardEvent.Content -> when (this) {
                is NewDashboardState.Success -> copy(
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

                else -> NewDashboardState.Success(
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

            }

            is NewDashboardEvent.InputQuery -> when (this) {
                is NewDashboardState.Error -> this
                is NewDashboardState.Loading -> this
                is NewDashboardState.Success -> copy(query = event.query)
            }

            is NewDashboardEvent.QueryFocusChanged -> when (this) {
                is NewDashboardState.Error -> this
                is NewDashboardState.Loading -> this
                is NewDashboardState.Success -> copy(hasFocus = event.hasFocus)
            }

            else -> this
        }
    }

    override suspend fun processAction(action: NewDashboardAction): NewDashboardEvent {
        return when (action) {
            NewDashboardAction.ClearQuery -> NewDashboardEvent.BackToMain
            NewDashboardAction.Initial -> {
                loadData(0L)
                NewDashboardEvent.ContentLoading
            }

            is NewDashboardAction.InputQuery -> search(action.query, 0)
            is NewDashboardAction.NextRandomWord -> {
                val currentState = state.value

                if (currentState !is NewDashboardState.Success) return NewDashboardEvent.ContentLoading
                val newRandomWord = newRandomWord(false, currentState)

                NewDashboardEvent.Content(
                    currentState.recentSearch,
                    currentState.wordOfTheDay,
                    newRandomWord,
                    currentState.foundWords,
                    currentState.learnedWordsStatus.status,
                    currentState.practiceWordsStatus.status,
                    currentState.newWordsStatus.status,
                    currentState.streakDaysStatus.status,
                    currentState.query
                )
            }

            is NewDashboardAction.OpenWord -> NewDashboardEvent.OpenWord(action.word)

            is NewDashboardAction.QueryFieldFocusChange ->
                NewDashboardEvent.QueryFocusChanged(action.isFocus)

            NewDashboardAction.Refresh -> {
                updateData()
                NewDashboardEvent.ContentLoading
            }

            is NewDashboardAction.UpdateFavorite -> {
                val currentState = state.value

                if (currentState !is NewDashboardState.Success) return NewDashboardEvent.ContentLoading
                updateFavourite(action.word, currentState)
            }

            NewDashboardAction.OnMenuItemClicked.About -> NewDashboardEvent.MenuItemOpen.About
            NewDashboardAction.OnMenuItemClicked.Favorites -> NewDashboardEvent.MenuItemOpen.Favorites
            NewDashboardAction.OnMenuItemClicked.Games -> NewDashboardEvent.MenuItemOpen.Games
            NewDashboardAction.OnMenuItemClicked.Settings -> NewDashboardEvent.MenuItemOpen.Settings
        }
    }

    override suspend fun handleEvent(event: NewDashboardEvent) = when (event) {
        is NewDashboardEvent.OpenWord -> NewDashboardEffect.OpenWord(event.word)
        is NewDashboardEvent.MenuItemOpen.Favorites -> NewDashboardEffect.OpenFavorites()
        is NewDashboardEvent.MenuItemOpen.Games -> NewDashboardEffect.OpenGames()
        is NewDashboardEvent.MenuItemOpen.Settings -> NewDashboardEffect.OpenSettings()
        is NewDashboardEvent.MenuItemOpen.About -> NewDashboardEffect.OpenAbout()
        else -> null
    }

    fun onFavoritesClick() = handleAction(NewDashboardAction.OnMenuItemClicked.Favorites)
    fun onSettingsClick() = handleAction(NewDashboardAction.OnMenuItemClicked.Settings)
    fun onGamesClick() = handleAction(NewDashboardAction.OnMenuItemClicked.Games)
    fun onAboutClick() = handleAction(NewDashboardAction.OnMenuItemClicked.About)
    fun onQueryChanged(query: TextFieldValue) = handleAction(NewDashboardAction.InputQuery(query))

    fun onQueryFieldFocusChanged(isFocus: Boolean) =
        handleAction(NewDashboardAction.QueryFieldFocusChange(isFocus))

    fun onBackClick() = handleAction(NewDashboardAction.ClearQuery)

    fun onOpenWord(wordCombined: WordCombinedUi) =
        handleAction(NewDashboardAction.OpenWord(wordCombined))

    fun onUpdateFavorite(wordCombined: WordCombinedUi) =
        handleAction(NewDashboardAction.UpdateFavorite(wordCombined))

    fun onNextRandomWord(state: NewDashboardState.Success) =
        handleAction(NewDashboardAction.NextRandomWord(state))

    fun onLearnClick(wordCombined: WordCombinedUi) {

    }

    fun onTryAgain() {
        handleAction(NewDashboardAction.Initial)
    }

    private suspend fun search(query: TextFieldValue, offset: Long = 0): NewDashboardEvent {
        val currentState = state.value
        if (currentState !is NewDashboardState.Success) return NewDashboardEvent.BackToMain

        if (query.text == currentState.query.text && query.text.isNotBlank()) {
            return NewDashboardEvent.InputQuery(query)
        }
        searchJob?.cancel()
        if (query.text.isBlank()) {
            updateData()
            return NewDashboardEvent.InputQuery(query)
        }

        launchSearch(currentState, query, offset)
        return NewDashboardEvent.InputQuery(query)
    }

    private suspend fun launchSearch(
        state: NewDashboardState.Success,
        query: TextFieldValue,
        offset: Long,
        limit: Long = DashboardViewModel.DEFAULT_LIMIT,
        exactly: Boolean = false
    ) {
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(350L)
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
                    NewDashboardEvent.Content(
                        state.recentSearch,
                        state.wordOfTheDay,
                        state.randomWord,
                        ContentState.Success(found),
                        state.learnedWordsStatus.status,
                        state.practiceWordsStatus.status,
                        state.newWordsStatus.status,
                        state.streakDaysStatus.status,
                        state.query,
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
                    DashboardViewModel.START_LIMIT
                )
            ).fastMap { it.toUi() }

            val wordOfTheDay = if (update && currentState is NewDashboardState.Success) {
                currentState.wordOfTheDay
            } else {
                try {
                    val word = getWordOfTheDayUseCase(GetWordOfTheDayUseCase.Param).toUi()
                    ContentState.Success(Pair(word.wordEtymology.random().words.random(), word))
                } catch (e: Exception) {
                    e.printStackTrace()
                    ContentState.Error(ConnectionErrorState(e))
                }
            }

            val randomWord = newRandomWord(update, currentState)


            handleState(
                if (currentState is NewDashboardState.Success) {
                    NewDashboardEvent.Content(
                        recentSearch = recentSearch,
                        wordOfTheDay = wordOfTheDay,
                        randomWord = randomWord,
                        foundWords = currentState.foundWords,
                        learnedWordsStatus = currentState.learnedWordsStatus.status,
                        practiceWordsStatus = currentState.practiceWordsStatus.status,
                        newWordsStatus = currentState.newWordsStatus.status,
                        streakDaysStatus = currentState.streakDaysStatus.status,
                        query = currentState.query
                    )
                } else {
                    NewDashboardEvent.Content(
                        recentSearch = recentSearch,
                        wordOfTheDay = wordOfTheDay,
                        randomWord = randomWord,
                        foundWords = ContentState.Success(listOf()),
                        learnedWordsStatus = "12",//"currentState.learnedWordsStatus.status",
                        practiceWordsStatus = "24",// "currentState.practiceWordsStatus.status",
                        newWordsStatus = "8", //"currentState.learnedWordsStatus.status",
                        streakDaysStatus = "7", //"currentState.streakDaysStatus.status",
                        query = TextFieldValue()
                    )
                }
            )
        }
    }

    private suspend fun NewDashboardViewModel.newRandomWord(
        update: Boolean,
        currentState: NewDashboardState
    ): ContentState<Pair<WordUi, WordCombinedUi>> {
        val randomWord = if (update && currentState is NewDashboardState.Success) {
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
        state: NewDashboardState.Success
    ): NewDashboardEvent.Content {
        updateFavouriteUseCase(UpdateFavouriteUseCase.Param(word.toDomain()))

        return state.updateFavourite(word.copy(isFavorite = !word.isFavorite))
    }

    private fun NewDashboardState.Success.updateFavourite(updatedWord: WordCombinedUi): NewDashboardEvent.Content {
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

        return NewDashboardEvent.Content(
            wordOfTheDay = newWordOfTheDay,
            foundWords = newFoundWords,
            randomWord = newRandomWord,
            recentSearch = newRecentSearch,
            learnedWordsStatus = this.learnedWordsStatus.status,
            practiceWordsStatus = this.practiceWordsStatus.status,
            newWordsStatus = this.newWordsStatus.status,
            streakDaysStatus = this.streakDaysStatus.status,
            query = this.query,
        )
    }

    private fun mapNewWord(
        mappedWord: WordCombinedUi,
        updatedWord: WordCombinedUi
    ) = if (mappedWord.word == updatedWord.word) updatedWord else mappedWord
}