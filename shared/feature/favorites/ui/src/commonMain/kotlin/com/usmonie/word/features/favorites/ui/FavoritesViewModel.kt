package com.usmonie.word.features.favorites.ui

import com.usmonie.compass.viewmodel.ContentState
import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.compass.viewmodel.updateData
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.word.features.dictionary.domain.usecases.GetAllFavouritesUseCase
import com.usmonie.word.features.dictionary.domain.usecases.UpdateFavouriteWordUseCase
import com.usmonie.word.features.dictionary.ui.models.WordCombinedUi
import com.usmonie.word.features.dictionary.ui.models.toUi

internal class FavoritesViewModel(
    private val getFavoritesUseCase: GetAllFavouritesUseCase,
    private val updateFavouriteWordUseCase: UpdateFavouriteWordUseCase
) :
    StateViewModel<FavoritesState, FavoritesAction, FavoritesEvent, FavoritesEffect>(
        FavoritesState(ContentState.Loading())
    ) {

    init {
        viewModelScope.launchSafe {
            val words = getFavoritesUseCase().fastMap { it.toUi() }
            handleAction(FavoritesAction.LoadedWords(words))
        }
    }

    override fun FavoritesState.reduce(event: FavoritesEvent): FavoritesState {
        return when (event) {
            is FavoritesEvent.FavoriteWord -> copy(
                words = words.updateData {
                    it.fastMap { word ->
                        if (word.word == event.wordCombined.word) {
                            event.wordCombined
                        } else {
                            word
                        }
                    }
                }
            )

            is FavoritesEvent.LoadedWords -> FavoritesState(ContentState.Success(event.words))
            is FavoritesEvent.OpenWord -> this
        }
    }

    override suspend fun processAction(action: FavoritesAction): FavoritesEvent {
        return when (action) {
            is FavoritesAction.FavoriteWord -> {
                updateFavouriteWordUseCase(
                    UpdateFavouriteWordUseCase.Param(
                        action.wordCombined.word,
                        action.wordCombined.isFavorite
                    )
                )
                FavoritesEvent.FavoriteWord(action.wordCombined.copy(isFavorite = !action.wordCombined.isFavorite))
            }

            is FavoritesAction.LoadedWords -> FavoritesEvent.LoadedWords(action.words)
            is FavoritesAction.OpenWord -> FavoritesEvent.OpenWord(action.wordCombined)
        }
    }

    override suspend fun handleEvent(event: FavoritesEvent): FavoritesEffect? {
        return when (event) {
            is FavoritesEvent.OpenWord -> FavoritesEffect.OpenWord(event.wordCombined)
            else -> null
        }
    }
}

internal fun FavoritesViewModel.openWord(wordCombined: WordCombinedUi) =
    handleAction(FavoritesAction.OpenWord(wordCombined))

internal fun FavoritesViewModel.favoriteWord(wordCombined: WordCombinedUi) =
    handleAction(FavoritesAction.FavoriteWord(wordCombined))
