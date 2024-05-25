package com.usmonie.word.features.settings.ui

import com.usmonie.compass.viewmodel.StateViewModel
import com.usmonie.core.kit.design.themes.WordThemes
import com.usmonie.core.kit.design.themes.typographies.ModernChic
import com.usmonie.core.kit.design.themes.typographies.WordTypography
import com.usmonie.word.features.settings.domain.models.DarkThemeMode
import com.usmonie.word.features.settings.domain.models.Theme
import com.usmonie.word.features.settings.domain.usecase.ChangeThemeUseCase
import com.usmonie.word.features.settings.ui.usecases.UserSelectedThemeUseCase
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCase

internal class SettingsViewModel(
    private val userSelectedThemeUseCase: UserSelectedThemeUseCase,
    private val changeThemeUseCase: ChangeThemeUseCase,
    private val subscriptionStatusUseCase: SubscriptionStatusUseCase
) : StateViewModel<SettingsState, SettingsAction, SettingsEvent, SettingsEffect>(
    SettingsState(
        WordThemes.DEEP_INDIGO,
        ModernChic,
        DarkThemeMode.AUTO,
        SubscriptionStatus.None
    )
) {

    init {
        viewModelScope.launchSafe {
            userSelectedThemeUseCase(Unit).collect {
                handleAction(SettingsAction.UpdateCurrentTheme(it))
            }
        }
        viewModelScope.launchSafe {
            subscriptionStatusUseCase(Unit).collect {
                handleAction(SettingsAction.UpdateSubscriptionStatus(it))
            }
        }
    }

    override fun SettingsState.reduce(event: SettingsEvent) = when (event) {
        SettingsEvent.ClearSearchHistory -> this
        is SettingsEvent.UpdateTheme -> copy(
            currentTheme = event.theme,
            currentTypography = event.typography,
            darkThemeMode = event.darkThemeMode
        )

        is SettingsEvent.UpdateSubscriptionStatus -> copy(subscriptionStatus = event.subscriptionStatus)
    }

    override suspend fun processAction(action: SettingsAction) = when (action) {
        is SettingsAction.ChangeDarkTheme -> {
            val currentTheme = state.value.currentTheme
            val currentTypography = state.value.currentTypography
            changeThemeUseCase(
                Theme(
                    currentTheme.name,
                    currentTypography.name,
                    action.darkThemeMode
                )
            )

            SettingsEvent.UpdateTheme(currentTheme, currentTypography, action.darkThemeMode)
        }

        is SettingsAction.ChangeTheme -> {
            val currentDarkThemeMode = state.value.darkThemeMode
            val currentTypography = state.value.currentTypography
            changeThemeUseCase(
                Theme(
                    action.theme.name,
                    currentTypography.name,
                    currentDarkThemeMode
                )
            )

            SettingsEvent.UpdateTheme(action.theme, currentTypography, currentDarkThemeMode)
        }

        is SettingsAction.ChangeTypography -> {
            val currentDarkThemeMode = state.value.darkThemeMode
            val currentTheme = state.value.currentTheme
            changeThemeUseCase(
                Theme(
                    currentTheme.name,
                    action.typography.name,
                    currentDarkThemeMode
                )
            )

            SettingsEvent.UpdateTheme(currentTheme, action.typography, currentDarkThemeMode)
        }

        SettingsAction.ClearSearchHistory -> SettingsEvent.ClearSearchHistory

        is SettingsAction.UpdateCurrentTheme -> SettingsEvent.UpdateTheme(
            action.theme.themes,
            action.theme.typography,
            action.theme.darkThemeMode
        )

        is SettingsAction.UpdateSubscriptionStatus -> SettingsEvent.UpdateSubscriptionStatus(action.subscriptionStatus)
    }

    override suspend fun handleEvent(event: SettingsEvent): SettingsEffect? {
        return null
    }
}

internal fun SettingsViewModel.onDarkModeChanged(mode: DarkThemeMode) =
    handleAction(SettingsAction.ChangeDarkTheme(mode))

internal fun SettingsViewModel.onThemeChanged(theme: WordThemes) =
    handleAction(SettingsAction.ChangeTheme(theme))

internal fun SettingsViewModel.onTypographyChanged(typography: WordTypography) =
    handleAction(SettingsAction.ChangeTypography(typography))
