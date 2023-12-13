package com.usmonie.word.features.new.models

import com.usmonie.word.features.dashboard.domain.models.Form

data class FormUi(
    val id: String,
    val formText: String?,
    val headNr: Int?,
    val ipa: String?,
    val roman: String?,
    val source: String?,
    val ruby: List<List<String>>,
    val tags: List<String>,
    val topics: List<String>,
    val form: Form,
)