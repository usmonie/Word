package com.usmonie.word.features.dashboard.domain.models

data class Descendant(
    val id: String,
    val depth: Int?,
    val tags: List<String>,
    val templates: List<Template>,
    val text: String?
)