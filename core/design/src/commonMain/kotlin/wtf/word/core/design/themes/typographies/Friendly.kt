package wtf.word.core.design.themes.typographies

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

data object Friendly : WordTypography("Friendly") {
    private val sourceSerifBlack = FontFamilyData.CustomFont(
        "Source Serif 4 Black",
        "sourceserif_black",
        FontWeight.Black,
        FontStyle.Normal
    )

    private val sourceSerifBold = FontFamilyData.CustomFont(
        "Source Serif 4 Bold",
        "sourceserif_bold",
        FontWeight.Bold,
        FontStyle.Normal
    )

    private val sourceSerifRegular = FontFamilyData.CustomFont(
        "Source Serif 4 Regular",
        "sourceserif_regular",
        FontWeight.Normal,
        FontStyle.Normal
    )

    private val sourceSerifLight = FontFamilyData.CustomFont(
        "Source Serif 4 Light",
        "sourceserif_light",
        FontWeight.Light,
        FontStyle.Normal
    )

    private val sourceSansRegular = FontFamilyData.CustomFont(
        "Source Sans Regular",
        "sourcesans_regular",
        FontWeight.Normal,
        FontStyle.Normal
    )

    private val sourceSansLightItalic = FontFamilyData.CustomFont(
        "Source Sans Light Italic",
        "sourcesans_light_italic",
        FontWeight.Light,
        FontStyle.Italic
    )

    private val sourceSansLight = FontFamilyData.CustomFont(
        "Source Sans Light",
        "sourcesans_light",
        FontWeight.Light,
        FontStyle.Normal
    )

    private val sourceCodeSemibold = FontFamilyData.CustomFont(
        "Source Code Semibold",
        "sourcecodepro_semibold",
        FontWeight.SemiBold,
        FontStyle.Normal
    )

    private val sourceCodeMedium = FontFamilyData.CustomFont(
        "Source Code Medium",
        "sourcecodepro_medium",
        FontWeight.Medium,
        FontStyle.Normal
    )

    private val sourceCodeRegular = FontFamilyData.CustomFont(
        "Source Code Regular",
        "sourcecodepro_regular",
        FontWeight.Normal,
        FontStyle.Normal
    )

    private val sourceCodeLight = FontFamilyData.CustomFont(
        "Source Code Light",
        "sourcecodepro_light",
        FontWeight.Light,
        FontStyle.Normal
    )

    override val displayLarge = sourceSerifBlack
    override val displayMedium = sourceSerifBold
    override val displaySmall = sourceSerifRegular

    override val headlineLarge = sourceSerifRegular
    override val headlineMedium = sourceSerifBold
    override val headlineSmall = sourceSerifRegular

    override val titleLarge = sourceCodeMedium
    override val titleMedium = sourceCodeRegular
    override val titleSmall = sourceCodeMedium

    override val labelLarge = sourceSerifLight
    override val labelMedium = sourceCodeMedium
    override val labelSmall = sourceCodeLight

    override val bodyLarge = sourceSansRegular
    override val bodyMedium = sourceSansLightItalic
    override val bodySmall = sourceSansLight
}
