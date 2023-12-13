package com.usmonie.word.features.new.models

import com.usmonie.word.features.dashboard.domain.models.WordCombined
import com.usmonie.word.features.dashboard.domain.models.WordEtymology

data class WordCombinedUi(
    val word: String,
    val wordEtymology: List<WordEtymologyUi>,
    val isFavorite: Boolean,
    val wordCombined: WordCombined
)

data class WordEtymologyUi(
    val etymologyText: String?,
    val etymologyNumber: Int?,
    val sounds: List<SoundUi>,
    val words: List<WordUi>,
    val wordEtymology: WordEtymology
)