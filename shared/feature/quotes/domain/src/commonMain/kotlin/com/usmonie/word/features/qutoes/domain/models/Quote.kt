package com.usmonie.word.features.qutoes.domain.models

data class Quote(
    val text: String,
    val encryptedText: String,
    val author: String,
    val category: List<String>
)
