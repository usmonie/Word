package com.usmonie.word.features.new.models

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.models.WordCombined
import com.usmonie.word.features.dashboard.domain.models.WordEtymology

@Stable
data class WordCombinedUi(
    val word: String,
    val wordEtymology: List<WordEtymologyUi>,
    val isFavorite: Boolean,
    val wordCombined: WordCombined
)

@Stable
data class WordEtymologyUi(
    val etymologyText: String?,
    val etymologyNumber: Int?,
    val sounds: List<SoundUi>,
    val words: List<WordUi>,
    val wordEtymology: WordEtymology
)