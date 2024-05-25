package com.usmonie.word.features.settings.ui.models

import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.WordTypography
import com.usmonie.word.features.settings.domain.models.DarkThemeMode

data class UserSelectedTheme(
    val themes: WordThemes,
    val typography: WordTypography,
    val darkThemeMode: DarkThemeMode
)
