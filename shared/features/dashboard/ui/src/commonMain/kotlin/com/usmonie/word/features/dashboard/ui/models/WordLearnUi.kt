package com.usmonie.word.features.dashboard.ui.models

data class WordLearnUi(
    val wordCombined: WordCombinedUi,
    val lastRepetition: Long,
    val interval: Long,
    val easyFactor: Long
)