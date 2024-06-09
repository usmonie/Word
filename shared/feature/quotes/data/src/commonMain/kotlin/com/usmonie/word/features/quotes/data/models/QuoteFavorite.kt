package com.usmonie.word.features.quotes.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.datetime.Clock
@Entity(tableName = "favorite_quotes_table")
internal data class QuoteFavorite(
    @PrimaryKey
    val quotePrimaryKey: String,
    val date: Long = Clock.System.now().toEpochMilliseconds()
)

internal data class FavoriteQuotes(
    @Embedded
    val quoteFavorite: QuoteFavorite,

    @Relation(
        parentColumn = "quotePrimaryKey",
        entityColumn = "primaryKey"
    )
    val quote: QuoteDb,

    @Relation(
        parentColumn = "quotePrimaryKey",
        entityColumn = "primaryKey"
    )
    val categories: List<QuoteCategoryCrossRefDb>
)

