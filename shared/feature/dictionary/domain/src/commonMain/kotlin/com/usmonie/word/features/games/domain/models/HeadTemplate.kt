package com.usmonie.word.features.games.domain.models

data class HeadTemplate(
    val args: Map<String, String?>,
    val expansion: String?,
    val name: String?
)
