package com.usmonie.word.features.games.data.db.room.models

import androidx.room.Embedded
import androidx.room.Relation

internal class FavoriteWords(
    @Embedded
    val word: WordFavorite,

    @Relation(parentColumn = "word", entityColumn = "word")
    val words: List<WordDb>
)
