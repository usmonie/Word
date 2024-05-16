package com.usmonie.word.features.dictionary.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class DescendantDto(
    @SerialName("depth")
    val depth: Int? = null,
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("templates")
    val templates: List<TemplateDto> = listOf(),
    @SerialName("text")
    val text: String? = null
)
