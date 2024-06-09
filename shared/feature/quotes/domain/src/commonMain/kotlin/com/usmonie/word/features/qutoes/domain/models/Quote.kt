package com.usmonie.word.features.qutoes.domain.models

data class Quote(
    val id: String,
    val text: String,
    val author: String?,
    val categories: List<String>,
    val favorite: Boolean,
    val wasPlayed: Boolean
)
