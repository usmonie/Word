package com.usmonie.word.features.models

import com.usmonie.word.features.dashboard.domain.models.InflectionTemplate

data class InflectionTemplateUi(
    val id: String,
//    val args: Map<String, String>,
    val name: String?,
    val inflectionTemplate: InflectionTemplate
)