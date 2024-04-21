@file:OptIn(ExperimentalMaterial3Api::class)

package com.usmonie.word.features.dashboard.ui.settings

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Immutable
import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.usecase.ChangeThemeUseCase
import com.usmonie.word.features.dashboard.domain.usecase.ClearRecentUseCase
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCase
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase
import kotlinx.coroutines.launch
import wtf.speech.core.ui.BaseViewModel
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.ModernChic
import wtf.word.core.design.themes.typographies.WordTypography

@Immutable
internal class SettingsViewModel(
    private val subscriptionStatusUseCase: SubscriptionStatusUseCase,
    private val currentThemeUseCase: CurrentThemeUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase,
    private val clearRecentUseCase: ClearRecentUseCase
) : BaseViewModel<SettingsState, SettingsAction, SettingsEvent, SettingsEffect>(
    SettingsState(
        WordColors.RICH_MAROON,
        ModernChic,
        SubscriptionStatus.PURCHASED,
        LazyListState(0, 0),
    )
) {

    init {
        viewModelScope.launch {
            subscriptionStatusUseCase(Unit).collect { status ->
                handleAction(SettingsAction.OnSubscriptionChanged(status))
            }
        }
    }

    override fun SettingsState.reduce(event: SettingsEvent) = when (event) {
        is SettingsEvent.OnSubscriptionChanged -> this.copy(subscriptionStatus = event.newSubscriptionStatus)
        is SettingsEvent.OnThemeChanged -> this.copy(currentTheme = event.newTheme)
        is SettingsEvent.OnTypographyChanged -> this.copy(currentTypography = event.newTypography)
        SettingsEvent.ClearSearchHistory -> this
    }

    override suspend fun processAction(action: SettingsAction) = when (action) {
        is SettingsAction.OnThemeChanged -> {
            changeColors(action.newTheme)
            SettingsEvent.OnThemeChanged(action.newTheme)
        }

        is SettingsAction.OnTypographyChanged -> {
            changeTypography(action.newTypography)
            SettingsEvent.OnTypographyChanged(action.newTypography)
        }

        is SettingsAction.OnSubscriptionChanged -> {
            currentThemeUseCase(Unit).run {
                val userSelectedColor = colorsName?.let { WordColors.valueOf(it) }
                    ?: WordColors.RICH_MAROON
                val colors = when {
                    action.newSubscriptionStatus != SubscriptionStatus.PURCHASED && userSelectedColor.paid -> WordColors.RICH_MAROON
                    else -> userSelectedColor
                }
                val typography = fonts?.let { WordTypography.valueOf(it) } ?: ModernChic
                handleAction(SettingsAction.OnThemeChanged(colors))
                handleAction(SettingsAction.OnTypographyChanged(typography))
            }
            SettingsEvent.OnSubscriptionChanged(action.newSubscriptionStatus)
        }

        SettingsAction.ClearSearchHistory -> {
            clearRecentUseCase(Unit)
            SettingsEvent.ClearSearchHistory
        }
    }

    override suspend fun handleEvent(event: SettingsEvent) = when (event) {
        is SettingsEvent.OnThemeChanged -> SettingsEffect.OnThemeChanged(event.newTheme)
        is SettingsEvent.OnTypographyChanged -> SettingsEffect.OnTypographyChanged(event.newTypography)
        else -> null
    }

    fun clearSearchHistory() = handleAction(SettingsAction.ClearSearchHistory)

    private suspend fun changeColors(colors: WordColors) {
        val typography = currentThemeUseCase(Unit).fonts
        changeThemeUseCase(Theme(colors.name, typography))
    }

    private suspend fun changeTypography(typography: WordTypography) {
        val color = currentThemeUseCase(Unit).colorsName
        changeThemeUseCase(Theme(color, typography.name))
    }
}