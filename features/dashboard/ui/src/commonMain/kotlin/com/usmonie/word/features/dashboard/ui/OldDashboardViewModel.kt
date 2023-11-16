//package com.usmonie.word.features.home.ui
//
//import androidx.compose.ui.text.input.TextFieldValue
//import com.usmonie.word.features.home.domain.models.WordDomain
//import com.usmonie.word.features.home.domain.usecase.AddToFavouriteUseCase
//import com.usmonie.word.features.home.domain.usecase.FilterWordsUseCase
//import com.usmonie.word.features.home.domain.usecase.GetAllFavouritesUseCase
//import com.usmonie.word.features.home.domain.usecase.GetSearchHistoryUseCase
//import com.usmonie.word.features.home.domain.usecase.GetWordOfTheDayUseCase
//import com.usmonie.word.features.home.domain.usecase.ParseDictionaryUseCase
//import com.usmonie.word.features.home.domain.usecase.SearchWordsUseCase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.cancelAndJoin
//import kotlinx.coroutines.launch
//import wtf.speech.core.ui.BaseViewModel
//import wtf.speech.core.ui.ContentState
//import wtf.word.core.domain.models.Definition
//import wtf.word.core.domain.models.Word
//
//class DashboardViewModel(
//    private val parseDictionaryUseCase: ParseDictionaryUseCase,
//    private val searchWordsUseCase: SearchWordsUseCase,
//    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
//    private val getWordOfTheDayUseCase: GetWordOfTheDayUseCase,
//    private val filterWordsUseCase: FilterWordsUseCase,
//    private val addToFavouriteUseCase: AddToFavouriteUseCase,
//    private val getAllFavouritesUseCase: GetAllFavouritesUseCase
//) : BaseViewModel<DashboardState, DashboardAction, DashboardEvent, DashboardEffect>(
//    DashboardState.Loading,
//    Dispatchers.Default
//) {
//    private var job: Job? = null
//
//    private var lastOffset: Int = 0
//
//    init {
//        handleAction(DashboardAction.LoadData())
//    }
//
//    fun showFavourites(offset: Int = 0) {
//        if (state.value is DashboardState.Favourites) {
//            handleAction(DashboardAction.LoadData())
//            return
//        }
//
//        if (offset == 0 || lastOffset != offset) {
//            lastOffset = offset
//            handleAction(DashboardAction.ShowFavourites(offset.toLong()))
//        }
//    }
//
//    fun onFilter(filter: Char, isSelected: Boolean, offset: Int = 0) {
//        if (offset == 0 || lastOffset != offset) {
//            lastOffset = offset
//            handleAction(
//                if (isSelected) DashboardAction.Filter.StartFilter(filter, offset.toLong())
//                else DashboardAction.Filter.CancelFilter(filter)
//            )
//        }
//    }
//
//    fun onInputQuery(value: TextFieldValue) = handleAction(DashboardAction.Search.InputQuery(value))
//    fun onSearch(value: TextFieldValue, offset: Int = 0) {
//        if (offset == 0 || lastOffset != offset) {
//            lastOffset = offset
//
//            handleAction(DashboardAction.Search.StartSearch(value, offset.toLong()))
//        }
//    }
//
//    fun onNextList(offset: Int = 0) {
//        if (offset == 0 || lastOffset != offset) {
//            lastOffset = offset
//            handleAction(DashboardAction.LoadData(offset.toLong()))
//        }
//    }
//
//    fun onOpenWord(word: Word) = handleAction(DashboardAction.OpenWord(word))
//    fun onAddToFavourites(word: Word, position: Int = -1) =
//        handleAction(DashboardAction.AddToFavourites(word, position))
//
//    fun onFindSynonym(synonym: String) = handleAction(DashboardAction.Search.FindSynonym(synonym))
//
//    override fun DashboardState.reduce(event: DashboardEvent): DashboardState {
//        return when (event) {
//            is DashboardEvent.UpdateWord -> {
//                if (event.position > -1) this.updateWordInList(event.word, event.position)
//                else this.updateWordOfTheDay(event.word)
//            }
//
//            is DashboardEvent.LoadedData -> DashboardState.Idle(
//                wordOfTheDay = ContentState.Success(event.wordOfDay),
//                lastSearches = ContentState.Success(
//                    if (this is DashboardState.Idle) buildWordsList(
//                        this.wordsState.item,
//                        event.words
//                    )
//                    else event.words
//                )
//            )
//
//            is DashboardEvent.UpdateLastSearches -> this
//            is DashboardEvent.LoadingSearch -> when (this) {
//                is DashboardState.Search -> this.copy(query = event.queryText)
//                else -> DashboardState.Search(event.queryText)
//            }
//
//            is DashboardEvent.LoadedSearch -> DashboardState.Search(
//                event.queryText,
//                ContentState.Success(buildWordsList(this.wordsState.item, event.foundWords))
//            )
//
//            is DashboardEvent.InputQuery -> this.inputQuery(event.queryText)
//
//            is DashboardEvent.LoadedFilteredData -> DashboardState.Filter(
//                selectedChar = event.charFilter,
//                foundWords = ContentState.Success(
//                    buildWordsList(
//                        if (this.selectedChar == event.charFilter) this.wordsState.item else listOf(),
//                        event.foundWords
//                    )
//                )
//            )
//
//            is DashboardEvent.LoadingFilteredData -> when (this) {
//                is DashboardState.Filter -> this.copy(
//                    selectedChar = event.charFilter,
//                    foundWords = if (this.selectedChar == event.charFilter) this.foundWords
//                    else ContentState.Loading(),
//                )
//
//                else -> DashboardState.Filter(selectedChar = event.charFilter)
//            }
//
//            is DashboardEvent.LoadedFavourites -> when (this) {
//                is DashboardState.Favourites -> this.copy(
//                    favouriteWords = ContentState.Success(
//                        buildWordsList(this.favouriteWords.item, event.words)
//                    )
//                )
//
//                else -> DashboardState.Favourites(favouriteWords = ContentState.Success(event.words))
//            }
//
//            is DashboardEvent.LoadingFavourites -> when (this) {
//                is DashboardState.Favourites -> this
//
//                else -> DashboardState.Favourites(favouriteWords = ContentState.Loading())
//            }
//
//            else -> this
//        }
//    }
//
//    override suspend fun processAction(action: DashboardAction): DashboardEvent {
//        return when (action) {
//            is DashboardAction.AddToFavourites -> addToFavourites(action)
//            is DashboardAction.OpenWord -> DashboardEvent.OpenWord(action.word)
//            is DashboardAction.LoadData -> loadData(action.offset)
//            DashboardAction.Update -> update()
//            is DashboardAction.Search -> search(action)
//            is DashboardAction.Filter -> filter(action)
//            is DashboardAction.ShowFavourites -> loadFavourites(action)
//        }
//    }
//
//    private suspend fun loadFavourites(action: DashboardAction.ShowFavourites): DashboardEvent {
//        if (action.offset == 0L) job?.cancelAndJoin()
//
//        job = viewModelScope.launch {
//            val favourites = getAllFavouritesUseCase(Unit)
//            if (job?.isCancelled == true) return@launch
//
//            handleState(DashboardEvent.LoadedFavourites(favourites.map { it.to() }))
//        }
//        return DashboardEvent.LoadingFavourites
//    }
//
//    private suspend fun addToFavourites(
//        action: DashboardAction.AddToFavourites
//    ): DashboardEvent.UpdateWord {
//        addToFavouriteUseCase(AddToFavouriteUseCase.Param(action.word.toDomain()))
//
//        return DashboardEvent.UpdateWord(
//            action.word.copy(isFavourite = !action.word.isFavourite),
//            action.position
//        )
//    }
//
//    private suspend fun update(): DashboardEvent.LoadedData {
//        val searchHistory = getSearchHistoryUseCase(
//            GetSearchHistoryUseCase.Param(0, START_LIMIT)
//        )
//        val wordOfTheDay = getWordOfTheDayUseCase(GetWordOfTheDayUseCase.Param)
//
//        return DashboardEvent.LoadedData(
//            wordOfTheDay.to(),
//            offset = 0,
//            words = searchHistory.map { it.to() }
//        )
//    }
//
//    private suspend fun filter(action: DashboardAction.Filter): DashboardEvent {
//        if (action !is DashboardAction.Filter.StartFilter) return processAction(DashboardAction.LoadData())
//        if (action.offset == 0L) job?.cancelAndJoin()
//
//        job = viewModelScope.launch {
//            val found = filterWordsUseCase(
//                FilterWordsUseCase.Param(
//                    action.charFilter,
//                    action.offset,
//                    DEFAULT_LIMIT
//                )
//            )
//
//            if (job?.isCancelled == true) return@launch
//
//            handleState(
//                DashboardEvent.LoadedFilteredData(
//                    action.charFilter,
//                    found.map { it.to() }
//                )
//            )
//        }
//
//        return DashboardEvent.LoadingFilteredData(action.charFilter, action.offset)
//    }
//
//    private suspend fun search(action: DashboardAction.Search): DashboardEvent {
//        if (action.query.text.isEmpty()) return processAction(DashboardAction.LoadData())
//
//        return when (action) {
//            is DashboardAction.Search.FindSynonym -> {
//                launchSearch(
//                    0,
//                    START_LIMIT,
//                    action.query,
//                    false
//                )
//                DashboardEvent.LoadingSearch(action.query, 0)
//            }
//
//            is DashboardAction.Search.InputQuery -> return DashboardEvent.InputQuery(action.query)
//            is DashboardAction.Search.StartSearch -> {
//                launchSearch(
//                    action.offset,
//                    if (action.offset == 0L) START_LIMIT else DEFAULT_LIMIT,
//                    action.query
//                )
//                DashboardEvent.LoadingSearch(action.query, action.offset)
//            }
//        }
//
//    }
//
//    private fun buildWordsList(
//        currentList: List<Word>?,
//        additionalList: List<Word>
//    ): List<Word> = (currentList ?: listOf()) + additionalList
//
//    private suspend fun launchSearch(
//        offset: Long,
//        limit: Long,
//        query: TextFieldValue,
//        exactly: Boolean = false
//    ) {
//        if (offset == 0L) job?.cancelAndJoin()
//
//        job = viewModelScope.launch {
//            val found = search(query.text, offset, limit, exactly)
//            if (job?.isCancelled == true) return@launch
//            handleState(DashboardEvent.LoadedSearch(query, found))
//        }
//    }
//
//    override suspend fun handleEvent(event: DashboardEvent): DashboardEffect? {
//        return when (event) {
//            is DashboardEvent.OpenWord -> DashboardEffect.OpenWord(event.word)
//            is DashboardEvent.LoadingSearch -> if (event.offset == 0L) DashboardEffect.RestartList() else null
//            is DashboardEvent.LoadingFilteredData -> if (event.offset == 0L) DashboardEffect.RestartList() else null
//            is DashboardEvent.LoadedData -> if (event.offset == 0L) DashboardEffect.RestartList() else null
//            else -> null
//        }
//    }
//
//    private suspend fun loadData(offset: Long): NewDashboardEvent.InitialData {
//        parseDictionaryUseCase(Unit)
//
//        val recentSearch = getSearchHistoryUseCase(
//            GetSearchHistoryUseCase.Param(
//                offset,
//                START_LIMIT
//            )
//        ).map { it.to() }
//        val wordOfTheDay = getWordOfTheDayUseCase(GetWordOfTheDayUseCase.Param).to()
//        val favourites = getAllFavouritesUseCase(Unit).map { it.to() }
//
//        return NewDashboardEvent.InitialData(
//            favourites = favourites,
//            recentSearch = recentSearch,
//            wordOfTheDay = ContentState.Success(wordOfTheDay)
//        )
//    }
//
//    private suspend fun search(
//        query: String,
//        offset: Long,
//        limit: Long,
//        exactly: Boolean
//    ): List<Word> {
//        val found = searchWordsUseCase(SearchWordsUseCase.Param(query, offset, limit, exactly))
//
//        return found.map { it.to() }
//    }
//
//    companion object {
//        const val DEFAULT_LIMIT = 20L
//        const val START_LIMIT = 40L
//    }
//}