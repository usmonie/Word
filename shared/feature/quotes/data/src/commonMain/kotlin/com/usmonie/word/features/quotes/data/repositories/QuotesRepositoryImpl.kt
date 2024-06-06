package com.usmonie.word.features.quotes.data.repositories

import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.quotes.data.QuotesDatabase
import com.usmonie.word.features.quotes.data.models.QuoteFavorite
import com.usmonie.word.features.quotes.data.models.QuoteWithCategories
import com.usmonie.word.features.qutoes.domain.models.Quote
import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository

internal class QuotesRepositoryImpl(quotesDatabase: QuotesDatabase) : QuotesRepository {
    private val quotesDao = quotesDatabase.quotesDao()

    override suspend fun putAll(quotes: List<Quote>) {
        quotesDao.insertQuotes(quotes)
    }

    override suspend fun getRandomQuote(): Quote {
        println("QUOTES_COUNT: ${quotesDao.getRowCount()}")
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

    override suspend fun updateQuote(quote: Quote) {
        quotesDao.insertQuote(quote)
    }

    override suspend fun favorite(quote: Quote) {
        quotesDao.favoriteQuote(QuoteFavorite(quoteId = quote.id))
    }

    override suspend fun unfavorite(quote: Quote) {
        quotesDao.unfavoriteQuote(QuoteFavorite(quoteId = quote.id))
    }

    override suspend fun getFavorites(): List<Quote> {
        return quotesDao.getFavorites().fastMap { it.map() }
    }
}

fun QuoteWithCategories.map() = Quote(
    quote.id,
    quote.text,
    quote.author,
    categories.fastMap { it.category },
    quote.favorite
)
