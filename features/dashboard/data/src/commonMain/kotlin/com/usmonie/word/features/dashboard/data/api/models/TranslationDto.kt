package com.usmonie.word.features.dashboard.data.api.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TranslationDto(
    @SerialName("alt")
    val alt: String?,
    @SerialName("code")
    val code: String?,
    @SerialName("english")
    val english: String?,
    @SerialName("lang")
    val lang: String?,
    @SerialName("note")
    val note: String?,
    @SerialName("roman")
    val roman: String?,
    @SerialName("sense")
    val sense: String?,
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("taxonomic")
    val taxonomic: String?,
    @SerialName("topics")
    val topics: List<String> = listOf(),
    @SerialName("word")
    val word: String?
)