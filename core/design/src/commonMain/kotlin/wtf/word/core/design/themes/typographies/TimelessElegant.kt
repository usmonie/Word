package wtf.word.core.design.themes.typographies

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

data object TimelessElegant : WordTypography("Timeless Elegant") {

    private val notoSerifBlack = FontFamilyData.CustomFont(
        "Noto Serif Black",
        "notoserif_black",
        FontWeight.Black,
        FontStyle.Normal
    )

    private val notoSerifBold = FontFamilyData.CustomFont(
        "Noto Serif Bold",
        "notoserif_bold",
        FontWeight.Bold,
        FontStyle.Normal
    )

    private val notoSerifSemiBold = FontFamilyData.CustomFont(
        "Noto Serif Semibold",
        "notoserif_semibold",
        FontWeight.SemiBold,
        FontStyle.Normal
    )

    private val notoSerifRegular = FontFamilyData.CustomFont(
        "Noto Serif Regular",
        "notoserif_regular",
        FontWeight.SemiBold,
        FontStyle.Normal
    )

    private val notoSerifLight = FontFamilyData.CustomFont(
        "Noto Serif Light",
        "notoserif_light",
        FontWeight.Light,
        FontStyle.Normal
    )

    private val notoSansRegular = FontFamilyData.CustomFont(
        "Noto Sans Regular",
        "notosans_regular",
        FontWeight.Normal,
        FontStyle.Normal
    )

    private val notoSansLight = FontFamilyData.CustomFont(
        "Noto Sans Light",
        "notosans_light",
        FontWeight.Light,
        FontStyle.Normal
    )

    private val notoSansLightItalic = FontFamilyData.CustomFont(
        "Noto Sans Light",
        "notosans_light_italic",
        FontWeight.Light,
        FontStyle.Italic
    )

    private val robotoMonoBold = FontFamilyData.Roboto(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold
    )

    private val robotoMonoMedium = FontFamilyData.Roboto(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium
    )

    private val robotoMonoNormal = FontFamilyData.Roboto(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal
    )

    private val robotoMonoLight = FontFamilyData.Roboto(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light
    )

    private val robotoMedium = FontFamilyData.Roboto(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium
    )

    private val robotoNormal = FontFamilyData.Roboto(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium
    )

    override val displayLarge = notoSerifBlack
    override val displayMedium = notoSerifBold
    override val displaySmall = notoSerifRegular

    override val headlineLarge = notoSerifRegular
    override val headlineMedium = notoSerifBold
    override val headlineSmall = notoSerifSemiBold

    override val titleLarge: FontFamilyData = robotoMonoMedium
    override val titleMedium: FontFamilyData = robotoMonoNormal
    override val titleSmall: FontFamilyData = robotoMonoMedium

    override val labelLarge = notoSerifLight
    override val labelMedium = robotoMedium
    override val labelSmall = notoSerifLight

    override val bodyLarge = notoSansRegular
    override val bodyMedium = notoSansLightItalic
    override val bodySmall = notoSansLight
}
