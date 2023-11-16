package com.usmonie.word.features.favourites

import com.usmonie.word.features.dashboard.domain.usecase.GetAllFavouritesUseCase
import com.usmonie.word.features.dashboard.domain.usecase.UpdateFavouriteUseCase
import com.usmonie.word.features.models.SynonymUi
import com.usmonie.word.features.models.WordUi
import com.usmonie.word.features.models.to
import com.usmonie.word.features.models.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wtf.speech.core.ui.BaseViewModel

class FavouritesViewModel(
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val getAllFavouritesUseCase: GetAllFavouritesUseCase,
) : BaseViewModel<FavouritesState, FavouritesAction, FavouritesEvent, FavouritesEffect>(
    FavouritesState.Loading(),
) {

    private var favouritesJob: Job? = null

    init {
        handleAction(FavouritesAction.Initial)
    }

    override fun FavouritesState.reduce(event: FavouritesEvent): FavouritesState {
        return when (event) {
            is FavouritesEvent.Next -> TODO()
            is FavouritesEvent.Updated -> if (event.favourites.isEmpty()) FavouritesState.Empty()
            else FavouritesState.Items(event.favourites)

            is FavouritesEvent.UpdatedWord -> when (this) {
                is FavouritesState.Items -> this.updateFavourite(event.wordUi)
                else -> this
            }

            FavouritesEvent.Loading -> when (this) {
                is FavouritesState.Items -> this
                else -> FavouritesState.Loading()
            }

            else -> this
        }
    }

    override suspend fun processAction(action: FavouritesAction) = when (action) {
        FavouritesAction.Initial -> loadData()
        FavouritesAction.OnBack -> FavouritesEvent.Back
        is FavouritesAction.UpdateFavouriteWord -> updateFavourite(action.wordUi)
        is FavouritesAction.OpenWord -> FavouritesEvent.OpenWord(action.wordUi)
    }

    override suspend fun handleEvent(event: FavouritesEvent) = when (event) {
        FavouritesEvent.Back -> FavouritesEffect.OnBack()
        is FavouritesEvent.OpenWord -> FavouritesEffect.OpenWord(event.wordUi)
        else -> null
    }

    fun onBack() = handleAction(FavouritesAction.OnBack)
    fun onUpdateFavourite(word: WordUi) = handleAction(FavouritesAction.UpdateFavouriteWord(word))
    fun onOpenWord(word: WordUi) = handleAction(FavouritesAction.OpenWord(word))
    fun onShareWord(word: WordUi) = handleAction(FavouritesAction.UpdateFavouriteWord(word))
    fun onSynonym(synonym: SynonymUi) {}

    private fun loadData(): FavouritesEvent {
        favouritesJob?.cancel()
        viewModelScope.launch(Dispatchers.IO) {
            ensureActive()
            val favourites = getAllFavouritesUseCase(Unit).map {
                ensureActive()
                it.to()
            }
            withContext(Dispatchers.Main.immediate) {
                ensureActive()
                handleState(FavouritesEvent.Updated(favourites))
            }
        }

        return FavouritesEvent.Loading
    }

    private suspend fun updateFavourite(word: WordUi): FavouritesEvent {
        updateFavouriteUseCase(UpdateFavouriteUseCase.Param(word.toDomain()))

        return FavouritesEvent.UpdatedWord(
            word.copy(isFavourite = !word.isFavourite),
        )
    }
}