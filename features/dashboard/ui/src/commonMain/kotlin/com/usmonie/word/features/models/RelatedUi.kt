package com.usmonie.word.features.models

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.models.Related

@Stable
@Immutable
data class RelatedUi(
    val id: String,
    val alt: String?,
    val english: String?,
    val qualifier: String?,
    val roman: String?,
    val ruby: List<List<String>>,
    val sense: String?,
    val source: String?,
    val tags: List<String>,
    val taxonomic: String?,
    val topics: List<String>,
    val urls: List<String>,
    val word: String?,
    val extra: String?,
    val related: Related
)