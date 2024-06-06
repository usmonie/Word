package com.usmonie.word.features.qutoes.domain.models

data class Quote(
    val id: Long,
    val text: String,
    val author: String,
    val categories: List<String>,
    val favorite: Boolean
)
