package com.usmonie.word.features.quotes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes", primaryKeys = ["text", "author"])
data class QuoteDb(
    val text: String,
    val author: String,
)

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val category: String
)
