package com.usmonie.core.kit.design.themes

import androidx.compose.material3.Shapes
import com.usmonie.core.kit.design.themes.colors.BritishRacingGreenColors
import com.usmonie.core.kit.design.themes.colors.DeepIndigoColors
import com.usmonie.core.kit.design.themes.colors.EmeraldEleganceColors
import com.usmonie.core.kit.design.themes.colors.GentleBreezeColors
import com.usmonie.core.kit.design.themes.colors.PastelSunsetColors
import com.usmonie.core.kit.design.themes.colors.RichMaroonColors
import com.usmonie.core.kit.design.themes.colors.RoyalIndigoColors
import com.usmonie.core.kit.design.themes.colors.TwilightAmethystColors

enum class WordThemes(val colors: Colors, val title: String, val paid: Boolean, val shapes: Shapes) {
    DEEP_INDIGO(DeepIndigoColors, "Deep Indigo", false, roundShapes),

    RICH_MAROON(RichMaroonColors, "Rich Maroon", false, roundShapes),
    BRITISH_RACING_GREEN(BritishRacingGreenColors, "British Racing Green", false, roundShapes),

    GENTLE_BREEZE(GentleBreezeColors, "Gentle Breeze", false, squareShapes),
    ROYAL_INDIGO(RoyalIndigoColors, "Royal Indigo", false, squareShapes),
    TWILIGHT_AMETHYST(TwilightAmethystColors, "Twilight Amethyst", false, squareShapes),
    PASTEL_SUNSET(PastelSunsetColors, "Pastel Sunset", false, squareShapes),
    EMERALD_ELEGANCE(EmeraldEleganceColors, "Emerald Elegance", false, squareShapes);
}
