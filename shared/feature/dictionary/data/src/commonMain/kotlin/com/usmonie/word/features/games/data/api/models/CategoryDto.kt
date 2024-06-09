package com.usmonie.word.features.games.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CategoryDto(
    @SerialName("kind")
    val kind: String? = null,
    @SerialName("langcode")
    val langcode: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("orig")
    val orig: String? = null,
    @SerialName("parents")
    val parents: List<String> = listOf(),
    @SerialName("source")
    val source: String? = null
)
