package com.usmonie.word.features.models

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.models.Example

@Stable
data class ExampleUi(
    val id: String,
    val english: String?,
    val note: String?,
    val ref: String?,
    val roman: String?,
    val ruby: List<List<String>>,
    val text: String?,
    val type: String?,
    val example: Example
)