package com.usmonie.word.features.dashboard.ui.models

import com.usmonie.word.features.dashboard.domain.models.Instance


data class InstanceUi(
    val id: String,
    val sense: String?,
    val source: String?,
    val word: String?,
    val tags: List<String>,
    val topics: List<String>,
)