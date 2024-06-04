package com.usmonie.word.features.quotes.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.usmonie.word.features.quotes.data.models.QuoteDb

@Dao
internal interface QuotesDao {
    @Insert
    suspend fun insert(quote: QuoteDb)

    @Insert
    suspend fun insert(quotes: List<QuoteDb>)

    @Query("SELECT * FROM quotes ORDER BY RANDOM() LIMIT 1")
    suspend fun randomQuote(): QuoteDb
}
