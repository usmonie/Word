package com.usmonie.word.features.favorites.ui

import com.usmonie.compass.viewmodel.StateViewModel

internal class FavoritesViewModel :
    StateViewModel<FavoritesState, FavoritesAction, FavoritesEvent, FavoritesEffect>(FavoritesState()) {

    init {
        viewModelScope.launchSafe {
        }
    }

    override fun FavoritesState.reduce(event: FavoritesEvent): FavoritesState {
        return this
    }

    override suspend fun processAction(action: FavoritesAction): FavoritesEvent {
        return FavoritesEvent()
    }

    override suspend fun handleEvent(event: FavoritesEvent): FavoritesEffect? {
        return null
    }
}
