package com.usmonie.word.features.new.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.models.Descendant

@Stable
@Immutable
data class DescendantUi(
    val id: String,
    val depth: Int?,
    val tags: List<String>,
    val templates: List<TemplateUi>,
    val text: String?,
    val descendant: Descendant
)