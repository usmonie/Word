package com.usmonie.word.features.quotes.data.repositories

import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.quotes.data.QuotesDatabase
import com.usmonie.word.features.quotes.data.models.*
import com.usmonie.word.features.quotes.domain.models.Quote
import com.usmonie.word.features.quotes.domain.models.QuoteCategories
import com.usmonie.word.features.quotes.domain.repositories.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class QuotesRepositoryImpl(private val quotesDatabase: QuotesDatabase) : QuotesRepository {
    private val quotesDao = quotesDatabase.quotesDao()

    override suspend fun putAll(quotes: List<Quote>) = withContext(Dispatchers.IO) {
        quotesDao.insertQuotesBatch(quotes)
    }

    override suspend fun getRandomQuote(): Quote = withContext(Dispatchers.IO) {
        quotesDao.getRandomQuoteWithCategories(wasPlayed = false)?.map()
            ?: throw NoSuchElementException("No unplayed quotes found")
    }

    override suspend fun getRandomWasntPlayedQuote(): Quote {
        return quotesDao.getRandomQuoteWithCategories(wasPlayed = false)?.map()
            ?: throw NoSuchElementException("No unplayed quotes found")
    }

    override suspend fun getQuotes(author: String): List<Quote> = withContext(Dispatchers.IO) {
        quotesDao.getQuotesByAuthor(author).fastMap { it.map() }
    }

    override suspend fun getQuotesByCategory(category: String): List<Quote> = withContext(Dispatchers.IO) {
        quotesDao.getQuotesByCategory(category).fastMap { it.map() }
    }

    override suspend fun getQuotesCount(): Long = withContext(Dispatchers.IO) {
        quotesDao.getQuotesCount()
    }

    override suspend fun updateQuote(quote: Quote) = withContext(Dispatchers.IO) {
        val quoteDb = QuoteDb(quote.text, quote.author, quote.favorite, quote.wasPlayed)
        quotesDao.insertQuotes(listOf(quoteDb))
    }

    override suspend fun favorite(quote: Quote) = withContext(Dispatchers.IO) {
        quotesDao.toggleFavoriteStatus(quote.id, true)
    }

    override suspend fun unfavorite(quote: Quote) = withContext(Dispatchers.IO) {
        quotesDao.toggleFavoriteStatus(quote.id, false)
    }

    override suspend fun getFavorites(): List<Quote> = withContext(Dispatchers.IO) {
        quotesDao.getFavoriteQuotesWithDetails().fastMap { it.map() }
    }

    override suspend fun getFavoritesByCategories(): List<QuoteCategories> = withContext(Dispatchers.IO) {
        quotesDao.getFavoriteCategoriesWithQuotes().fastMap { it.map() }
    }
}

fun QuoteWithCategories.map() = Quote(
    id = quote.primaryKey,
    text = quote.text,
    author = quote.author,
    categories = categories.fastMap { it.category },
    favorite = quote.favorite,
    wasPlayed = quote.wasPlayed
)

fun FavoriteQuoteWithDetails.map() = Quote(
    id = quote.primaryKey,
    text = quote.text,
    author = quote.author,
    categories = categories.fastMap { it.category },
    favorite = true,
    wasPlayed = quote.wasPlayed
)

fun CategoryWithQuotes.map() = QuoteCategories(
    category = category.category,
    quotes = quotes.fastMap {
        Quote(
            id = it.primaryKey,
            text = it.text,
            author = it.author,
            categories = emptyList(), // We don't have categories here, so we use an empty list
            favorite = it.favorite,
            wasPlayed = it.wasPlayed
        )
    }
)