package com.usmonie.word.features.onboarding.ui.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import com.usmonie.word.features.dashboard.domain.models.Language

@Immutable
data class LanguageType(
    val language: Language,
    val title: String,
    val flag: Painter
)
