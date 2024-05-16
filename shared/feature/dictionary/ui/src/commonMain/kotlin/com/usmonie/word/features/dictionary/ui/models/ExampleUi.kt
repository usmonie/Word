package com.usmonie.word.features.dictionary.ui.models

import androidx.compose.runtime.Stable

@Stable
data class ExampleUi(
    val id: String,
    val english: String?,
    val note: String?,
    val ref: String?,
    val roman: String?,
    val ruby: List<List<String>>,
    val text: String?,
    val type: String?,
)