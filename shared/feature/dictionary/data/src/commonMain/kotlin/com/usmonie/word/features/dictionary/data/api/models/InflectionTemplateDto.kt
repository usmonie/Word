package com.usmonie.word.features.dictionary.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InflectionTemplateDto(
    @SerialName("args")
    val args: Map<String, String> = mapOf(),
    @SerialName("name")
    val name: String? = null
)
