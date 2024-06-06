package com.usmonie.word.features.quotes.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.datetime.Clock

@Entity(tableName = "favorite_quotes_table")
internal data class QuoteFavorite(
    @PrimaryKey
    val quoteId: Long,
    val date: Long = Clock.System.now().epochSeconds
)

internal data class FavoriteQuotes(
    @Embedded
    val quoteFavorite: QuoteFavorite,

    @Relation(
        parentColumn = "quoteId",
        entityColumn = "id"
    )
    val quote: QuoteDb,

    @Relation(
        parentColumn = "quoteId",
        entityColumn = "id"
    )
    val categories: List<QuoteCategoryCrossRefDb>
)

