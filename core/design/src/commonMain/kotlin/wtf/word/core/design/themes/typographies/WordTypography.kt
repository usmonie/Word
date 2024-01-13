package wtf.word.core.design.themes.typographies

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

sealed class WordTypography(val name: String) {
    sealed class FontFamilyData {
        data class CustomFont(
            val name: String,
            val res: String,
            val weight: FontWeight,
            val style: FontStyle
        ) : FontFamilyData()

        data class Roboto(val fontFamily: FontFamily, val fontWeight: FontWeight) : FontFamilyData()
    }

    abstract val displayLarge: FontFamilyData
    abstract val displayMedium: FontFamilyData
    abstract val displaySmall: FontFamilyData

    abstract val headlineLarge: FontFamilyData
    abstract val headlineMedium: FontFamilyData
    abstract val headlineSmall: FontFamilyData

    abstract val titleLarge: FontFamilyData
    abstract val titleMedium: FontFamilyData
    abstract val titleSmall: FontFamilyData

    abstract val labelLarge: FontFamilyData
    abstract val labelMedium: FontFamilyData
    abstract val labelSmall: FontFamilyData

    abstract val bodyLarge: FontFamilyData
    abstract val bodyMedium: FontFamilyData
    abstract val bodySmall: FontFamilyData

    companion object {
        fun valueOf(value: String): WordTypography {
            return when (value) {
                ModernChic.name -> ModernChic
                Friendly.name -> Friendly
                else -> TimelessElegant
            }
        }
    }

    @Composable
    open fun typography(): Typography {
        return MaterialTheme.typography.copy(
            displayLarge = MaterialTheme.typography.displayLarge.copy(
                fontFamily = getFont(displayLarge)
            ),
            displayMedium = MaterialTheme.typography.displayMedium.copy(
                fontFamily = getFont(displayLarge)
            ),
            displaySmall = MaterialTheme.typography.displaySmall.copy(
                fontFamily = getFont(displaySmall)
            ),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = getFont(headlineLarge)
            ),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = getFont(headlineMedium)
            ),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = getFont(headlineSmall)
            ),
            titleLarge = MaterialTheme.typography.titleLarge.copy(
                fontFamily = getFont(titleLarge)
            ),
            titleMedium = MaterialTheme.typography.titleMedium.copy(
                fontFamily = getFont(titleMedium)
            ),
            titleSmall = MaterialTheme.typography.titleSmall.copy(
                fontFamily = getFont(titleSmall)
            ),
            labelLarge = MaterialTheme.typography.labelLarge.copy(
                fontFamily = getFont(labelLarge)
            ),
            labelMedium = MaterialTheme.typography.labelMedium.copy(
                fontFamily = getFont(labelMedium)
            ),
            labelSmall = MaterialTheme.typography.labelSmall.copy(
                fontFamily = getFont(labelSmall)
            ),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = getFont(bodyLarge),
                fontSize = 16.sp
            ),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = getFont(bodyMedium)
            ),
            bodySmall = MaterialTheme.typography.bodySmall.copy(
                fontFamily = getFont(bodySmall)
            ),
        )
    }

    @Composable
    fun getFont(fontFamilyData: FontFamilyData): FontFamily {
        return when (fontFamilyData) {
            is FontFamilyData.CustomFont -> FontFamily(
                font(
                    fontFamilyData.name,
                    fontFamilyData.res,
                    fontFamilyData.weight,
                    fontFamilyData.style
                )
            )

            is FontFamilyData.Roboto -> fontFamilyData.fontFamily
        }
    }
}