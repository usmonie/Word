package com.usmonie.word.features.dashboard.ui.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
@Immutable
data class DescendantUi(
    val id: String,
    val depth: Int?,
    val tags: List<String>,
    val templates: List<TemplateUi>,
    val text: String?,
)