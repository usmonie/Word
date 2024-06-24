package com.usmonie.word.features.settings.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.usmonie.word.features.settings.domain.models.DarkThemeMode
import com.usmonie.word.features.settings.domain.models.Theme
import com.usmonie.word.features.settings.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppSettingsRepositoryImpl(
	private val datastore: DataStore<Preferences>,
) : AppSettingsRepository {
	override val currentTheme: Flow<Theme>
		get() = datastore.data.map {

			Theme(
				it[CURRENT_USER_THEME_COLORS_KEY],
				it[CURRENT_USER_THEME_FONTS_KEY],
				DarkThemeMode.valueOf(it[CURRENT_USER_THEME_DARK_MODE_KEY] ?: "AUTO")
			)
		}

	override suspend fun setCurrentTheme(theme: Theme) {
		datastore.edit {
			val colors = theme.colorsName
			if (colors != null) it[CURRENT_USER_THEME_COLORS_KEY] = colors

			val fonts = theme.fonts
			if (fonts != null) {
				it[CURRENT_USER_THEME_FONTS_KEY] = fonts
			}
			it[CURRENT_USER_THEME_DARK_MODE_KEY] = theme.darkThemeMode.name
			println(it[CURRENT_USER_THEME_FONTS_KEY])
		}
	}

	override suspend fun setOnboardingShowed(wasShowed: Boolean) {
		datastore.edit {
			it[WAS_ONBOARDING_SHOWED] = wasShowed
		}
	}

	override val wasOnboardingShowed: Flow<Boolean>
		get() = datastore.data.map { it[WAS_ONBOARDING_SHOWED] ?: false }

	companion object {
		private val CURRENT_USER_THEME_COLORS_KEY =
			stringPreferencesKey("CURRENT_USER_THEME_COLORS")
		private val CURRENT_USER_THEME_FONTS_KEY = stringPreferencesKey("CURRENT_USER_THEME_FONTS")
		private val CURRENT_USER_THEME_DARK_MODE_KEY = stringPreferencesKey("CURRENT_USER_THEME_DARK_MODE")

		private val WAS_ONBOARDING_SHOWED = booleanPreferencesKey("WAS_ONBOARDING_SHOWED")
	}
}