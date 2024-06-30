package com.usmonie.word.features.quotes.data

import androidx.room.*
import com.usmonie.word.features.quotes.data.models.*
import com.usmonie.word.features.quotes.domain.models.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

@Dao
internal abstract class QuotesDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun insertQuotes(quotes: List<QuoteDb>)

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract suspend fun insertCategories(categories: List<Category>)

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	abstract suspend fun insertQuoteCategoryReferences(references: List<QuoteCategoryCrossRefDb>)

	@Transaction
	open suspend fun insertQuotesBatch(quotes: List<Quote>) = withContext(Dispatchers.IO) {
		val quoteDbList = ArrayList<QuoteDb>(quotes.size)
		val categorySet = HashSet<Category>()
		val referenceList = ArrayList<QuoteCategoryCrossRefDb>()

		quotes.forEach { quote ->
			val quoteDb = QuoteDb(quote.text, quote.author, quote.favorite, false)
			quoteDbList.add(quoteDb)
			quote.categories.forEach { categoryName ->
				categorySet.add(Category(categoryName))
				referenceList.add(QuoteCategoryCrossRefDb(quoteDb.primaryKey, categoryName))
			}
		}

		insertQuotes(quoteDbList)
		insertCategories(categorySet.toList())
		insertQuoteCategoryReferences(referenceList)
	}

	@Query("SELECT * FROM quotes WHERE wasPlayed = :wasPlayed ORDER BY RANDOM() LIMIT 1")
	abstract suspend fun getRandomQuote(wasPlayed: Boolean = false): QuoteDb?

	@Transaction
	open suspend fun getRandomQuoteWithCategories(wasPlayed: Boolean = false): QuoteWithCategories? {
		val quote = getRandomQuote(wasPlayed) ?: return null
		val categories = getCategoriesForQuote(quote.primaryKey)
		return QuoteWithCategories(quote, categories)
	}

	@Query("SELECT category FROM quote_category_cross_ref WHERE quotePrimaryKey = :quoteKey")
	abstract suspend fun getCategoriesForQuote(quoteKey: String): List<Category>

	@Query("UPDATE quotes SET wasPlayed = :wasPlayed WHERE primaryKey = :quoteKey")
	abstract suspend fun updateQuotePlayedStatus(quoteKey: String, wasPlayed: Boolean)

	@Transaction
	@Query("SELECT * FROM quotes WHERE author = :author")
	abstract suspend fun getQuotesByAuthor(author: String): List<QuoteWithCategories>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	abstract suspend fun insertFavoriteQuote(item: QuoteFavorite)

	@Query("DELETE FROM favorite_quotes WHERE quotePrimaryKey = :quoteKey")
	abstract suspend fun deleteFavoriteQuote(quoteKey: String)

	@Transaction
	@Query(
		"""
        SELECT favorite_quotes.*, quotes.*
        FROM favorite_quotes
        INNER JOIN quotes ON favorite_quotes.quotePrimaryKey = quotes.primaryKey
        ORDER BY favorite_quotes.date DESC
    """
	)
	@RewriteQueriesToDropUnusedColumns
	abstract suspend fun getFavoriteQuotesWithDetails(): List<FavoriteQuoteWithDetails>

	@Query("SELECT COUNT(*) FROM quotes")
	abstract suspend fun getQuotesCount(): Long

	@Query("SELECT * FROM quotes WHERE favorite = 1")
	abstract suspend fun getFavoriteQuotes(): List<QuoteDb>

	@Transaction
	@Query(
		"""
        SELECT * FROM categories
        WHERE category IN (
            SELECT DISTINCT category FROM quote_category_cross_ref
            INNER JOIN quotes ON quote_category_cross_ref.quotePrimaryKey = quotes.primaryKey
            WHERE quotes.favorite = 1
        )
    """
	)
	abstract suspend fun getFavoriteCategoriesWithQuotes(): List<CategoryWithQuotes>

	@Transaction
	open suspend fun toggleFavoriteStatus(quoteKey: String, isFavorite: Boolean) {
		if (isFavorite) {
			insertFavoriteQuote(QuoteFavorite(quoteKey))
		} else {
			deleteFavoriteQuote(quoteKey)
		}
		updateQuoteFavoriteStatus(quoteKey, isFavorite)
	}

	@Query("UPDATE quotes SET favorite = :isFavorite WHERE primaryKey = :quoteKey")
	abstract suspend fun updateQuoteFavoriteStatus(quoteKey: String, isFavorite: Boolean)

	@Query("SELECT * FROM quotes WHERE text LIKE '%' || :query || '%' OR author LIKE '%' || :query || '%'")
	abstract suspend fun searchQuotes(query: String): List<QuoteDb>

	@Transaction
	@Query(
		"""
        SELECT * FROM quotes
        WHERE primaryKey IN (
            SELECT DISTINCT quotePrimaryKey FROM quote_category_cross_ref
            WHERE category = :category
        )
    """
	)
	abstract suspend fun getQuotesByCategory(category: String): List<QuoteWithCategories>
}