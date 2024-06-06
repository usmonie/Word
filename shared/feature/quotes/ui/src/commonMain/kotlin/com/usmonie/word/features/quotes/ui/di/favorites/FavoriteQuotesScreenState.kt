package com.usmonie.word.features.quotes.ui.di.favorites

import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.word.features.qutoes.domain.models.Quote

data class FavoriteQuotesScreenState(
    val quotes: List<Quote>
) : ScreenState

sealed class FavoriteQuotesScreenAction : ScreenAction {
    data class FavoriteQuote(val quote: Quote) : FavoriteQuotesScreenAction()
}

sealed class FavoriteQuotesScreenEvent : ScreenEvent {
    data class UpdateQuotes(val quotes: List<Quote>) : FavoriteQuotesScreenEvent()
}

sealed class FavoriteQuotesScreenEffect : ScreenEffect
