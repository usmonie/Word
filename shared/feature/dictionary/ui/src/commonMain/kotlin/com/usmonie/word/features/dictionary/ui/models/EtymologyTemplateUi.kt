package com.usmonie.word.features.dictionary.ui.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
@Immutable
data class EtymologyTemplateUi(
    val id: String,
//    val args: Map<String, String>,
    val expansion: String?,
    val name: String?,
)
