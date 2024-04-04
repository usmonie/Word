package com.usmonie.word.features.dashboard.data.api.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InstanceDto(
    @SerialName("sense")
    val sense: String? = null,
    @SerialName("source")
    val source: String? = null,
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("topics")
    val topics: List<String> = listOf(),
    @SerialName("word")
    val word: String? = null
)