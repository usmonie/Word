package com.usmonie.word.features.dashboard.domain.models

data class EtymologyTemplate(
    val id: String,
    val args: Map<String, String?>,
    val expansion: String?,
    val name: String?
)