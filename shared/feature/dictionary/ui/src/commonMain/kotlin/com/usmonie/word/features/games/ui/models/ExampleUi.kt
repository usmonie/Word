package com.usmonie.word.features.games.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class ExampleUi(
    val english: String?,
    val note: String?,
    val ref: String?,
    val roman: String?,
    val ruby: List<List<String>>,
    val text: String?,
    val type: String?,
)