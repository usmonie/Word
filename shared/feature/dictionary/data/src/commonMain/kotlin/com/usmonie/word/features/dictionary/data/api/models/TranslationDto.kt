package com.usmonie.word.features.dictionary.data.api.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TranslationDto(
    @SerialName("alt")
    val alt: String? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("english")
    val english: String? = null,
    @SerialName("lang")
    val lang: String? = null,
    @SerialName("note")
    val note: String? = null,
    @SerialName("roman")
    val roman: String? = null,
    @SerialName("sense")
    val sense: String? = null,
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("taxonomic")
    val taxonomic: String? = null,
    @SerialName("topics")
    val topics: List<String> = listOf(),
    @SerialName("word")
    val word: String? = null
)
