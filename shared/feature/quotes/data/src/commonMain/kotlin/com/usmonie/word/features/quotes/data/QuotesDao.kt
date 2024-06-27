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
import com.usmonie.word.features.quotes.domain.models.Quote

@Suppress("TooManyFunctions")
@Dao
internal abstract class QuotesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(quote: QuoteDb)

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
            val quoteDb = QuoteDb(quote.text, quote.author, quote.favorite, false)
            insert(quoteDb)
            quote.categories.fastForEach {
                val category = Category(it)
                insertCategory(category)
                insertQuoteReference(QuoteCategoryCrossRefDb(quoteDb.primaryKey, it))
            }
        }
    }

    @Transaction
    open suspend fun insertQuote(quote: Quote) {
        val quoteDb = QuoteDb(quote.text, quote.author, quote.favorite, false)
        insert(quoteDb)
        quote.categories.fastForEach {
            val category = Category(it)
            insertCategory(category)
            insertQuoteReference(QuoteCategoryCrossRefDb(primaryKey = quoteDb.primaryKey, it))
        }
    }

    @Query("SELECT * FROM quotes LIMIT 1 OFFSET :offset")
    abstract suspend fun randomQuote(offset: Int): QuoteWithCategories

    @Query("SELECT * FROM quotes WHERE wasPlayed = false LIMIT 1 OFFSET :offset")
    abstract suspend fun randomQuoteWasntPlayed(offset: Int): QuoteWithCategories

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
            INNER JOIN favorite_quotes_table ON quotes.primaryKey = favorite_quotes_table.quotePrimaryKey
        """
    )
    abstract suspend fun getFavorites(): List<QuoteWithCategories>

    @Transaction
    @Query(
        """
            SELECT * FROM quotes 
            INNER JOIN favorite_quotes_table ON quotes.primaryKey = favorite_quotes_table.quotePrimaryKey
            ORDER BY date DESC
        """
    )
    abstract suspend fun getFavoritesByCategories(): List<QuoteWithCategories>

    @Query("SELECT COUNT(text) FROM quotes")
    abstract suspend fun getRowCount(): Long
}
