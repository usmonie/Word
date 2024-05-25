package com.usmonie.word.features.dictionary.data.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.usmonie.word.features.dictionary.data.db.room.models.FavoriteWords
import com.usmonie.word.features.dictionary.data.db.room.models.WordFavorite

@Dao
internal interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun favorite(wordFavorite: WordFavorite)

    @Query("DELETE FROM favorites_table WHERE word = :word")
    suspend fun unfavorite(word: String)

    @Query("SELECT * FROM favorites_table")
    suspend fun favorites(): List<FavoriteWords>

    @Query("SELECT * FROM favorites_table WHERE word = :wordQuery")
    suspend fun query(wordQuery: String): WordFavorite?
}
