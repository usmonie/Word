package com.usmonie.word.features.dashboard.ui.models

import androidx.compose.runtime.Immutable

@Immutable
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
)

@Immutable
data class Forms(val forms: List<FormUi>)
