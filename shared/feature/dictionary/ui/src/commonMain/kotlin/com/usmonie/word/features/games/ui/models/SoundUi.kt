package com.usmonie.word.features.games.ui.models

import androidx.compose.runtime.Immutable

@Immutable
data class SoundUi(
    val audio: String?,
    val audioIpa: String?,
    val transcription: String?,
    val form: String?,
    val homophone: String?,
    val mp3Url: String?,
    val note: String?,
    val oggUrl: String?,
    val other: String?,
    val rhymes: String?,
    val tags: String,
    val text: String?,
    val topics: List<String>,
    val zhPron: String?,
)
