package wtf.word.core.design.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import wtf.word.core.design.themes.colors.BritishRacingGreenColors
import wtf.word.core.design.themes.colors.DeepIndigoColors
import wtf.word.core.design.themes.colors.EmeraldEleganceColors
import wtf.word.core.design.themes.colors.GentleBreezeColors
import wtf.word.core.design.themes.colors.PastelSunsetColors
import wtf.word.core.design.themes.colors.RichMaroonColors
import wtf.word.core.design.themes.colors.RoyalIndigoColors
import wtf.word.core.design.themes.colors.TwilightAmethystColors
import wtf.word.core.design.themes.typographies.Friendly
import wtf.word.core.design.themes.typographies.WordTypography

enum class WordColors(val colors: Colors, val title: String, val paid: Boolean) {
    RICH_MAROON(RichMaroonColors, "Rich Maroon", false),
    BRITISH_RACING_GREEN(BritishRacingGreenColors, "British Racing Green", false),
    DEEP_INDIGO(DeepIndigoColors, "Deep Indigo", false),

    GENTLE_BREEZE(GentleBreezeColors, "Gentle Breeze", true),
    ROYAL_INDIGO(RoyalIndigoColors, "Royal Indigo", true),
    TWILIGHT_AMETHYST(TwilightAmethystColors, "Twilight Amethyst", true),
    PASTEL_SUNSET(PastelSunsetColors, "Pastel Sunset", true),
    EMERALD_ELEGANCE(EmeraldEleganceColors, "Emerald Elegance", true);
}

@Composable
fun WordTheme(
    wordColors: WordColors = WordColors.BRITISH_RACING_GREEN,
    typography: WordTypography = Friendly,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val theme = if (useDarkTheme) {
        wordColors.colors.darkColorScheme()
    } else {
        wordColors.colors.lightColorScheme()
    }

    MaterialTheme(
        theme,
        typography = typography.typography(),
        content = content
    )
}
