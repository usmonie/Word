package com.usmonie.word.features.quotes.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["primaryKey", "category"])
data class QuoteCategoryCrossRefDb(
    val primaryKey: String,
    val category: String
)

class QuoteWithCategories(
    @Embedded
    val quote: QuoteDb,
    @Relation(
        parentColumn = "primaryKey",
        entityColumn = "category",
        associateBy = Junction(QuoteCategoryCrossRefDb::class)
    )
    val categories: List<Category>
)

class CategoryWithQuotes(
    @Embedded
    val category: Category,
    @Relation(
        parentColumn = "primaryKey",
        entityColumn = "category",
        associateBy = Junction(QuoteCategoryCrossRefDb::class)
    )
    val quotes: List<QuoteDb>
)
