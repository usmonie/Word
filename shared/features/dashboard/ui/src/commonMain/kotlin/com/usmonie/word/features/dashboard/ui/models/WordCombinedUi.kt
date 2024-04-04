package com.usmonie.word.features.dashboard.ui.models

import androidx.compose.runtime.Stable

@Stable
data class WordCombinedUi(
    val word: String,
    val wordEtymology: List<WordEtymologyUi>,
    val isFavorite: Boolean,
)

@Stable
data class WordEtymologyUi(
    val etymologyText: String?,
    val etymologyNumber: Int?,
    val sounds: List<SoundUi>,
    val words: List<WordUi>,
)