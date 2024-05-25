package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.repository.AppSettingsRepository

interface ChangeThemeUseCase : CoroutineUseCase<Theme, Unit>

internal class ChangeThemeUseCaseImpl(
    private val appSettingsRepository: AppSettingsRepository
) : ChangeThemeUseCase {
    override suspend fun invoke(input: Theme) {
        appSettingsRepository.setCurrentTheme(input)
    }
}
