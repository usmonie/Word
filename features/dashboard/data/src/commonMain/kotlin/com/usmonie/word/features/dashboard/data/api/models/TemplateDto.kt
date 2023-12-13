package com.usmonie.word.features.dashboard.data.api.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TemplateDto(
    @SerialName("args")
    val args: Map<String, String> = mapOf(),
    @SerialName("expansion")
    val expansion: String?,
    @SerialName("name")
    val name: String?
)