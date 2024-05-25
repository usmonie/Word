package com.usmonie.word.features.settings.ui

import com.usmonie.compass.viewmodel.ScreenAction
import com.usmonie.compass.viewmodel.ScreenEffect
import com.usmonie.compass.viewmodel.ScreenEvent
import com.usmonie.compass.viewmodel.ScreenState
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.WordTypography
import com.usmonie.word.features.settings.domain.models.DarkThemeMode
import com.usmonie.word.features.settings.ui.models.UserSelectedTheme
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

data class SettingsState(
    val currentTheme: WordThemes,
    val currentTypography: WordTypography,
    val darkThemeMode: DarkThemeMode,
    val subscriptionStatus: SubscriptionStatus
) : ScreenState

sealed class SettingsAction : ScreenAction {
    data class UpdateCurrentTheme(val theme: UserSelectedTheme) : SettingsAction()
    data class UpdateSubscriptionStatus(val subscriptionStatus: SubscriptionStatus) :
        SettingsAction()

    data class ChangeTheme(val theme: WordThemes) : SettingsAction()
    data class ChangeDarkTheme(val darkThemeMode: DarkThemeMode) : SettingsAction()
    data class ChangeTypography(val typography: WordTypography) : SettingsAction()
    data object ClearSearchHistory : SettingsAction()
}

sealed class SettingsEvent : ScreenEvent {
    data class UpdateTheme(
        val theme: WordThemes,
        val typography: WordTypography,
        val darkThemeMode: DarkThemeMode
    ) : SettingsEvent()

    data object ClearSearchHistory : SettingsEvent()

    data class UpdateSubscriptionStatus(val subscriptionStatus: SubscriptionStatus) : SettingsEvent()
}

sealed class SettingsEffect : ScreenEffect
