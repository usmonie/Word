package com.usmonie.word.features.dictionary.domain.models

data class EtymologyTemplate(
    val args: Map<String, String?>,
    val expansion: String?,
    val name: String?
)
