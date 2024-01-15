package com.usmonie.word.features.new.settings

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Immutable
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.WordTypography

@OptIn(ExperimentalMaterial3Api::class)
@Immutable
data class SettingsState(
    val currentTheme: WordColors,
    val currentTypography: WordTypography,
    val subscriptionStatus: SubscriptionStatus,
    val listState: LazyListState,
    val appBarState: TopAppBarState
) : ScreenState

sealed class SettingsEvent: ScreenEvent {
    data class OnThemeChanged(val newTheme: WordColors) : SettingsEvent()
    data class OnTypographyChanged(val newTypography: WordTypography) : SettingsEvent()
    data class OnSubscriptionChanged(val newSubscriptionStatus: SubscriptionStatus) : SettingsEvent()

    data object ClearSearchHistory : SettingsEvent()
}

sealed class SettingsAction: ScreenAction {
    data class OnThemeChanged(val newTheme: WordColors) : SettingsAction()
    data class OnTypographyChanged(val newTypography: WordTypography) : SettingsAction()

    data class OnSubscriptionChanged(val newSubscriptionStatus: SubscriptionStatus) : SettingsAction()
    data object ClearSearchHistory : SettingsAction()
}

sealed class SettingsEffect: ScreenEffect {
    data class OnThemeChanged(val newTheme: WordColors) : SettingsEffect()
    data class OnTypographyChanged(val newTypography: WordTypography) : SettingsEffect()
}