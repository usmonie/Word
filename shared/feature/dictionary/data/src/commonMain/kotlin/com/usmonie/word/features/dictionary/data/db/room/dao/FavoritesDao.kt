package com.usmonie.word.features.dictionary.data.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.usmonie.word.features.dictionary.data.db.room.models.FavoriteWords
import com.usmonie.word.features.dictionary.data.db.room.models.WordFavorite

@Dao
internal interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun favorite(wordFavorite: WordFavorite)

    @Query("DELETE FROM favorites_table WHERE word = :word")
    suspend fun unfavorite(word: String)

    // TODO: Fix that with ksp2 and room
    @Transaction
    @Query("SELECT * FROM favorites_table ORDER BY date DESC")
    suspend fun favorites(): List<FavoriteWords>

    @Query("SELECT * FROM favorites_table WHERE word = :wordQuery")
    suspend fun query(wordQuery: String): WordFavorite?
}
