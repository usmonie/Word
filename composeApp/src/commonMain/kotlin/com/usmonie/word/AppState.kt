package com.usmonie.word

import androidx.compose.runtime.Immutable
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.WordTypography

@Immutable
data class AppState(
    val themes: WordThemes,
    val typography: WordTypography,
)
