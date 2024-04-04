package com.usmonie.word.features.dashboard.domain.models

data class WordCombined(
    val wordEtymology: List<WordEtymology>,
    val isFavorite: Boolean,
    val word: String,
)

data class WordEtymology(
    val etymologyText: String?,
    val etymologyNumber: Int?,
    val words: List<Word>,
    val sounds: List<Sound>,
)
