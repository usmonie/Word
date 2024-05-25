package com.usmonie.word.features.dashboard.domain.repository

import com.usmonie.word.features.dashboard.domain.models.Theme
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {
    val currentTheme: Flow<Theme>
    val wasOnboardingShowed: Flow<Boolean>

    suspend fun setCurrentTheme(theme: Theme)
    suspend fun setOnboardingShowed(wasShowed: Boolean)
}
