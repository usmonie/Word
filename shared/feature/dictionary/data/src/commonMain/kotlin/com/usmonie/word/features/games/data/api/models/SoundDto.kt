package com.usmonie.word.features.games.data.api.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SoundDto(
    @SerialName("audio")
    val audio: String? = null,
    @SerialName("audio-ipa")
    val audioIpa: String? = null,
    @SerialName("enpr")
    val enpr: String? = null,
    @SerialName("form")
    val form: String? = null,
    @SerialName("homophone")
    val homophone: String? = null,
    @SerialName("ipa")
    val ipa: String? = null,
    @SerialName("mp3_url")
    val mp3Url: String? = null,
    @SerialName("note")
    val note: String? = null,
    @SerialName("ogg_url")
    val oggUrl: String? = null,
    @SerialName("other")
    val other: String? = null,
    @SerialName("rhymes")
    val rhymes: String? = null,
    @SerialName("tags")
    val tags: List<String> = listOf(),
    @SerialName("text")
    val text: String? = null,
    @SerialName("topics")
    val topics: List<String> = listOf(),
    @SerialName("zh-pron")
    val zhPron: String? = null
)
