package wtf.word.core.design.themes.typographies

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

data object ModernChic : WordTypography("Modern Chic") {

    private val ralewayBlack = FontFamilyData.CustomFont(
        "Raleway Black",
        "raleway_black",
        FontWeight.Black,
        FontStyle.Normal
    )

    private val ralewayBold = FontFamilyData.CustomFont(
        "Raleway Bold",
        "raleway_bold",
        FontWeight.Bold,
        FontStyle.Normal
    )

    private val ralewayRegular = FontFamilyData.CustomFont(
        "Raleway Regular",
        "raleway_regular",
        FontWeight.SemiBold,
        FontStyle.Normal
    )

    private val ralewaySemibold = FontFamilyData.CustomFont(
        "Raleway Semibold",
        "raleway_semibold",
        FontWeight.SemiBold,
        FontStyle.Normal
    )

    private val ralewayLight = FontFamilyData.CustomFont(
        "Raleway Light",
        "raleway_light",
        FontWeight.Light,
        FontStyle.Normal
    )

    private val latoBold = FontFamilyData.CustomFont(
        "Lato Bold",
        "lato_bold",
        FontWeight.Bold,
        FontStyle.Normal
    )

    private val latoMedium = FontFamilyData.CustomFont(
        "Lato Medium",
        "lato_medium",
        FontWeight.SemiBold,
        FontStyle.Normal
    )

    private val latoRegular = FontFamilyData.CustomFont(
        "Lato Regular",
        "lato_regular",
        FontWeight.Normal,
        FontStyle.Normal
    )

    private val openSansRegular = FontFamilyData.CustomFont(
        "Open Sans Regular",
        "opensans_regular",
        FontWeight.Normal,
        FontStyle.Normal
    )

    private val openSansLight = FontFamilyData.CustomFont(
        "Open Sans Light",
        "opensans_light",
        FontWeight.Light,
        FontStyle.Normal
    )

    private val openSansLightItalic = FontFamilyData.CustomFont(
        "Open Sans Light Italic",
        "opensans_light_italic",
        FontWeight.Light,
        FontStyle.Italic
    )

    override val displayLarge = ralewayBlack
    override val displayMedium = ralewayBold
    override val displaySmall = ralewayRegular

    override val headlineLarge = ralewayRegular
    override val headlineMedium = ralewayBold
    override val headlineSmall = ralewaySemibold

    override val titleLarge = latoRegular
    override val titleMedium = latoBold
    override val titleSmall = latoMedium

    override val labelLarge = ralewayLight
    override val labelMedium = latoMedium
    override val labelSmall = ralewayLight

    override val bodyLarge = openSansRegular
    override val bodyMedium = openSansLightItalic
    override val bodySmall = openSansLight
}
