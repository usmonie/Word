package com.usmonie.word.features.new.models

import com.usmonie.word.features.dashboard.domain.models.Translation

data class TranslationUi(
    val id: String,
    val alt: String?,
    val code: String?,
    val english: String?,
    val lang: String?,
    val note: String?,
    val roman: String?,
    val sense: String?,
    val tags: List<String>,
    val taxonomic: String?,
    val topics: List<String>,
    val word: String?,
    val translation: Translation
)