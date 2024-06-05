package com.usmonie.word.features.quotes.data.repositories

import com.usmonie.word.features.quotes.data.QuotesDatabase
import com.usmonie.word.features.quotes.data.models.QuoteWithCategories
import com.usmonie.word.features.qutoes.domain.models.Quote
import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository
import wtf.word.core.domain.tools.fastMap

internal class QuotesRepositoryImpl(quotesDatabase: QuotesDatabase) : QuotesRepository {
    private val quotesDao = quotesDatabase.quotesDao()

    override suspend fun putAll(quotes: List<Quote>) {
        quotesDao.insertQuotes(quotes)
    }

    override suspend fun getRandomQuote(): Quote {
        return quotesDao.randomQuote().map()
    }

    override suspend fun getQuotes(author: String): List<Quote> {
        return quotesDao.getQuotesByAuthor(author).fastMap { it.map() }
    }

    override suspend fun getQuotesByCategory(category: String): List<Quote> {
        return quotesDao.getQuotesByAuthor(category).fastMap { it.map() }
    }

    override suspend fun getQuotesCount(): Long {
        return quotesDao.getRowCount()
    }
}

fun QuoteWithCategories.map() = Quote(
    quote.text,
    quote.author,
    categories.fastMap { it.category }
)
