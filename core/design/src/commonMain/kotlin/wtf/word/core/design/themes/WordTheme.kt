package wtf.word.core.design.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import wtf.word.core.design.themes.colors.BritishRacingGreenColors
import wtf.word.core.design.themes.colors.DeepIndigoColors
import wtf.word.core.design.themes.colors.RichMaroonColors
import wtf.word.core.design.themes.typographies.font

enum class WordColors(val colors: Colors) {
    RICH_MAROON(RichMaroonColors),
    BRITISH_RACING_GREEN(BritishRacingGreenColors),
    DEEP_INDIGO(DeepIndigoColors);

    fun next() = when(this) {
        RICH_MAROON -> BRITISH_RACING_GREEN
        BRITISH_RACING_GREEN -> DEEP_INDIGO
        DEEP_INDIGO -> RICH_MAROON
    }
}

sealed class WordTypography(val name: String) {
    data class FontFamilyData(
        val name: String,
        val res: String,
        val weight: FontWeight,
        val style: FontStyle
    )

    abstract val displayLarge: FontFamilyData
    abstract val displayMedium: FontFamilyData
    abstract val displaySmall: FontFamilyData

    abstract val headlineLarge: FontFamilyData
    abstract val headlineMedium: FontFamilyData
    abstract val headlineSmall: FontFamilyData

    abstract val labelLarge: FontFamilyData
    abstract val labelSmall: FontFamilyData

    abstract val bodyLarge: FontFamilyData

    companion object {
        fun valueOf(value: String): WordTypography {
            return when (value) {
                ModernChic.name -> ModernChic
                Friendly.name -> Friendly
                else -> TimelessElegant
            }
        }

        fun WordTypography.next() = when(this) {
            Friendly -> ModernChic
            ModernChic -> TimelessElegant
            TimelessElegant -> Friendly
        }
    }
    data object TimelessElegant : WordTypography("TimelessElegant") {

        override val displayLarge = FontFamilyData(
            "Noto Serif Black",
            "notoserif_black",
            FontWeight.Black,
            FontStyle.Normal
        )

        override val displayMedium = FontFamilyData(
            "Noto Serif Bold",
            "notoserif_bold",
            FontWeight.Bold,
            FontStyle.Normal
        )

        override val displaySmall = FontFamilyData(
            "Noto Serif Regular",
            "notoserif_regular",
            FontWeight.SemiBold,
            FontStyle.Normal
        )

        override val headlineLarge = FontFamilyData(
            "Noto Serif Regular",
            "notoserif_regular",
            FontWeight.Normal,
            FontStyle.Normal
        )

        override val headlineMedium = FontFamilyData(
            "Noto Serif Bold",
            "notoserif_bold",
            FontWeight.Bold,
            FontStyle.Normal
        )

        override val headlineSmall = FontFamilyData(
            "Noto Serif Semibold",
            "notoserif_semibold",
            FontWeight.SemiBold,
            FontStyle.Normal
        )

        override val labelLarge = FontFamilyData(
            "Noto Serif Semibold",
            "notoserif_semibold",
            FontWeight.SemiBold,
            FontStyle.Normal
        )

        override val labelSmall = FontFamilyData(
            "Noto Serif Light",
            "notoserif_light",
            FontWeight.Light,
            FontStyle.Normal
        )

        override val bodyLarge = FontFamilyData(
            "Noto Sans Regular",
            "notosans_regular",
            FontWeight.Normal,
            FontStyle.Normal
        )

