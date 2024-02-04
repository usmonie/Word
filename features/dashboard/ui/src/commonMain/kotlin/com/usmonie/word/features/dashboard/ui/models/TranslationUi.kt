package com.usmonie.word.features.dashboard.ui.models

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
)