package com.usmonie.word.features.new.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.models.EtymologyTemplate

@Stable
@Immutable
data class EtymologyTemplateUi(
    val id: String,
//    val args: Map<String, String>,
    val expansion: String?,
    val name: String?,
    val etymologyTemplate: EtymologyTemplate,

)