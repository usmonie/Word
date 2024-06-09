package com.usmonie.word.features.quotes.data.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "quotes", indices = [Index(value = ["author"])])
data class QuoteDb(
    val text: String,
    val author: String?,
    val favorite: Boolean,
    val wasPlayed: Boolean,
    @PrimaryKey
    val primaryKey: String = (text.hashCode() + author.hashCode()).toString()
)

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val category: String
)
