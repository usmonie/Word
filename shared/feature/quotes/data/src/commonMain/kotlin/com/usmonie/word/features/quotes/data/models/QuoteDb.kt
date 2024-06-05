package com.usmonie.word.features.quotes.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "quotes", indices = [Index(value = ["author"])])
data class QuoteDb(
    val text: String,
    val author: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val category: String
)
