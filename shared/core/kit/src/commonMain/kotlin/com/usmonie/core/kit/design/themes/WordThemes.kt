package com.usmonie.core.kit.design.themes;

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
    RICH_MAROON(RichMaroonColors, "Rich Maroon", false, roundShapes),
    BRITISH_RACING_GREEN(BritishRacingGreenColors, "British Racing Green", false, roundShapes),
    DEEP_INDIGO(DeepIndigoColors, "Deep Indigo", false, roundShapes),

    GENTLE_BREEZE(GentleBreezeColors, "Gentle Breeze", true, squareShapes),
    ROYAL_INDIGO(RoyalIndigoColors, "Royal Indigo", true, squareShapes),
    TWILIGHT_AMETHYST(TwilightAmethystColors, "Twilight Amethyst", true, squareShapes),
    PASTEL_SUNSET(PastelSunsetColors, "Pastel Sunset", true, squareShapes),
    EMERALD_ELEGANCE(EmeraldEleganceColors, "Emerald Elegance", true, squareShapes);
}