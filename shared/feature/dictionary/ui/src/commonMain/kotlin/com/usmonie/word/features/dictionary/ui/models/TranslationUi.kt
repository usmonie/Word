package com.usmonie.word.features.dictionary.ui.models

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