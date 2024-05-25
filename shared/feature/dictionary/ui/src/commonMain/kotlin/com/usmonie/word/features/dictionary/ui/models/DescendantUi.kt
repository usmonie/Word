package com.usmonie.word.features.dictionary.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class DescendantUi(
    val depth: Int?,
    val tags: List<String>,
    val templates: List<TemplateUi>,
    val text: String?,
)
