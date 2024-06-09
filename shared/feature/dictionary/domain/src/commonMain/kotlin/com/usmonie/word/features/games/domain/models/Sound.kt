package com.usmonie.word.features.games.domain.models

data class Sound(
    val audio: String?,
    val audioIpa: String?,
    val enpr: String?,
    val form: String?,
    val homophone: String?,
    val ipa: String?,
    val mp3Url: String?,
    val note: String?,
    val oggUrl: String?,
    val other: String?,
    val rhymes: String?,
    val tags: List<String>,
    val text: String?,
    val topics: List<String>,
    val zhPron: String?
)
