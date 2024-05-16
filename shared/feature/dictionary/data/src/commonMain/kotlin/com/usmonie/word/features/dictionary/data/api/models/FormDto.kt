package com.usmonie.word.features.dictionary.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FormDto(
    @SerialName("form")
    val form: String,
    @SerialName("head_nr")
    val headNr: Int? = null,
    @SerialName("ipa")
    val ipa: String? = null,
    @SerialName("roman")
    val roman: String? = null,
    @SerialName("ruby")
    val ruby: List<List<String>> = listOf(),
    @SerialName("source")
    val source: String? = null,
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("topics")
    val topics: List<String> = listOf()
)
