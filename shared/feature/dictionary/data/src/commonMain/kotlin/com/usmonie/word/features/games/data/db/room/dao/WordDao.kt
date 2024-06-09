package com.usmonie.word.features.games.data.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.usmonie.word.features.games.data.db.room.models.SearchHistoryDb
import com.usmonie.word.features.games.data.db.room.models.WordDb
import com.usmonie.word.features.games.data.db.room.models.WordSearchHistoryDb

@Dao
internal interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(words: List<WordDb>)

    @Query("SELECT * FROM words_table WHERE word = :wordQuery")
    suspend fun query(wordQuery: String): List<WordDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(searchHistoryDb: SearchHistoryDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(searchHistoryDb: List<SearchHistoryDb>)

    @Transaction
    @Query("SELECT * FROM search_history_table ORDER BY date DESC")
    suspend fun searchHistory(): List<WordSearchHistoryDb>

    @Query("DELETE FROM search_history_table")
    suspend fun clearSearchHistory()
}
