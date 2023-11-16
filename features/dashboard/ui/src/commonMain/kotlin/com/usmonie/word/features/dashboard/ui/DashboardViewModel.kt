package com.usmonie.word.features.dashboard.ui

import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.usecase.ChangeThemeUseCase
import com.usmonie.word.features.dashboard.domain.usecase.ClearRecentUseCase
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetSearchHistoryUseCase
import com.usmonie.word.features.dashboard.domain.usecase.GetWordOfTheDayUseCase
import com.usmonie.word.features.dashboard.domain.usecase.ParseDictionaryUseCase
import com.usmonie.word.features.dashboard.domain.usecase.SearchWordsUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.models.SynonymUi
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.models.to
import com.usmonie.word.features.models.toDomain
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
import wtf.word.core.design.themes.WordTypography
import wtf.word.core.design.themes.WordTypography.Companion.next

class DashboardViewModel(
    private val parseDictionaryUseCase: ParseDictionaryUseCase,
    private val searchWordsUseCase: SearchWordsUseCase,
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val getWordOfTheDayUseCase: GetWordOfTheDayUseCase,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getCurrentThemeUseCase: CurrentThemeUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase,
    private val clearRecentUseCase: ClearRecentUseCase
) : BaseViewModel<DashboardState, DashboardAction, DashboardEvent, DashboardEffect>(DashboardState()) {

    private var searchJob: Job? = null

    init {
        handleAction(DashboardAction.Initial)
    }

    fun onOpenWord(wordUi: WordUi) = handleAction(DashboardAction.OpenWord(wordUi))
    fun onShareWord(wordUi: WordUi) {}
    fun onSynonymClicked(synonym: SynonymUi) =
        handleAction(DashboardAction.InputQuery(synonym.word))

    fun onUpdateFavouritesPressed(wordUi: WordUi) =
        handleAction(DashboardAction.UpdateFavourite(wordUi))

    fun onQueryChanged(query: String) = handleAction(DashboardAction.InputQuery(query))
    fun onWordOfTheDayItemClicked() = handleAction(DashboardAction.OnMenuItemClick.WordOfTheDay)
    fun onFavouritesItemClicked() = handleAction(DashboardAction.OnMenuItemClick.Favourites)
    fun onSettingsItemClicked() = handleAction(DashboardAction.OnMenuItemClick.Settings)
    fun onChangeColors() = handleAction(DashboardAction.ChangeColors)
    fun onChangeFonts() = handleAction(DashboardAction.ChangeFonts)
    fun onClearRecentHistory() = handleAction(DashboardAction.ClearRecentHistory)

    fun onBackClick() = handleAction(DashboardAction.BackToMain)

    override fun DashboardState.reduce(event: DashboardEvent) = when (event) {
        is DashboardEvent.Loading -> this
        is DashboardEvent.UpdatedFavourites -> this.updateFavourite(event.word)
        is DashboardEvent.FoundWords -> this.copy(
            query = event.query,
            foundWords = ContentState.Success(event.foundWords)
        )

        is DashboardEvent.InputQuery -> this.copy(
            query = event.query,
            foundWords = ContentState.Loading()
        )

        is DashboardEvent.NextItemsLoaded.FoundWord -> this.loadNextFoundWords(event.newWords)
        is DashboardEvent.NextItemsLoaded.RecentSearch -> this.loadNextRecent(event.newWords)
        is DashboardEvent.OpenWord -> this
        is DashboardEvent.InitialData -> copy(
            wordOfTheDay = event.wordOfTheDay,
            recentSearch = event.recentSearch,
        )

        is DashboardEvent.UpdateData -> copy(
            wordOfTheDay = event.wordOfTheDay,
            recentSearch = event.recentSearch,
        )

        DashboardEvent.UpdateMenuItemState.Favourites -> this
        DashboardEvent.UpdateMenuItemState.Settings -> this.openSettings()
        DashboardEvent.UpdateMenuItemState.WordOfTheDay -> this.openWordOfTheDay()
        is DashboardEvent.ChangeTheme -> this
        DashboardEvent.BackToMain -> this.copy(query = "")
    }

    override suspend fun processAction(action: DashboardAction) = when (action) {
        is DashboardAction.InputQuery -> search(action.query)

        DashboardAction.NextItems.FoundWords ->
            DashboardEvent.NextItemsLoaded.FoundWord(listOf())

        DashboardAction.NextItems.RecentSearch ->
            DashboardEvent.NextItemsLoaded.RecentSearch(listOf())

        DashboardAction.OnMenuItemClick.Favourites -> DashboardEvent.UpdateMenuItemState.Favourites
        DashboardAction.OnMenuItemClick.Settings -> DashboardEvent.UpdateMenuItemState.Settings
        DashboardAction.OnMenuItemClick.WordOfTheDay -> DashboardEvent.UpdateMenuItemState.WordOfTheDay
        is DashboardAction.OpenWord -> DashboardEvent.OpenWord(action.word)
        is DashboardAction.UpdateFavourite -> updateFavourite(action)
        DashboardAction.Initial -> initialData()
        DashboardAction.ChangeColors -> changeColors()
        DashboardAction.ChangeFonts -> changeFonts()
        DashboardAction.ClearRecentHistory -> clearRecentHistory()
        DashboardAction.BackToMain -> DashboardEvent.BackToMain
    }

    override suspend fun handleEvent(event: DashboardEvent) = when (event) {
        is DashboardEvent.ChangeTheme -> DashboardEffect.ChangeTheme(
            event.colors,
            event.typography
        )
        is DashboardEvent.UpdateMenuItemState.Favourites -> DashboardEffect.OpenFavourites()

        is DashboardEvent.OpenWord -> DashboardEffect.OpenWord(event.word)
        else -> null
    }

    private suspend fun clearRecentHistory(): DashboardEvent {
        clearRecentUseCase(Unit)
        updateData()
        return DashboardEvent.Loading
    }

    private suspend fun search(query: String, offset: Long = 0): DashboardEvent {
        searchJob?.cancel()
        if (query.isBlank()) return DashboardEvent.InputQuery(query)

        launchSearch(query, offset)
        return DashboardEvent.InputQuery(query)
    }

    private suspend fun launchSearch(
        query: String,
        offset: Long,
        limit: Long = DEFAULT_LIMIT,
        exactly: Boolean = false
    ) {
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            ensureActive()

            val found = searchWordsUseCase(
                SearchWordsUseCase.Param(query, offset, limit, exactly)
            ).map {
                ensureActive()
                it.to()
            }

            delay(200L)
            withContext(Dispatchers.Main) {
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
            action.word.copy(isFavourite = !action.word.isFavourite),
        )
    }

    private suspend fun initialData(): DashboardEvent {
        loadData(0)
        return DashboardEvent.Loading
    }

    private suspend fun updateData() = loadData(0, true)

    private suspend fun loadData(offset: Long, update: Boolean = false): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            parseDictionaryUseCase(Unit)
            val recentSearch = getSearchHistoryUseCase(
                GetSearchHistoryUseCase.Param(
                    offset,
                    START_LIMIT
                )
            ).map { it.to() }
            val wordOfTheDay = getWordOfTheDayUseCase(GetWordOfTheDayUseCase.Param).to()

            val theme = loadTheme()

            handleState(
                if (update) DashboardEvent.UpdateData(
                    recentSearch = recentSearch,
                    wordOfTheDay = ContentState.Success(wordOfTheDay),
                ) else
                    DashboardEvent.InitialData(
                        recentSearch = recentSearch,
                        wordOfTheDay = ContentState.Success(wordOfTheDay),
                        theme.first,
                        theme.second
                    )
            )
        }
    }

    private suspend fun changeColors(): DashboardEvent {
        val currentTheme = loadTheme()
        val changed = Pair(currentTheme.first.next(), currentTheme.second)
        changeTheme(changed.first, changed.second)
        return DashboardEvent.ChangeTheme(changed.first, changed.second)
    }

    private suspend fun changeFonts(): DashboardEvent {
        val currentTheme = loadTheme()
        val changed = Pair(currentTheme.first, currentTheme.second.next())
        changeTheme(changed.first, changed.second)
        return DashboardEvent.ChangeTheme(changed.first, changed.second)
    }

    private suspend fun changeTheme(colors: WordColors, typography: WordTypography) {
        changeThemeUseCase(Theme(colors.name, typography.name))
    }

    private fun loadTheme(): Pair<WordColors, WordTypography> {
        val theme = getCurrentThemeUseCase(Unit)
        return Pair(
            theme.colorsName?.let { WordColors.valueOf(it) } ?: WordColors.RICH_MAROON,
            theme.fonts?.let { WordTypography.valueOf(it) } ?: WordTypography.Friendly,
        )
    }

    companion object {
        const val DEFAULT_LIMIT = 20L
        const val START_LIMIT = 20L
    }
}
