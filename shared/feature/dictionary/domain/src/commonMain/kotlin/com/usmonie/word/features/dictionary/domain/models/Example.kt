package com.usmonie.word.features.dictionary.domain.models

data class Example(
    val english: String?,
    val note: String?,
    val ref: String?,
    val roman: String?,
    val ruby: List<List<String>>,
    val text: String?,
    val type: String?
)
