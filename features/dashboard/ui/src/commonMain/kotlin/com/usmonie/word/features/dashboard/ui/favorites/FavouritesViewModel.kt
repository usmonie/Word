@file:OptIn(ExperimentalMaterial3Api::class)

package com.usmonie.word.features.dashboard.ui.favorites

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Immutable
import com.usmonie.word.features.dashboard.ui.analytics.DashboardAnalyticsEvents
import com.usmonie.word.features.dashboard.domain.usecase.GetAllFavouritesUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.dashboard.ui.models.WordCombinedUi
import com.usmonie.word.features.dashboard.ui.models.toUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wtf.speech.core.ui.BaseViewModel
import wtf.word.core.domain.Analytics
import wtf.word.core.domain.tools.fastMap

@Immutable
class FavouritesViewModel(
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase,
    private val analytics: Analytics,
    listState: LazyListState = LazyListState(0, 0),
    appBarState: TopAppBarState = TopAppBarState(-Float.MAX_VALUE, 0f, 0f),
) : BaseViewModel<FavoritesState, FavoritesAction, FavoritesEvent, FavoritesEffect>(
    FavoritesState.Loading(listState, appBarState),
) {

    private var favouritesJob: Job? = null

    init {
        handleAction(FavoritesAction.Initial)
    }

    override fun FavoritesState.reduce(event: FavoritesEvent) = when (event) {
        is FavoritesEvent.Next -> TODO()
        is FavoritesEvent.Updated -> if (event.favourites.isEmpty()) FavoritesState.Empty(
            listState,
            appBarState
        )
        else FavoritesState.Items(event.favourites, listState, appBarState)

        is FavoritesEvent.UpdatedWord -> when (this) {
            is FavoritesState.Items -> this.updateFavourite(event.wordUi)
            else -> this
        }

        FavoritesEvent.Loading -> when (this) {
            is FavoritesState.Items -> this
            else -> FavoritesState.Loading(listState, appBarState)
        }

        else -> this
    }

    override suspend fun processAction(action: FavoritesAction) = when (action) {
        FavoritesAction.Initial -> loadData()
        FavoritesAction.OnBack -> FavoritesEvent.Back
        is FavoritesAction.UpdateFavouriteWord -> updateFavourite(action.word)
        is FavoritesAction.OpenWord -> {
            analytics.log(DashboardAnalyticsEvents.OpenWord(action.word))
            FavoritesEvent.OpenWord(action.word)
        }

    }

    override suspend fun handleEvent(event: FavoritesEvent) = when (event) {
        FavoritesEvent.Back -> FavoritesEffect.OnBack()
        is FavoritesEvent.OpenWord -> FavoritesEffect.OpenWord(event.wordUi)
        else -> null
    }

    fun onBack() = handleAction(FavoritesAction.OnBack)
    fun onUpdateFavourite(word: WordCombinedUi) =
        handleAction(FavoritesAction.UpdateFavouriteWord(word))

    fun onOpenWord(word: WordCombinedUi) = handleAction(FavoritesAction.OpenWord(word))
    fun onShareWord(word: WordCombinedUi) = handleAction(FavoritesAction.UpdateFavouriteWord(word))

    private fun loadData(): FavoritesEvent {
        favouritesJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            val favourites = getAllFavouritesUseCase(Unit).fastMap {
                ensureActive()
                it.toUi()
            }
            withContext(Dispatchers.Main.immediate) {
                ensureActive()
                handleState(FavoritesEvent.Updated(favourites))
            }
        }

        return FavoritesEvent.Loading
    }

    private suspend fun updateFavourite(word: WordCombinedUi): FavoritesEvent {
        updateFavouriteUseCase(UpdateFavouriteUseCase.Param(word.word, word.isFavorite))
        return FavoritesEvent.UpdatedWord(word.copy(isFavorite = word.isFavorite))
    }
}