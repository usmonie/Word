package com.usmonie.word.features.dashboard.data.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ExampleDto(
    @SerialName("english")
    val english: String? = null,
    @SerialName("note")
    val note: String? = null,
    @SerialName("ref")
    val ref: String? = null,
    @SerialName("roman")
    val roman: String? = null,
    @SerialName("ruby")
    val ruby: List<List<String>> = listOf(),
    @SerialName("text")
    val text: String? = null,
    @SerialName("type")
    val type: String? = null
)