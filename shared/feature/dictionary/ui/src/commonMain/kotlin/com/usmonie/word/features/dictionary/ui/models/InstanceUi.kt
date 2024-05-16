package com.usmonie.word.features.dictionary.ui.models

data class InstanceUi(
    val id: String,
    val sense: String?,
    val source: String?,
    val word: String?,
    val tags: List<String>,
    val topics: List<String>,
)
