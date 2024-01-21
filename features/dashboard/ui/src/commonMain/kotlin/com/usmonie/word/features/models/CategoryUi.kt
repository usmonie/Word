package com.usmonie.word.features.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.models.Category

@Stable
@Immutable
data class CategoryUi(
    val id: String,
    val kind: String?,
    val langcode: String?,
    val name: String?,
    val orig: String?,
    val parents: List<String>,
    val source: String?,
    val category: Category
)