package com.usmonie.word.features.games.domain.models

data class Descendant(
    val depth: Int?,
    val tags: List<String>,
    val templates: List<Template>,
    val text: String?
)
