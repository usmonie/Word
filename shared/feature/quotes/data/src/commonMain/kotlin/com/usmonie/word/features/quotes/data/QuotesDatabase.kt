package com.usmonie.word.features.quotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.usmonie.word.features.quotes.data.models.QuoteDb

@Database(
    entities = [QuoteDb::class],
    version = 1
)
internal abstract class QuotesDatabase : RoomDatabase() {
    abstract fun quotesDao(): QuotesDao
}
