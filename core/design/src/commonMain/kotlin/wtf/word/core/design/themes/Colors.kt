package wtf.word.core.design.themes

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

abstract class Colors {

    protected abstract val light_primary: Color
    protected abstract val light_onPrimary: Color
    protected abstract val light_primaryContainer: Color
    protected abstract val light_onPrimaryContainer: Color
    protected abstract val light_secondary: Color
    protected abstract val light_onSecondary: Color
    protected abstract val light_secondaryContainer: Color
    protected abstract val light_onSecondaryContainer: Color
    protected abstract val light_tertiary: Color
    protected abstract val light_onTertiary: Color
    protected abstract val light_tertiaryContainer: Color
    protected abstract val light_onTertiaryContainer: Color
    protected abstract val light_error: Color
    protected abstract val light_onError: Color
    protected abstract val light_errorContainer: Color
    protected abstract val light_onErrorContainer: Color
    protected abstract val light_outline: Color
    protected abstract val light_background: Color
    protected abstract val light_onBackground: Color
    protected abstract val light_surface: Color
    protected abstract val light_onSurface: Color
    protected abstract val light_surfaceVariant: Color
    protected abstract val light_onSurfaceVariant: Color
    protected abstract val light_inverseSurface: Color
    protected abstract val light_inverseOnSurface: Color
    protected abstract val light_inversePrimary: Color
    protected abstract val light_surfaceTint: Color
    protected abstract val light_outlineVariant: Color
    protected abstract val light_scrim: Color

    protected abstract val dark_primary: Color
    protected abstract val dark_onPrimary: Color
    protected abstract val dark_primaryContainer: Color
    protected abstract val dark_onPrimaryContainer: Color
    protected abstract val dark_secondary: Color
    protected abstract val dark_onSecondary: Color
    protected abstract val dark_secondaryContainer: Color
    protected abstract val dark_onSecondaryContainer: Color
    protected abstract val dark_tertiary: Color
    protected abstract val dark_onTertiary: Color
    protected abstract val dark_tertiaryContainer: Color
    protected abstract val dark_onTertiaryContainer: Color
    protected abstract val dark_error: Color
    protected abstract val dark_onError: Color
    protected abstract val dark_errorContainer: Color
    protected abstract val dark_onErrorContainer: Color
    protected abstract val dark_outline: Color
    protected abstract val dark_background: Color
    protected abstract val dark_onBackground: Color
    protected abstract val dark_surface: Color
    protected abstract val dark_onSurface: Color
    protected abstract val dark_surfaceVariant: Color
    protected abstract val dark_onSurfaceVariant: Color
    protected abstract val dark_inverseSurface: Color
    protected abstract val dark_inverseOnSurface: Color
    protected abstract val dark_inversePrimary: Color
    protected abstract val dark_surfaceTint: Color
    protected abstract val dark_outlineVariant: Color
    protected abstract val dark_scrim: Color


    protected abstract val seed: Color

    @Composable
    fun lightColorScheme() = remember(this) {
        lightColorScheme(
            primary = light_primary,
            onPrimary = light_onPrimary,
            primaryContainer = light_primaryContainer,
            onPrimaryContainer = light_onPrimaryContainer,
            secondary = light_secondary,
            onSecondary = light_onSecondary,
            secondaryContainer = light_secondaryContainer,
            onSecondaryContainer = light_onSecondaryContainer,
            tertiary = light_tertiary,
            onTertiary = light_onTertiary,
            tertiaryContainer = light_tertiaryContainer,
            onTertiaryContainer = light_onTertiaryContainer,
            error = light_error,
            onError = light_onError,
            errorContainer = light_errorContainer,
            onErrorContainer = light_onErrorContainer,
            outline = light_outline,
            background = light_primary,
            onBackground = light_onPrimary,
            surface = light_surface,
            onSurface = light_onSurface,
            surfaceVariant = light_surfaceVariant,
            onSurfaceVariant = light_onSurfaceVariant,
            inverseSurface = light_inverseSurface,
            inverseOnSurface = light_inverseOnSurface,
            inversePrimary = light_inversePrimary,
            surfaceTint = light_surfaceTint,
            outlineVariant = light_outlineVariant,
            scrim = light_scrim,
        )
    }

    @Composable
    fun darkColorScheme() = remember(this) {
        darkColorScheme(
            primary = dark_primary,
            onPrimary = dark_onPrimary,
            primaryContainer = dark_primaryContainer,
            onPrimaryContainer = dark_onPrimaryContainer,
            secondary = dark_secondary,
            onSecondary = dark_onSecondary,
            secondaryContainer = dark_secondaryContainer,
            onSecondaryContainer = dark_onSecondaryContainer,
            tertiary = dark_tertiary,
            onTertiary = dark_onTertiary,
            tertiaryContainer = dark_tertiaryContainer,
            onTertiaryContainer = dark_onTertiaryContainer,
            error = dark_error,
            onError = dark_onError,
            errorContainer = dark_errorContainer,
            onErrorContainer = dark_onErrorContainer,
            outline = dark_outline,
            background = dark_primary,
            onBackground = dark_onPrimary,
            surface = dark_surface,
            onSurface = dark_onSurface,
            surfaceVariant = dark_surfaceVariant,
            onSurfaceVariant = dark_onSurfaceVariant,
            inverseSurface = dark_inverseSurface,
            inverseOnSurface = dark_inverseOnSurface,
            inversePrimary = dark_inversePrimary,
            surfaceTint = dark_surfaceTint,
            outlineVariant = dark_outlineVariant,
            scrim = dark_scrim,
        )
    }
}