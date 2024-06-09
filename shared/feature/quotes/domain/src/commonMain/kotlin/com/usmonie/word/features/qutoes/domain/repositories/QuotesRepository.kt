package com.usmonie.word.features.qutoes.domain.repositories

import com.usmonie.word.features.qutoes.domain.models.Quote
import com.usmonie.word.features.qutoes.domain.models.QuoteCategories

interface QuotesRepository {

    suspend fun putAll(quotes: List<Quote>)

    suspend fun getRandomQuote(): Quote
    suspend fun getRandomWasntPlayedQuote(): Quote

    suspend fun getQuotes(author: String): List<Quote>

    suspend fun getQuotesByCategory(category: String): List<Quote>

    suspend fun getQuotesCount(): Long

    suspend fun updateQuote(quote: Quote)

    suspend fun favorite(quote: Quote)

    suspend fun unfavorite(quote: Quote)

    suspend fun getFavorites(): List<Quote>

    suspend fun getFavoritesByCategories(): List<QuoteCategories>
}
