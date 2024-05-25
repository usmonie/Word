package com.usmonie.word.features.dictionary.data.db.room.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.datetime.Clock

@Entity(tableName = "search_history_table")
internal data class SearchHistoryDb(
    @PrimaryKey
    val word: String
) {
    var date: Long = Clock.System.now().epochSeconds
}

internal data class WordSearchHistoryDb(
    @Embedded
    val search: SearchHistoryDb,

    @Relation(parentColumn = "word", entityColumn = "word")
    val words: List<WordDb>
)
