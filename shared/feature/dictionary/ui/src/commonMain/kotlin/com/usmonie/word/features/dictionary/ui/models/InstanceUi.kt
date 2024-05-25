package com.usmonie.word.features.dictionary.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class InstanceUi(
    val sense: String?,
    val source: String?,
    val word: String?,
    val tags: List<String>,
    val topics: List<String>,
)
