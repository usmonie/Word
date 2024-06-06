package com.usmonie.word.features.quotes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.usmonie.core.domain.tools.fastForEach
import com.usmonie.word.features.quotes.data.models.Category
import com.usmonie.word.features.quotes.data.models.QuoteCategoryCrossRefDb
import com.usmonie.word.features.quotes.data.models.QuoteDb
import com.usmonie.word.features.quotes.data.models.QuoteFavorite
import com.usmonie.word.features.quotes.data.models.QuoteWithCategories
import com.usmonie.word.features.qutoes.domain.models.Quote

@Suppress("TooManyFunctions")
@Dao
internal abstract class QuotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(quote: QuoteDb): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(quotes: List<QuoteDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCategory(category: Category)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertCategories(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertQuoteReference(quotes: QuoteCategoryCrossRefDb)

    @Transaction
    open suspend fun insertQuotes(quotes: List<Quote>) {
        quotes.fastForEach { quote ->
            val quoteId = insert(QuoteDb(quote.text, quote.author, quote.favorite))
            quote.categories.fastForEach {
                val category = Category(it)
                insertCategory(category)
                insertQuoteReference(QuoteCategoryCrossRefDb(quoteId, it))
            }
        }
    }

    @Transaction
    open suspend fun insertQuote(quote: Quote) {
        val quoteId = insert(QuoteDb(quote.text, quote.author, quote.favorite))
        quote.categories.fastForEach {
            val category = Category(it)
            insertCategory(category)
            insertQuoteReference(QuoteCategoryCrossRefDb(quoteId, it))
        }
    }

    @Query("SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1")
    abstract suspend fun randomQuote(): QuoteWithCategories

    @Transaction
    @Query("SELECT * FROM quotes WHERE author = :author")
    abstract suspend fun getQuotesByAuthor(author: String): List<QuoteWithCategories>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun favoriteQuote(item: QuoteFavorite)

    @Delete
    abstract suspend fun unfavoriteQuote(item: QuoteFavorite)

    @Transaction
    @Query(
        """
            SELECT * FROM quotes 
            INNER JOIN favorite_quotes_table ON quotes.id = favorite_quotes_table.quoteId
        """
    )
    abstract suspend fun getFavorites(): List<QuoteWithCategories>

    @Query("SELECT COUNT(text) FROM quotes")
    abstract suspend fun getRowCount(): Long
}
