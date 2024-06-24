@file:OptIn(ExperimentalResourceApi::class)

package wtf.word.core.design.themes.typographies

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import word.shared.core.design.generated.resources.Res
import word.shared.core.design.generated.resources.notosans_light
import word.shared.core.design.generated.resources.notosans_light_italic
import word.shared.core.design.generated.resources.notosans_regular
import word.shared.core.design.generated.resources.notoserif_black
import word.shared.core.design.generated.resources.notoserif_bold
import word.shared.core.design.generated.resources.notoserif_light
import word.shared.core.design.generated.resources.notoserif_regular
import word.shared.core.design.generated.resources.notoserif_semibold

data object TimelessElegant : WordTypography("Timeless Elegant") {

	private val notoSerifBlack = FontFamilyData.CustomFont(
		Res.font.notoserif_black,
		FontWeight.Black,
		FontStyle.Normal
	)

	private val notoSerifBold = FontFamilyData.CustomFont(
		Res.font.notoserif_bold,
		FontWeight.Bold,
		FontStyle.Normal
	)

	private val notoSerifSemiBold = FontFamilyData.CustomFont(
		Res.font.notoserif_semibold,
		FontWeight.SemiBold,
		FontStyle.Normal
	)

	private val notoSerifRegular = FontFamilyData.CustomFont(
		Res.font.notoserif_regular,
		FontWeight.SemiBold,
		FontStyle.Normal
	)

	private val notoSerifLight = FontFamilyData.CustomFont(
		Res.font.notoserif_light,
		FontWeight.Light,
		FontStyle.Normal
	)

	private val notoSansRegular = FontFamilyData.CustomFont(
		Res.font.notosans_regular,
		FontWeight.Normal,
		FontStyle.Normal
	)

	private val notoSansLight = FontFamilyData.CustomFont(
		Res.font.notosans_light,
		FontWeight.Light,
		FontStyle.Normal
	)

	private val notoSansLightItalic = FontFamilyData.CustomFont(
		Res.font.notosans_light_italic,
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
	override val headlineMedium = notoSerifBlack
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
