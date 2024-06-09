package com.usmonie.word.features.quotes.data.repositories

import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.quotes.data.QuotesDatabase
import com.usmonie.word.features.quotes.data.models.CategoryWithQuotes
import com.usmonie.word.features.quotes.data.models.QuoteDb
import com.usmonie.word.features.quotes.data.models.QuoteFavorite
import com.usmonie.word.features.quotes.data.models.QuoteWithCategories
import com.usmonie.word.features.quotes.data.usecases.QUOTES_COUNT
import com.usmonie.word.features.qutoes.domain.models.Quote
import com.usmonie.word.features.qutoes.domain.models.QuoteCategories
import com.usmonie.word.features.qutoes.domain.repositories.QuotesRepository
import kotlin.random.Random

@Suppress("TooManyFunctions")
internal class QuotesRepositoryImpl(quotesDatabase: QuotesDatabase) : QuotesRepository {
    private val quotesDao = quotesDatabase.quotesDao()

    override suspend fun putAll(quotes: List<Quote>) {
        quotesDao.insertQuotes(quotes)
    }

    override suspend fun getRandomQuote(): Quote {
        val offset = Random.nextInt(0, QUOTES_COUNT.toInt())
        return quotesDao.randomQuote(offset).map()
    }

    override suspend fun getRandomWasntPlayedQuote(): Quote {
        val offset = Random.nextInt(0, QUOTES_COUNT.toInt())
        return quotesDao.randomQuoteWasntPlayed(offset).map()
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
        val quoteDb = QuoteDb(quote.text, quote.author, quote.favorite, quote.wasPlayed, quote.id)
        quotesDao.insert(quoteDb)
        quotesDao.favoriteQuote(QuoteFavorite(quotePrimaryKey = quote.id))
    }

    override suspend fun unfavorite(quote: Quote) {
        val quoteDb = QuoteDb(quote.text, quote.author, quote.favorite, quote.wasPlayed, quote.id)
        quotesDao.insert(quoteDb)
        quotesDao.unfavoriteQuote(QuoteFavorite(quotePrimaryKey = quote.id))
    }

    override suspend fun getFavorites(): List<Quote> {
        return quotesDao.getFavorites().fastMap { println("FAV_QUOTES_${it.quote.favorite}"); it.map() }
    }

    override suspend fun getFavoritesByCategories(): List<QuoteCategories> {
        return emptyList() // quotesDao.getFavoritesByCategories().fastMap { it.map() }
    }
}

fun QuoteWithCategories.map() = Quote(
    quote.primaryKey,
    quote.text,
    quote.author,
    categories.fastMap { it.category },
    quote.favorite,
    quote.wasPlayed
)

fun CategoryWithQuotes.map() = QuoteCategories(
    category.category,
    emptyList(),
)
