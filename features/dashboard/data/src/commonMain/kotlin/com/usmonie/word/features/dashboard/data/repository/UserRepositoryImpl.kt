package com.usmonie.word.features.dashboard.data.repository

import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.domain.models.Language
import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.repository.UserRepository

class UserRepositoryImpl(private val kVault: KVault) : UserRepository {

    override var currentTheme: Theme
        set(value) {
            value.colorsName?.let { kVault.set(CURRENT_USER_THEME_COLORS_KEY, it) }
            value.fonts?.let { kVault.set(CURRENT_USER_THEME_FONTS_KEY, it) }
        }
        get() = Theme(
            kVault.string(CURRENT_USER_THEME_COLORS_KEY),
            kVault.string(CURRENT_USER_THEME_FONTS_KEY)
        )

    override var wordsPerDayCount: Int
        get() = kVault.int(WORDS_PER_DAY_COUNT) ?: 0
        set(value) {
            kVault.set(WORDS_PER_DAY_COUNT, value)
        }

    override var nativeLanguage: Language
        get() = Language.valueOf(kVault.string(NATIVE_LANGUAGE) ?: Language.ES.name)
        set(value) {
            kVault.set(NATIVE_LANGUAGE, value.name)

        }

    override var notificationsTime: NotificationTime
        get() = NotificationTime.valueOf(
            kVault.string(NOTIFICATIONS_TIME) ?: NotificationTime.MORNING.name
        )
        set(value) {
            kVault.set(NOTIFICATIONS_TIME, value.name)
        }

    override var wasOnboardingShowed: Boolean
        get() = kVault.bool(WAS_ONBOARDING_SHOWED) ?: false
        set(value) {
            kVault.set(WAS_ONBOARDING_SHOWED, value)
        }
    override var lastPracticeTime: Long
        get() = kVault.long(LAST_PRACTICE_TIME) ?: 0L
        set(value) {
            kVault.set(LAST_PRACTICE_TIME, value)
        }

    companion object {
        private const val CURRENT_USER_THEME_COLORS_KEY: String = "CURRENT_USER_THEME_COLORS"
        private const val CURRENT_USER_THEME_FONTS_KEY: String = "CURRENT_USER_THEME_FONTS"

        private const val WORDS_PER_DAY_COUNT = "WORDS_PER_DAY_COUNT"
        private const val NATIVE_LANGUAGE = "NATIVE_LANGUAGE"
        private const val NOTIFICATIONS_TIME = "NOTIFICATIONS_TIME"

        private const val WAS_ONBOARDING_SHOWED = "WAS_ONBOARDING_SHOWED"
        private const val LAST_PRACTICE_TIME = "LAST_PRACTICE_TIME"
    }
}
