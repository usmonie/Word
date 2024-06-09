package com.usmonie.word.features.quotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.usmonie.word.features.quotes.data.models.Category
import com.usmonie.word.features.quotes.data.models.QuoteCategoryCrossRefDb
import com.usmonie.word.features.quotes.data.models.QuoteDb
import com.usmonie.word.features.quotes.data.models.QuoteFavorite

@Database(
    entities = [QuoteDb::class, Category::class, QuoteCategoryCrossRefDb::class, QuoteFavorite::class],
    version = 11
)
internal abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quotesDao(): QuotesDao
}
