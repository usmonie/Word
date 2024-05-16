package com.usmonie.word.features.dashboard.data.repository

import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.repository.AppSettingsRepository

class AppSettingsRepositoryImpl(
    private val kVault: KVault,
) : AppSettingsRepository {
    override var currentTheme: Theme
        set(value) {
            value.colorsName?.let { kVault.set(CURRENT_USER_THEME_COLORS_KEY, it) }
            value.fonts?.let { kVault.set(CURRENT_USER_THEME_FONTS_KEY, it) }
        }
        get() {
            return Theme(
                kVault.string(CURRENT_USER_THEME_COLORS_KEY),
                kVault.string(CURRENT_USER_THEME_FONTS_KEY)
            )
        }

    override var wasOnboardingShowed: Boolean
        get() = kVault.bool(WAS_ONBOARDING_SHOWED) ?: false
        set(value) {
            kVault.set(WAS_ONBOARDING_SHOWED, value)
        }

    companion object {
        private const val CURRENT_USER_THEME_COLORS_KEY: String = "CURRENT_USER_THEME_COLORS"
        private const val CURRENT_USER_THEME_FONTS_KEY: String = "CURRENT_USER_THEME_FONTS"

        private const val WAS_ONBOARDING_SHOWED = "WAS_ONBOARDING_SHOWED"
    }
}