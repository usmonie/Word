package com.usmonie.word.features.dashboard.ui.settings

import androidx.compose.runtime.Composable
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import com.usmonie.word.features.dashboard.domain.repository.WordRepository
import com.usmonie.word.features.dashboard.domain.usecase.ChangeThemeUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.ClearRecentUseCaseImpl
import com.usmonie.word.features.dashboard.domain.usecase.CurrentThemeUseCaseImpl
import com.usmonie.word.features.subscription.domain.repository.SubscriptionRepository
import com.usmonie.word.features.subscription.domain.usecase.SubscriptionStatusUseCaseImpl
import com.usmonie.word.features.dashboard.ui.ui.AdMob
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.word.core.design.themes.WordColors
import wtf.word.core.design.themes.typographies.WordTypography

internal class SettingsScreen(
    private val changeTheme: (WordColors) -> Unit,
    private val changeFont: (WordTypography) -> Unit,
    private val settingsViewModel: SettingsViewModel,
    private val adMob: AdMob
) : Screen(settingsViewModel) {
    override val id: String = ID

    @Composable
    override fun Content() {
        SettingsScreenContent(changeTheme, changeFont, viewModel = settingsViewModel, adMob)
    }

    class Builder(
        private val changeTheme: (WordColors) -> Unit,
        private val changeFont: (WordTypography) -> Unit,
        private val subscriptionRepository: SubscriptionRepository,
        private val userRepository: UserRepository,
        private val wordRepository: WordRepository,
        private val adMob: AdMob
    ) : ScreenBuilder {
        override val id: String = ID
        override fun build(params: Map<String, String>?, extra: Extra?) = SettingsScreen(
            changeTheme,
            changeFont,
            SettingsViewModel(
                SubscriptionStatusUseCaseImpl(
                    subscriptionRepository,
                ),
                CurrentThemeUseCaseImpl(userRepository),
                ChangeThemeUseCaseImpl(userRepository),
                ClearRecentUseCaseImpl(wordRepository)
            ),
            adMob
        )
    }

    companion object {
        const val ID = "SettingsScreen"
    }
}