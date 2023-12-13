package com.usmonie.word.features.new.models

import com.usmonie.word.features.dashboard.domain.models.EtymologyTemplate

data class EtymologyTemplateUi(
    val id: String,
//    val args: Map<String, String>,
    val expansion: String?,
    val name: String?,
    val etymologyTemplate: EtymologyTemplate,

)