package com.usmonie.word.features.dashboard.ui.models

import androidx.compose.runtime.Stable
import com.usmonie.word.features.dashboard.domain.models.Sound

@Stable
data class SoundUi(
    val id: String,
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
    val zhPron: String?,
)