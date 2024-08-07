package com.usmonie.core.kit.design.themes.colors

import androidx.compose.ui.graphics.Color
import com.usmonie.core.kit.design.themes.Colors

@Suppress("MagicNumber")
object RoyalIndigoColors : Colors() {
    override val light_primary: Color = Color(0xFF3F51B5)
    override val light_onPrimary: Color = Color(0xFFFFFFFF)
    override val light_primaryContainer: Color = Color(0xFF0000BA)
    override val light_onPrimaryContainer: Color = light_onPrimary // Color(0xFF000063)
    override val light_secondary: Color = Color(0xFF5C6BC0)
    override val light_onSecondary: Color = Color(0xFFEDE7F6)
    override val light_secondaryContainer: Color = Color(0xFF9FA8DA)
    override val light_onSecondaryContainer: Color = Color(0xFF0000BA)
    override val light_tertiary: Color = Color(0xFF3F51B5)
    override val light_onTertiary: Color = Color(0xFFC5CAE9)
    override val light_tertiaryContainer: Color = Color(0xFFD1C4E9)
    override val light_onTertiaryContainer: Color = Color(0xFF1A237E)
    override val light_error: Color = Color(0xFFB00020)
    override val light_onError: Color = Color(0xFFFFFFFF)
    override val light_errorContainer: Color = Color(0xFFFCD8DC)
    override val light_onErrorContainer: Color = Color(0xFF5D001E)
    override val light_background: Color = Color(0xFFE8EAF6)
    override val light_onBackground: Color = Color(0xFF1A237E)
    override val light_surface: Color = Color(0xffd6d9e8)
    override val light_onSurface: Color = Color(0xFF1A237E)
    override val light_surfaceVariant = Color(0xFFcacde3)
    override val light_onSurfaceVariant = Color(0xFF1A237E)
    override val light_outline: Color = Color(0xFFB0BEC5)
    override val light_inverseSurface: Color = Color(0xFF303F9F)
    override val light_inverseOnSurface: Color = Color(0xFFEDE7F6)
    override val light_inversePrimary: Color = Color(0xFFC5CAE9)
    override val light_surfaceTint: Color = Color(0xFF3F51B5)
    override val light_outlineVariant: Color = Color(0xFFB0BEC5)
    override val light_scrim: Color = Color(0xFF000000)

    override val light_surfaceBright = Color(0xFFFDFDFD)
    override val light_surfaceDim = Color(0xFFEAEAE9)
    override val light_surfaceContainer = Color(0xFFe9e9f0)
    override val light_surfaceContainerHighest = Color(0xFFd1d1de)
    override val light_surfaceContainerHigh = Color(0xFFb9b9c9)
    override val light_surfaceContainerLow = Color(0xFFE6E6E8)
    override val light_surfaceContainerLowest = Color(0xFFDADADB)
    override val light_isAppearanceLightStatusBars: Boolean = true

    override val dark_primary: Color = Color(0xFFC5CAE9)
    override val dark_onPrimary: Color = Color(0xFF000063)
    override val dark_primaryContainer: Color = Color(0xFF303F9F)
    override val dark_onPrimaryContainer: Color = Color(0xFFC5CAE9)
    override val dark_secondary: Color = Color(0xFF7986CB)
    override val dark_onSecondary: Color = Color(0xFF303F9F)
    override val dark_secondaryContainer: Color = Color(0xFF5C6BC0)
    override val dark_onSecondaryContainer: Color = Color(0xFFEDE7F6)
    override val dark_tertiary: Color = Color(0xFF9FA8DA)
    override val dark_onTertiary: Color = Color(0xFF0000BA)
    override val dark_tertiaryContainer: Color = Color(0xFF3F51B5)
    override val dark_onTertiaryContainer: Color = Color(0xFFC5CAE9)
    override val dark_error: Color = Color(0xFFCF6679)
    override val dark_onError: Color = Color(0xFF000000)
    override val dark_errorContainer: Color = Color(0xFF5D001E)
    override val dark_onErrorContainer: Color = Color(0xFFCF6679)
    override val dark_background: Color = Color(0xFF303F9F)
    override val dark_onBackground: Color = Color(0xFFEDE7F6)
    override val dark_surface: Color = Color(0xff182686)
    override val dark_onSurface: Color = Color(0xFFEDE7F6)
    override val dark_surfaceVariant: Color = Color(0xFF3F51B5)
    override val dark_onSurfaceVariant: Color = Color(0xFFC5CAE9)
    override val dark_outline: Color = Color(0xFF9FA8DA)
    override val dark_inverseSurface: Color = Color(0xFFE8EAF6)
    override val dark_inverseOnSurface: Color = Color(0xFF303F9F)
    override val dark_inversePrimary: Color = Color(0xFF3F51B5)
    override val dark_surfaceTint: Color = Color(0xFFC5CAE9)
    override val dark_outlineVariant: Color = Color(0xFF9FA8DA)
    override val dark_scrim: Color = Color(0xFF000000)

    override val dark_surfaceBright = Color(0xFFFAFAFC)
    override val dark_surfaceDim = Color(0xFFFAFAFC)
    override val dark_surfaceContainer = Color(0xFF243291)
    override val dark_surfaceContainerHighest = Color(0xFF424fa8)
    override val dark_surfaceContainerHigh = Color(0xFF303e9c)
    override val dark_surfaceContainerLow = Color(0xFFE6E6E8)
    override val dark_surfaceContainerLowest = Color(0xFFDADADB)

    override val seed: Color = light_primary
}
