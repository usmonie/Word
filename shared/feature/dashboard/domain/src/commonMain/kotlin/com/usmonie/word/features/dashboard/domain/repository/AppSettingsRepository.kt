package com.usmonie.word.features.dashboard.domain.repository

import com.usmonie.word.features.dashboard.domain.models.Theme

interface AppSettingsRepository {
    var currentTheme: Theme
    var wasOnboardingShowed: Boolean
}
