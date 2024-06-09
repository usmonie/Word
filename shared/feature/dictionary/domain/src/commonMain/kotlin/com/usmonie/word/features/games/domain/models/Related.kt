package com.usmonie.word.features.games.domain.models

data class Related(
    val alt: String?,
    val english: String?,
    val qualifier: String?,
    val roman: String?,
    val ruby: List<List<String>>,
    val sense: String?,
    val source: String?,
    val tags: List<String>,
    val taxonomic: String?,
    val topics: List<String>,
    val urls: List<String>,
    val word: String?,
    val extra: String?
)
