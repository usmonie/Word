package com.usmonie.word.features.new.models

import com.usmonie.word.features.dashboard.domain.models.Descendant

data class DescendantUi(
    val id: String,
    val depth: Int?,
    val tags: List<String>,
    val templates: List<TemplateUi>,
    val text: String?,
    val descendant: Descendant
)