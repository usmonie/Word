package com.usmonie.word.features.new.models

import com.usmonie.word.features.dashboard.domain.models.Category

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