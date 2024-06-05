package com.usmonie.word.features.qutoes.domain.models

data class Quote(
    val text: String,
    val author: String,
    val categories: List<String>
)
