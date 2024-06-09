package com.usmonie.word.features.favorites.ui.quotes

import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.word.features.quotes.kit.di.Categories
import com.usmonie.word.features.quotes.kit.di.Quotes
import com.usmonie.word.features.qutoes.domain.models.Quote

data class FavoriteQuotesScreenState(
    val categories: Categories,
    val quotes: Quotes,
    val selectedCategory: String? = null,
) : ScreenState

sealed class FavoriteQuotesScreenAction : ScreenAction {
    data object Init : FavoriteQuotesScreenAction()

    data class OnFavoriteQuote(val quote: Quote) : FavoriteQuotesScreenAction()
    data class OnSelectedCategory(val category: String?) : FavoriteQuotesScreenAction()
}

sealed class FavoriteQuotesScreenEvent : ScreenEvent {
    data class UpdateQuotes(val categories: List<String>, val quotes: List<Quote>) : FavoriteQuotesScreenEvent()
    data class SelectCategory(val category: String?) : FavoriteQuotesScreenEvent()
}

sealed class FavoriteQuotesScreenEffect : ScreenEffect
