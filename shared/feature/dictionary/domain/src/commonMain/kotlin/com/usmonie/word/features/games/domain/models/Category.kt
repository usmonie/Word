package com.usmonie.word.features.games.domain.models

data class Category(
    val kind: String?,
    val langcode: String?,
    val name: String?,
    val orig: String?,
    val parents: List<String>,
    val source: String?
)