        @Composable
        override fun typography(): Typography = super.typography().copy(
            titleLarge = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Normal
            ),
            titleMedium = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            ),
            titleSmall = MaterialTheme.typography.displaySmall.copy(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Medium
            ),
        )
    }

    data object ModernChic : WordTypography("ModernChic") {


        override val displayLarge = FontFamilyData(
            "Raleway Black",
            "raleway_black",
            FontWeight.Black,
            FontStyle.Normal
        )

        override val displayMedium = FontFamilyData(
            "Raleway Bold",
            "raleway_bold",
            FontWeight.Bold,
            FontStyle.Normal
        )

        override val displaySmall = FontFamilyData(
            "Raleway Regular",
            "raleway_regular",
            FontWeight.SemiBold,
            FontStyle.Normal
        )

        override val headlineLarge = FontFamilyData(
            "Raleway Regular",
            "raleway_regular",
            FontWeight.Normal,
            FontStyle.Normal
        )

        override val headlineMedium = FontFamilyData(
            "Raleway Bold",
            "raleway_bold",
            FontWeight.Bold,
            FontStyle.Normal
        )

        override val headlineSmall = FontFamilyData(
            "Raleway Semibold",
            "raleway_semibold",
            FontWeight.SemiBold,
            FontStyle.Normal
        )

        override val labelLarge = FontFamilyData(
            "Raleway Semibold",
            "raleway_semibold",
            FontWeight.SemiBold,
            FontStyle.Normal
        )

        override val labelSmall = FontFamilyData(
            "Raleway Light",
            "raleway_light",
            FontWeight.Light,
            FontStyle.Normal
        )

        override val bodyLarge = FontFamilyData(
            "Open Sans Regular",
            "opensans_regular",
            FontWeight.Normal,
            FontStyle.Normal
        )

        @Composable
        override fun typography(): Typography {

            return super.typography().copy(
                titleSmall = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = FontFamily(
                        font(
                            "Lato Semibold",
                            "lato_regular",
                            FontWeight.SemiBold,
                            FontStyle.Normal
                        )
                    )
                ),
                titleMedium = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily(
                        font(
                            "Lato Bold",
                            "lato_bold",
                            FontWeight.Bold,
                            FontStyle.Normal
                        )
                    ),
                    fontWeight = FontWeight.SemiBold
                ),
                titleLarge = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily(
                        font(
                            "Lato Bold",
                            "lato_bold",
                            FontWeight.Bold,
                            FontStyle.Normal
                        )
                    )
                ),
            )
        }
    }

    data object Friendly : WordTypography("Friendly") {
        override val displayLarge = FontFamilyData(
            "Source Serif 4 Black",
            "sourceserif_black",
            FontWeight.Black,
            FontStyle.Normal
        )

        override val displayMedium = FontFamilyData(
            "Source Serif 4 Bold",
            "sourceserif_bold",
            FontWeight.Bold,
            FontStyle.Normal
        )

        override val displaySmall = FontFamilyData(
            "Source Serif 4 Regular",
            "sourceserif_regular",
            FontWeight.SemiBold,
            FontStyle.Normal
        )

        override val headlineLarge = FontFamilyData(
            "Source Serif Regular",
            "sourceserif_regular",
            FontWeight.Normal,
            FontStyle.Normal
        )

        override val headlineMedium = FontFamilyData(
            "Source Serif Bold",
            "sourceserif_bold",
            FontWeight.Bold,
            FontStyle.Normal
        )

        override val headlineSmall = FontFamilyData(
            "Source Serif Semibold",
            "sourceserif_semibold",
            FontWeight.SemiBold,
            FontStyle.Normal
        )

        override val labelLarge = FontFamilyData(
            "Source Serif Semibold",
            "sourceserif_semibold",
            FontWeight.SemiBold,
            FontStyle.Normal
        )

        override val labelSmall = FontFamilyData(
            "Source Serif Light",
            "sourceserif_light",
            FontWeight.Light,
            FontStyle.Normal
        )

        override val bodyLarge = FontFamilyData(
            "Source Sans Regular",
            "sourcesans_regular",
            FontWeight.Normal,
            FontStyle.Normal
        )

        @Composable
        override fun typography(): Typography = super.typography().copy(
            titleSmall = MaterialTheme.typography.titleSmall.copy(
                fontFamily = FontFamily(
                    font(
                        "Source Code Medium",
                        "sourcecodepro_medium",
                        FontWeight.Medium,
                        FontStyle.Normal
                    )
                )
            ),
            titleMedium = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily(
                    font(
                        "Source Code Mediu",
                        "sourcecodepro_medium",
                        FontWeight.Medium,
                        FontStyle.Normal
                    )
                ),
                fontWeight = FontWeight.SemiBold
            ),
            titleLarge = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily(
                    font(
                        "Source Serif 4 Bold",
                        "sourceserif_bold",
                        FontWeight.Bold,
                        FontStyle.Normal
                    )
                )
            ),
        )
    }

    @Composable
    open fun typography(): Typography {
        return MaterialTheme.typography.copy(
            displayLarge = MaterialTheme.typography.displayLarge.copy(
                fontSize = 54.sp,
                fontFamily = FontFamily(getFont(displayLarge))
            ),
            displayMedium = MaterialTheme.typography.displayMedium.copy(
                fontFamily = FontFamily(getFont(displayLarge))
            ),
            displaySmall = MaterialTheme.typography.displaySmall.copy(
                fontFamily = FontFamily(getFont(displaySmall))
            ),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = FontFamily(getFont(headlineLarge))
            ),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily(getFont(headlineMedium))
            ),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 22.sp,
                fontFamily = FontFamily(getFont(headlineSmall))
            ),
            titleMedium = MaterialTheme.typography.titleMedium.copy(fontSize = 28.sp),
            labelLarge = MaterialTheme.typography.labelLarge.copy(
                fontFamily = FontFamily(getFont(labelLarge))
            ),
            labelSmall = MaterialTheme.typography.labelSmall.copy(
                fontSize = 12.sp,
                fontFamily = FontFamily(getFont(labelSmall))
            ),
            bodyLarge = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily(getFont(bodyLarge))
            ),
        )
    }

    @Composable
    fun getFont(fontFamilyData: FontFamilyData) = font(
        fontFamilyData.name,
        fontFamilyData.res,
        fontFamilyData.weight,
        fontFamilyData.style
    )
}

@Composable
fun WordTheme(
    wordColors: WordColors = WordColors.RICH_MAROON,
    typography: WordTypography = WordTypography.TimelessElegant,
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
