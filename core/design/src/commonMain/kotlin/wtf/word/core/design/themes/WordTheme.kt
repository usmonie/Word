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

enum class WordColors(val colors: Colors) {
    RICH_MAROON(RichMaroonColors),
    BRITISH_RACING_GREEN(BritishRacingGreenColors),
    DEEP_INDIGO(DeepIndigoColors),

    GENTLE_BREEZE(GentleBreezeColors),
    ROYAL_INDIGO(RoyalIndigoColors),
    TWILIGHT_AMETHYST(TwilightAmethystColors),
    PASTEL_SUNSET(PastelSunsetColors),
    EMERALD_ELEGANCE(EmeraldEleganceColors);

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
//    val colorScheme = when {
//        useDarkTheme -> wordColors.colors.darkColorScheme()
//        else -> wordColors.colors.lightColorScheme()
//    }

    MaterialTheme(
        wordColors.colors.lightColorScheme(),
        typography = typography.typography(),
        content = content
    )
}
