package com.usmonie.word.features.dashboard.domain.models

data class InflectionTemplate(
    val id: String,
    val args: Map<String, String?>,
    val name: String?
)