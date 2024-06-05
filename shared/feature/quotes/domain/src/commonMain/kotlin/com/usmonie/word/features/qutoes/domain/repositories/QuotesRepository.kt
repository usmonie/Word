package com.usmonie.word.features.qutoes.domain.repositories

import com.usmonie.word.features.qutoes.domain.models.Quote

interface QuotesRepository {

    suspend fun putAll(quotes: List<Quote>)

    suspend fun getRandomQuote(): Quote

    suspend fun getQuotes(author: String): List<Quote>

    suspend fun getQuotesByCategory(category: String): List<Quote>

    suspend fun getQuotesCount(): Long
}
