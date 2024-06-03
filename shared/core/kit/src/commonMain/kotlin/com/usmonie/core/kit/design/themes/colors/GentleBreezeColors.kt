package com.usmonie.core.kit.design.themes.colors

import androidx.compose.ui.graphics.Color
import com.usmonie.core.kit.design.themes.Colors

@Suppress("MagicNumber")
object GentleBreezeColors : Colors() {
    override val light_primary: Color = Color(0xFF006399)
    override val light_onPrimary: Color = Color(0xFFffffff)
    override val light_primaryContainer: Color
        get() = light_secondary
    override val light_onPrimaryContainer: Color = Color(0xFF00227B)
    override val light_secondary: Color = Color(0xFFF4A8A8)
    override val light_onSecondary: Color = Color(0xFF5D001E)
    override val light_secondaryContainer: Color = Color(0xFFFCD8DC)
    override val light_onSecondaryContainer: Color = Color(0xFF5D001E)
    override val light_tertiary: Color = Color(0xFFB8D8B8)
    override val light_onTertiary: Color = Color(0xFF1C3A13)
    override val light_tertiaryContainer: Color = Color(0xFFA2CF9A)
    override val light_onTertiaryContainer: Color = Color(0xFF1C3A13)
    override val light_error: Color = Color(0xFFB00020)
    override val light_onError: Color = Color(0xFFFFFFFF)
    override val light_errorContainer: Color = Color(0xFFFCD8DC)
    override val light_onErrorContainer: Color = Color(0xFF5D001E)
    override val light_background: Color = Color(0xFFFFFBFE)
    override val light_onBackground: Color = Color(0xFF1E1E1E)
    override val light_surface: Color = Color(0xfff6f1f1)
    override val light_onSurface: Color = Color(0xFF1E1E1E)
    override val light_surfaceVariant = Color(0xFFf0e6e6)
    override val light_onSurfaceVariant = Color(0xFF4D4639)
    override val light_inverseSurface: Color = Color(0xFF303030)
    override val light_inverseOnSurface: Color = Color(0xFFF5F5F5)
    override val light_inversePrimary: Color = Color(0xFFD1E3FF)
    override val light_surfaceTint: Color = light_primary
    override val light_outlineVariant: Color = Color(0xFFB0BEC5)
    override val light_outline: Color = Color(0xFF79747E)
    override val light_scrim: Color = Color(0xFF000000)

    override val light_surfaceBright = Color(0xFFFDFDFD)
    override val light_surfaceDim = Color(0xFFEAEAE9)
    override val light_surfaceContainer = Color(0xFFFAFAFC)
    override val light_surfaceContainerHighest = Color(0xFFFFFFFF)
    override val light_surfaceContainerHigh = Color(0xFFF5F5F7)
    override val light_surfaceContainerLow = Color(0xFFE6E6E8)
    override val light_surfaceContainerLowest = Color(0xFFDADADB)
    override val light_isAppearanceLightStatusBars: Boolean = true

    override val dark_primary: Color = Color(0xFF2336b0)
    override val dark_onPrimary: Color = Color(0xFFFFFFFF)
    override val dark_primaryContainer: Color = dark_primary
    override val dark_onPrimaryContainer: Color = dark_onPrimary
    override val dark_secondary: Color = Color(0xFFEF9A9A)
    override val dark_onSecondary: Color = Color(0xFF000000)
    override val dark_secondaryContainer: Color = dark_secondary
    override val dark_onSecondaryContainer: Color = dark_onSecondary
    override val dark_tertiary: Color = Color(0xFFA5D6A7)
    override val dark_onTertiary: Color = Color(0xFF000000)
    override val dark_tertiaryContainer: Color = dark_tertiary
    override val dark_onTertiaryContainer: Color = dark_onTertiary
    override val dark_error: Color = Color(0xFFCF6679)
    override val dark_onError: Color = Color(0xFF000000)
    override val dark_errorContainer: Color = dark_error
    override val dark_onErrorContainer: Color = dark_onError
    override val dark_background: Color = Color(0xFF121212)
    override val dark_onBackground: Color = Color(0xFFFFFFFF)
    override val dark_surface: Color = Color(0xff252222)
    override val dark_onSurface: Color = Color(0xFFFFFFFF)
    override val dark_surfaceVariant: Color = Color(0xFF736a6a)
    override val dark_onSurfaceVariant: Color = Color(0xFF87828f)
    override val dark_inverseSurface: Color =
        Color(0xFFF1F1F1) // Светло-серый цвет для контрастных поверхностей в темной теме
    override val dark_inverseOnSurface: Color =
        Color(0xFF2E2E2E) // Темно-серый цвет текста на инверсной поверхности
    override val dark_inversePrimary: Color =
        Color(0xFF6D9EFF) // Светлый вариант основного цвета для контраста на темных поверхностях
    override val dark_surfaceTint: Color =
        dark_primary // Используется основной цвет темной темы для тонирования
    override val dark_outlineVariant: Color =
        Color(0xFF919191) // Серый цвет для контуров и разделительных элементов

    override val dark_outline: Color = Color(0xFF919191)
    override val dark_scrim: Color = Color(0xFF000000)

    override val dark_surfaceBright = Color(0xFFFAFAFC)
    override val dark_surfaceDim = Color(0xFFFAFAFC)
    override val dark_surfaceContainer = Color(0xFF473d3d)
    override val dark_surfaceContainerHighest = Color(0xFF574848)
    override val dark_surfaceContainerHigh = Color(0xFF423838)
    override val dark_surfaceContainerLow = Color(0xFF332727)
    override val dark_surfaceContainerLowest = Color(0xFF2b2020)

    override val seed = Color(0xFF800000)
}
