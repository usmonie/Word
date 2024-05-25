package com.usmonie.word.features.dictionary.data.db.room.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "favorites_table")
internal data class WordFavorite(
    @PrimaryKey
    val word: String,
)

internal data class FavoriteWords(
    @Embedded
    val word: WordFavorite,

    @Relation(parentColumn = "word", entityColumn = "word")
    val words: List<WordDb>
)