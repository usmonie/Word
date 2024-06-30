package com.usmonie.word.features.quotes.data.models

import androidx.room.*
import kotlinx.datetime.Clock

@Entity(
	tableName = "quotes",
	indices = [
		Index(value = ["author"]),
		Index(value = ["wasPlayed"]),
		Index(value = ["favorite"])
	]
)
data class QuoteDb(
	@PrimaryKey
	val primaryKey: String,
	val text: String,
	val author: String?,
	val favorite: Boolean,
	val wasPlayed: Boolean
) {
	constructor(text: String, author: String?, favorite: Boolean, wasPlayed: Boolean) : this(
		primaryKey = generatePrimaryKey(text, author),
		text = text,
		author = author,
		favorite = favorite,
		wasPlayed = wasPlayed
	)

	companion object {
		fun generatePrimaryKey(text: String, author: String?): String =
			(text.hashCode() + (author?.hashCode() ?: 0)).toString()
	}
}

@Entity(tableName = "categories")
data class Category(
	@PrimaryKey
	val category: String
)

@Entity(
	tableName = "quote_category_cross_ref",
	primaryKeys = ["quotePrimaryKey", "category"],
	indices = [Index("category")]
)
data class QuoteCategoryCrossRefDb(
	val quotePrimaryKey: String,
	val category: String
)

@Entity(
	tableName = "favorite_quotes",
	indices = [Index("date")]
)
data class QuoteFavorite(
	@PrimaryKey
	val quotePrimaryKey: String,
	val date: Long = Clock.System.now().toEpochMilliseconds()
)

data class QuoteWithCategories(
	@Embedded val quote: QuoteDb,
	@Relation(
		parentColumn = "primaryKey",
		entityColumn = "category",
		associateBy = Junction(
			value = QuoteCategoryCrossRefDb::class,
			parentColumn = "quotePrimaryKey",
			entityColumn = "category"
		)
	)
	val categories: List<Category>
)

data class CategoryWithQuotes(
	@Embedded val category: Category,
	@Relation(
		parentColumn = "category",
		entityColumn = "primaryKey",
		associateBy = Junction(
			value = QuoteCategoryCrossRefDb::class,
			parentColumn = "category",
			entityColumn = "quotePrimaryKey"
		)
	)
	val quotes: List<QuoteDb>
)

data class FavoriteQuoteWithDetails(
	@Embedded val favorite: QuoteFavorite,
	@Relation(
		entity = QuoteDb::class,
		parentColumn = "quotePrimaryKey",
		entityColumn = "primaryKey"
	)
	val quote: QuoteDb,
	@Relation(
		entity = Category::class,
		parentColumn = "quotePrimaryKey",
		entityColumn = "category",
		associateBy = Junction(
			value = QuoteCategoryCrossRefDb::class,
			parentColumn = "quotePrimaryKey",
			entityColumn = "category"
		)
	)
	val categories: List<Category>
)
