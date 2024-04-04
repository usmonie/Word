package com.usmonie.word.features.dashboard.domain.models


data class HeadTemplate(
    val id: String,
    val args: Map<String, String?>,
    val expansion: String?,
    val name: String?
)