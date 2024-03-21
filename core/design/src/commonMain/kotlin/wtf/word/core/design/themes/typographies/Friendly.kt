@file:OptIn(ExperimentalResourceApi::class)

package wtf.word.core.design.themes.typographies

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import word.core.design.generated.resources.Res
import word.core.design.generated.resources.sourcecodepro_light
import word.core.design.generated.resources.sourcecodepro_medium
import word.core.design.generated.resources.sourcecodepro_regular
import word.core.design.generated.resources.sourcecodepro_semibold
import word.core.design.generated.resources.sourcesans_light
import word.core.design.generated.resources.sourcesans_light_italic
import word.core.design.generated.resources.sourcesans_regular
import word.core.design.generated.resources.sourceserif_black
import word.core.design.generated.resources.sourceserif_bold
import word.core.design.generated.resources.sourceserif_light
import word.core.design.generated.resources.sourceserif_regular

data object Friendly : WordTypography("Friendly") {
    private val sourceSerifBlack = FontFamilyData.CustomFont(
        Res.font.sourceserif_black,
        FontWeight.Black,
        FontStyle.Normal
    )

    private val sourceSerifBold = FontFamilyData.CustomFont(
        Res.font.sourceserif_bold,
        FontWeight.Bold,
        FontStyle.Normal
    )

    private val sourceSerifRegular = FontFamilyData.CustomFont(
        Res.font.sourceserif_regular,
        FontWeight.Normal,
        FontStyle.Normal
    )

    private val sourceSerifLight = FontFamilyData.CustomFont(
        Res.font.sourceserif_light,
        FontWeight.Light,
        FontStyle.Normal
    )

    private val sourceSansRegular = FontFamilyData.CustomFont(
        Res.font.sourcesans_regular,
        FontWeight.Normal,
        FontStyle.Normal
    )

    private val sourceSansLightItalic = FontFamilyData.CustomFont(
        Res.font.sourcesans_light_italic,
        FontWeight.Light,
        FontStyle.Italic
    )

    private val sourceSansLight = FontFamilyData.CustomFont(
        Res.font.sourcesans_light,
        FontWeight.Light,
        FontStyle.Normal
    )

    private val sourceCodeSemibold = FontFamilyData.CustomFont(
        Res.font.sourcecodepro_semibold,
        FontWeight.SemiBold,
        FontStyle.Normal
    )

    private val sourceCodeMedium = FontFamilyData.CustomFont(
        Res.font.sourcecodepro_medium,
        FontWeight.Medium,
        FontStyle.Normal
    )

    private val sourceCodeRegular = FontFamilyData.CustomFont(
        Res.font.sourcecodepro_regular,
        FontWeight.Normal,
        FontStyle.Normal
    )

    @OptIn(ExperimentalResourceApi::class)
    private val sourceCodeLight = FontFamilyData.CustomFont(
        Res.font.sourcecodepro_light,
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
