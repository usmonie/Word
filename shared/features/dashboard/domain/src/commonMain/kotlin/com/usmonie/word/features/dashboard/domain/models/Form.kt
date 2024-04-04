package com.usmonie.word.features.dashboard.domain.models

data class Form(
    val id: String,
    val form: String?,
    val headNr: Int?,
    val ipa: String?,
    val roman: String?,
    val ruby: List<List<String>>,
    val source: String?,
    val tags: List<String>,
    val topics: List<String>
)