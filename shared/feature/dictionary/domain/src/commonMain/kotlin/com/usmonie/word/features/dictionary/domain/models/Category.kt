package com.usmonie.word.features.dictionary.domain.models

data class Category(
    val id: String,
    val kind: String?,
    val langcode: String?,
    val name: String?,
    val orig: String?,
    val parents: List<String>,
    val source: String?
)
