package com.usmonie.word.features.dictionary.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class CategoryUi(
    val kind: String?,
    val langcode: String?,
    val name: String?,
    val orig: String?,
    val parents: List<String>,
    val source: String?,
)
