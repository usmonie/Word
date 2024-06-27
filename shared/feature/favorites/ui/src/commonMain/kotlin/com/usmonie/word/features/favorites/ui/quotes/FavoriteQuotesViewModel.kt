package com.usmonie.word.features.favorites.ui.quotes

import androidx.compose.runtime.Immutable
import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.word.features.quotes.kit.di.Categories
import com.usmonie.word.features.quotes.kit.di.Quotes
import com.usmonie.word.features.quotes.domain.models.Quote
import com.usmonie.word.features.quotes.domain.usecases.GetFavoriteQuotesUseCase
import com.usmonie.word.features.quotes.domain.usecases.UpdateFavoriteQuoteUseCase

@Immutable
internal class FavoriteQuotesViewModel(
    private val getFavoritesUseCase: GetFavoriteQuotesUseCase,
//    private val getFavoriteCategoriesUseCase: GetFavoriteQuotesUseCase,
    private val updateFavoriteQuoteUseCase: UpdateFavoriteQuoteUseCase
) : StateViewModel<FavoriteQuotesScreenState, FavoriteQuotesScreenAction, FavoriteQuotesScreenEvent, FavoriteQuotesScreenEffect>(
    FavoriteQuotesScreenState(Categories(emptyList()), Quotes(emptyList()))
) {

    init {
        handleAction(FavoriteQuotesScreenAction.Init)
    }

    override fun FavoriteQuotesScreenState.reduce(event: FavoriteQuotesScreenEvent): FavoriteQuotesScreenState {
        return when (event) {
            is FavoriteQuotesScreenEvent.UpdateQuotes -> copy(categories = Categories(event.categories), quotes = Quotes(event.quotes))
            is FavoriteQuotesScreenEvent.SelectCategory -> TODO()
        }
    }

    override suspend fun processAction(action: FavoriteQuotesScreenAction): FavoriteQuotesScreenEvent {
        return when (action) {
            is FavoriteQuotesScreenAction.OnFavoriteQuote -> {
                updateFavoriteQuoteUseCase(action.quote)

                val newQuotes = state.value.quotes.quotes.fastMap {
                    if (it.id == action.quote.id) {
                        it.copy(favorite = !it.favorite)
                    } else {
                        it
                    }
                }
                FavoriteQuotesScreenEvent.UpdateQuotes(emptyList(), newQuotes)
            }

            is FavoriteQuotesScreenAction.OnSelectedCategory -> FavoriteQuotesScreenEvent.SelectCategory(action.category)
            FavoriteQuotesScreenAction.Init -> {
                val quotes = getFavoritesUseCase()
                FavoriteQuotesScreenEvent.UpdateQuotes(emptyList(), quotes)
            }
        }
    }

    override suspend fun handleEvent(event: FavoriteQuotesScreenEvent): FavoriteQuotesScreenEffect? {
        return null
    }
}

internal fun FavoriteQuotesViewModel.onSelectCategory(category: String) =
    handleAction(FavoriteQuotesScreenAction.OnSelectedCategory(category))

internal fun FavoriteQuotesViewModel.onFavoriteQuote(quote: Quote) =
    handleAction(FavoriteQuotesScreenAction.OnFavoriteQuote(quote))