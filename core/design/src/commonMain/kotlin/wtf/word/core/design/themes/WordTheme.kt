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

enum class WordColors(val colors: Colors, val title: String) {
    RICH_MAROON(RichMaroonColors, "Rich Maroon"),
    BRITISH_RACING_GREEN(BritishRacingGreenColors, "British Racing Green"),
    DEEP_INDIGO(DeepIndigoColors, "Deep Indigo"),

    GENTLE_BREEZE(GentleBreezeColors, "Gentle Breeze"),
    ROYAL_INDIGO(RoyalIndigoColors, "Royal Indigo"),
    TWILIGHT_AMETHYST(TwilightAmethystColors, "Twilight Amethyst"),
    PASTEL_SUNSET(PastelSunsetColors, "Pastel Sunset"),
    EMERALD_ELEGANCE(EmeraldEleganceColors, "Emerald Elegance");

    fun next(subscribed: Boolean = true) = if (subscribed) when (this) {
        RICH_MAROON -> BRITISH_RACING_GREEN
        BRITISH_RACING_GREEN -> DEEP_INDIGO
        DEEP_INDIGO -> GENTLE_BREEZE
        GENTLE_BREEZE -> EMERALD_ELEGANCE
        EMERALD_ELEGANCE -> TWILIGHT_AMETHYST
        TWILIGHT_AMETHYST -> PASTEL_SUNSET
        PASTEL_SUNSET -> ROYAL_INDIGO
        ROYAL_INDIGO -> RICH_MAROON
    } else BRITISH_RACING_GREEN
}

@Composable
fun WordTheme(
    wordColors: WordColors = WordColors.BRITISH_RACING_GREEN,
    typography: WordTypography = Friendly,
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        wordColors.colors.lightColorScheme(),
        typography = typography.typography(),
        content = content
    )
}
