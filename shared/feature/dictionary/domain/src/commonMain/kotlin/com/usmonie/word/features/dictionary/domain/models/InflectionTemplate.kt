package com.usmonie.word.features.dictionary.domain.models

data class InflectionTemplate(
    val args: Map<String, String?>,
    val name: String?
)
