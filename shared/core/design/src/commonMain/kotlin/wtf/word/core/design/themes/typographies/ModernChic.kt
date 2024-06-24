package wtf.word.core.design.themes.typographies

import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import word.shared.core.design.generated.resources.Res
import word.shared.core.design.generated.resources.lato_bold
import word.shared.core.design.generated.resources.lato_medium
import word.shared.core.design.generated.resources.lato_regular
import word.shared.core.design.generated.resources.opensans_light
import word.shared.core.design.generated.resources.opensans_light_italic
import word.shared.core.design.generated.resources.opensans_regular
import word.shared.core.design.generated.resources.raleway_black
import word.shared.core.design.generated.resources.raleway_bold
import word.shared.core.design.generated.resources.raleway_light
import word.shared.core.design.generated.resources.raleway_regular
import word.shared.core.design.generated.resources.raleway_semibold

@OptIn(ExperimentalResourceApi::class)
data object ModernChic : WordTypography("Modern Chic") {

	private val ralewayBlack = FontFamilyData.CustomFont(
		Res.font.raleway_black,
		FontWeight.Black,
		FontStyle.Normal
	)

	private val ralewayBold = FontFamilyData.CustomFont(
		Res.font.raleway_bold,
		FontWeight.Bold,
		FontStyle.Normal
	)

	private val ralewayRegular = FontFamilyData.CustomFont(
		Res.font.raleway_regular,
		FontWeight.SemiBold,
		FontStyle.Normal
	)

	private val ralewaySemibold = FontFamilyData.CustomFont(
		Res.font.raleway_semibold,
		FontWeight.SemiBold,
		FontStyle.Normal
	)

	private val ralewayLight = FontFamilyData.CustomFont(
		Res.font.raleway_light,
		FontWeight.Light,
		FontStyle.Normal
	)

	private val latoBold = FontFamilyData.CustomFont(
		Res.font.lato_bold,
		FontWeight.Bold,
		FontStyle.Normal
	)

	private val latoMedium = FontFamilyData.CustomFont(
		Res.font.lato_medium,
		FontWeight.SemiBold,
		FontStyle.Normal
	)

	private val latoRegular = FontFamilyData.CustomFont(
		Res.font.lato_regular,
		FontWeight.Normal,
		FontStyle.Normal
	)

	private val openSansRegular = FontFamilyData.CustomFont(
		Res.font.opensans_regular,
		FontWeight.Normal,
		FontStyle.Normal
	)

	private val openSansLight = FontFamilyData.CustomFont(
		Res.font.opensans_light,
		FontWeight.Light,
		FontStyle.Normal
	)

	private val openSansLightItalic = FontFamilyData.CustomFont(
		Res.font.opensans_light_italic,
		FontWeight.Light,
		FontStyle.Italic
	)

	override val displayLarge = ralewayBlack
	override val displayMedium = ralewayBold
	override val displaySmall = ralewayRegular

	override val headlineLarge = ralewayRegular
	override val headlineMedium = ralewayBlack
	override val headlineSmall = ralewaySemibold

	override val titleLarge = latoMedium
	override val titleMedium = latoRegular
	override val titleSmall = latoMedium

	override val labelLarge = ralewayLight
	override val labelMedium = latoMedium
	override val labelSmall = ralewayLight

	override val bodyLarge = openSansRegular
	override val bodyMedium = openSansLightItalic
	override val bodySmall = openSansLight
}
