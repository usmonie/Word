package com.usmonie.word.features.dictionary.data.db.room.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock

@Entity(tableName = "favorites_table")
internal data class WordFavorite(
    @PrimaryKey
    val word: String,
    val date: Long = Clock.System.now().epochSeconds
)
