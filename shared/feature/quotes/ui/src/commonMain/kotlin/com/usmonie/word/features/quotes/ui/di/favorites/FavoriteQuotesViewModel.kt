package com.usmonie.word.features.quotes.ui.di.favorites

import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.qutoes.domain.usecases.GetFavoriteQuotesUseCase
import com.usmonie.word.features.qutoes.domain.usecases.UpdateFavoriteQuoteUseCase

class FavoriteQuotesViewModel(
    private val getFavoritesUseCase: GetFavoriteQuotesUseCase,
    private val updateFavoriteQuoteUseCase: UpdateFavoriteQuoteUseCase
) : StateViewModel<FavoriteQuotesScreenState, FavoriteQuotesScreenAction, FavoriteQuotesScreenEvent, FavoriteQuotesScreenEffect>(
    FavoriteQuotesScreenState(emptyList())
) {
    override fun FavoriteQuotesScreenState.reduce(event: FavoriteQuotesScreenEvent): FavoriteQuotesScreenState {
        return when (event) {
            is FavoriteQuotesScreenEvent.UpdateQuotes -> FavoriteQuotesScreenState(event.quotes)
        }
    }

    override suspend fun processAction(action: FavoriteQuotesScreenAction): FavoriteQuotesScreenEvent {
        return when (action) {
            is FavoriteQuotesScreenAction.FavoriteQuote -> {
                updateFavoriteQuoteUseCase(action.quote)

                val newQuotes =
                    state.value.quotes.fastMap { if (it.id == action.quote.id) it.copy(favorite = !it.favorite) else it }
                FavoriteQuotesScreenEvent.UpdateQuotes(newQuotes)
            }
        }
    }

    override suspend fun handleEvent(event: FavoriteQuotesScreenEvent): FavoriteQuotesScreenEffect? {
        return null
    }
}
