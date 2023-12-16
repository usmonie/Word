package com.usmonie.word.features.new.dashboard

import androidx.compose.ui.text.input.TextFieldValue
import com.usmonie.word.features.analytics.DashboardAnalyticsEvents
import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.usecase.ChangeThemeUseCase
import com.usmonie.word.features.dashboard.domain.usecase.ClearRecentUseCase
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetSearchHistoryUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetWordOfTheDayUseCase
import com.usmonie.word.features.dashboard.domain.usecase.RandomWordUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SearchWordsUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.models.SynonymUi
import com.usmonie.word.features.new.models.WordCombinedUi
import com.usmonie.word.features.new.models.toDomain
import com.usmonie.word.features.new.models.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wtf.speech.core.ui.BaseViewModel
import wtf.speech.core.ui.ContentState
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.Friendly
import wtf.word.core.design.themes.typographies.WordTypography
import wtf.word.core.design.themes.typographies.WordTypography.Companion.next
import wtf.word.core.domain.Analytics
import wtf.word.core.domain.tools.fastMap

class DashboardViewModel(
    private val searchWordsUseCase: SearchWordsUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val getWordOfTheDayUseCase: GetWordOfTheDayUseCase,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getCurrentThemeUseCase: CurrentThemeUseCase,
    private val getRandomWordUseCase: RandomWordUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase,
    private val clearRecentUseCase: ClearRecentUseCase,
    private val analytics: Analytics
) : BaseViewModel<DashboardState, DashboardAction, DashboardEvent, DashboardEffect>(DashboardState()) {

    private var searchJob: Job? = null

    init {
        handleAction(DashboardAction.Initial)
    }

    fun onOpenWord(word: WordCombinedUi) = handleAction(DashboardAction.OpenWord(word))
    fun onShareWord(word: WordCombinedUi) {}
    fun onSynonymClicked(synonym: SynonymUi) =
        handleAction(DashboardAction.InputQuery(TextFieldValue(synonym.word)))

    fun onUpdateFavouritesPressed(word: WordCombinedUi) =
        handleAction(DashboardAction.UpdateFavourite(word))

    fun onUpdateRandomCard() = handleAction(DashboardAction.UpdateRandomWord)
    fun onGamesClicked() = handleAction(DashboardAction.OnMenuItemClick.Games)
    fun onHangman() = handleAction(DashboardAction.OnGamesItemClick.Hangman)
    fun onQueryChanged(query: String) {
        handleAction(DashboardAction.InputQuery(TextFieldValue(query)))
    }

    fun onFavouritesItemClicked() = handleAction(DashboardAction.OnMenuItemClick.Favourites)
    fun onSettingsItemClicked() = handleAction(DashboardAction.OnMenuItemClick.Settings)
    fun onAboutItemClicked() = handleAction(DashboardAction.OnMenuItemClick.About)
    fun onTelegramItemClicked() = handleAction(DashboardAction.OnMenuItemClick.Telegram)
    fun onChangeColors() = handleAction(DashboardAction.ChangeColors)
    fun onChangeFonts() = handleAction(DashboardAction.ChangeFonts)
    fun onClearRecentHistory() = handleAction(DashboardAction.ClearRecentHistory)
    fun onBackClick() = handleAction(DashboardAction.BackToMain)

    override fun DashboardState.reduce(event: DashboardEvent) = when (event) {
        is DashboardEvent.RandomWordLoading -> this.copy(wordOfTheDay = ContentState.Loading())
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

        is DashboardEvent.NextItemsLoaded.FoundWord -> this.loadNextFoundWords(event.newWords)
        is DashboardEvent.NextItemsLoaded.RecentSearch -> this.loadNextRecent(event.newWords)
        is DashboardEvent.OpenWord -> this
        is DashboardEvent.InitialData -> copy(
            wordOfTheDay = event.wordOfTheDay,
            recentSearch = event.recentSearch,
        )

        DashboardEvent.UpdateMenuItemState.Settings -> this.openSettings()
        DashboardEvent.UpdateMenuItemState.About -> this.openAbout()
        DashboardEvent.UpdateMenuItemState.WordOfTheDay -> this.openWordOfTheDay()
        DashboardEvent.UpdateMenuItemState.Games -> this.openGames()
        DashboardEvent.BackToMain -> this.copy(query = TextFieldValue())
        else -> this
    }

    override suspend fun processAction(action: DashboardAction) = when (action) {
        is DashboardAction.InputQuery -> search(action.query)

        DashboardAction.NextItems.FoundWords ->
            DashboardEvent.NextItemsLoaded.FoundWord(listOf())

        DashboardAction.NextItems.RecentSearch ->
            DashboardEvent.NextItemsLoaded.RecentSearch(listOf())

        DashboardAction.OnMenuItemClick.Favourites -> {
            analytics.log(DashboardAnalyticsEvents.OpenFavourites)
            DashboardEvent.UpdateMenuItemState.Favourites
        }

        DashboardAction.OnMenuItemClick.Settings -> {
            analytics.log(DashboardAnalyticsEvents.OpenSettings)

            DashboardEvent.UpdateMenuItemState.Settings
        }

        DashboardAction.OnMenuItemClick.WordOfTheDay -> DashboardEvent.UpdateMenuItemState.WordOfTheDay
        is DashboardAction.OpenWord -> {
            analytics.log(DashboardAnalyticsEvents.OpenWord(action.word))

            DashboardEvent.OpenWord(action.word)
        }

        is DashboardAction.UpdateFavourite -> updateFavourite(action)
        DashboardAction.Initial -> initialData()
        DashboardAction.ChangeColors -> changeColors()
        DashboardAction.ChangeFonts -> changeFonts()
        DashboardAction.ClearRecentHistory -> clearRecentHistory()
        DashboardAction.BackToMain -> DashboardEvent.BackToMain
        DashboardAction.Update -> {
            updateData()
            DashboardEvent.RandomWordLoading
        }

        DashboardAction.UpdateRandomWord -> initialData()

        DashboardAction.OnMenuItemClick.Games -> DashboardEvent.UpdateMenuItemState.Games
        DashboardAction.OnGamesItemClick.Hangman -> {
            val word = getRandomWordUseCase(RandomWordUseCase.Param(10))
            DashboardEvent.OpenGame.Hangman(word.toUi())
        }

        DashboardAction.OnMenuItemClick.About -> DashboardEvent.UpdateMenuItemState.About
        DashboardAction.OnMenuItemClick.Telegram -> DashboardEvent.UpdateMenuItemState.Telegram
    }

    override suspend fun handleEvent(event: DashboardEvent) = when (event) {
        is DashboardEvent.ChangeTheme -> DashboardEffect.ChangeTheme(
            event.colors,
            event.typography
        )

        is DashboardEvent.UpdateMenuItemState.Favourites -> DashboardEffect.OpenFavourites()

        is DashboardEvent.OpenWord -> DashboardEffect.OpenWord(event.word)
        is DashboardEvent.OpenGame.Hangman -> DashboardEffect.OpenHangman(event.word)
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
                SearchWordsUseCase.Param(query.text, offset, limit, exactly)
            ).fastMap {
                ensureActive()
                it.toUi()
            }

            delay(200L)
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
                val word = getWordOfTheDayUseCase(GetWordOfTheDayUseCase.Param).toUi()
                ContentState.Success(Pair(word.wordEtymology.random().words.random(), word))
            }

            handleState(
                DashboardEvent.InitialData(
                    recentSearch = recentSearch,
                    wordOfTheDay = wordOfTheDay,
                )
            )
        }
    }

    private suspend fun changeColors(): DashboardEvent {
        val currentTheme = loadTheme()
        val changed = Pair(currentTheme.first.next(), currentTheme.second)
        changeTheme(changed.first, changed.second)
        analytics.log(DashboardAnalyticsEvents.ChangeTheme(changed.first, changed.second))

        return DashboardEvent.ChangeTheme(changed.first, changed.second)
    }

    private suspend fun changeFonts(): DashboardEvent {
        val currentTheme = loadTheme()
        val changed = Pair(currentTheme.first, currentTheme.second.next())
        changeTheme(changed.first, changed.second)
        analytics.log(DashboardAnalyticsEvents.ChangeTheme(changed.first, changed.second))
        return DashboardEvent.ChangeTheme(changed.first, changed.second)
    }

    private suspend fun changeTheme(colors: WordColors, typography: WordTypography) {
        changeThemeUseCase(Theme(colors.name, typography.name))
    }

    private fun loadTheme(): Pair<WordColors, WordTypography> {
        val theme = getCurrentThemeUseCase(Unit)
        return Pair(
            theme.colorsName?.let { WordColors.valueOf(it) } ?: WordColors.RICH_MAROON,
            theme.fonts?.let { WordTypography.valueOf(it) } ?: Friendly,
        )
    }

    companion object {
        const val DEFAULT_LIMIT = 20L
        const val START_LIMIT = 20L
    }
}
