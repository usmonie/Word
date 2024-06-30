package com.usmonie.word.features.dashboard.ui.screen

import org.jetbrains.compose.resources.StringResource
import word.shared.feature.dashboard.ui.generated.resources.Res
import word.shared.feature.dashboard.ui.generated.resources.favorites_subtitle
import word.shared.feature.dashboard.ui.generated.resources.games_subtitle
import word.shared.feature.dashboard.ui.generated.resources.settings_subtitle

enum class DashboardMenuItem(val titleRes: StringResource) {
	FAVORITES(Res.string.favorites_subtitle),
	SETTINGS(Res.string.settings_subtitle),
	GAMES(Res.string.games_subtitle)
}
