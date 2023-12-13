package com.usmonie.word.features.new.models

import com.usmonie.word.features.dashboard.domain.models.Template

data class TemplateUi(
    val id: String,
//    val args: Map<String, String>,
    val expansion: String?,
    val name: String?,
    val template: Template
)