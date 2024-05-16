package com.usmonie.core.kit.design.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.usmonie.core.kit.design.themes.typographies.Friendly
import com.usmonie.core.kit.design.themes.typographies.WordTypography

@Composable
fun WordTheme(
    wordThemes: WordThemes = WordThemes.BRITISH_RACING_GREEN,
    typography: WordTypography = Friendly,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val theme = if (useDarkTheme) {
        wordThemes.colors.darkColorScheme()
    } else {
        wordThemes.colors.lightColorScheme()
    }

    MaterialTheme(
        theme,
        typography = typography.typography(),
        shapes = wordThemes.shapes,
        content = content
    )
}
