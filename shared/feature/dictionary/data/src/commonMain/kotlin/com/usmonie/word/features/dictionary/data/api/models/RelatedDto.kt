package com.usmonie.word.features.dictionary.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RelatedDto(
    @SerialName("alt")
    val alt: String? = null,
    @SerialName("english")
    val english: String? = null,
    @SerialName("qualifier")
    val qualifier: String? = null,
    @SerialName("roman")
    val roman: String? = null,
    @SerialName("ruby")
    val ruby: List<List<String>> = listOf(),
    @SerialName("sense")
    val sense: String? = null,
    @SerialName("source")
    val source: String? = null,
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("taxonomic")
    val taxonomic: String? = null,
    @SerialName("topics")
    val topics: List<String> = listOf(),
    @SerialName("urls")
    val urls: List<String> = listOf(),
    @SerialName("word")
    val word: String? = null,
    @SerialName("extra")
    val extra: String? = null
)
