package com.usmonie.word

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.usmonie.compass.core.RouteManager
import com.usmonie.compass.core.ui.Root
import com.usmonie.core.domain.usecases.invoke
import com.usmonie.core.kit.design.themes.WordTheme
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.ModernChic
import com.usmonie.word.features.ads.ui.AdsManager
import com.usmonie.word.features.ads.ui.LocalAdsManager
import com.usmonie.word.features.settings.domain.models.DarkThemeMode
import com.usmonie.word.features.settings.ui.models.UserSelectedTheme
import com.usmonie.word.features.settings.ui.usecases.UserSelectedThemeUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.compose.koinInject

@Composable
fun App(
	adsManager: AdsManager = koinInject(),
	routeManager: RouteManager = koinInject(),
	userSelectedThemeUseCase: UserSelectedThemeUseCase = koinInject(),
) {
	val isSystemInDark = isSystemInDarkTheme()
	val currentTheme by userSelectedThemeUseCase().collectAsState(
		UserSelectedTheme(
			WordThemes.DEEP_INDIGO,
			ModernChic,
			DarkThemeMode.AUTO
		),
		Dispatchers.Main
	)

	val useDarkThemeMode = currentTheme.darkThemeMode == DarkThemeMode.ALWAYS ||
		(currentTheme.darkThemeMode == DarkThemeMode.AUTO && isSystemInDark)

	WordTheme(
		currentTheme.themes,
		currentTheme.typography,
		useDarkThemeMode
	) {
		CompositionLocalProvider(LocalAdsManager provides adsManager) {
			Root(routeManager, isGestureNavigationEnabled = true)
		}
	}
}
