package com.usmonie.word.features.settings.domain.usecase

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.settings.domain.models.Theme
import com.usmonie.word.features.settings.domain.repository.AppSettingsRepository

interface ChangeThemeUseCase : CoroutineUseCase<Theme, Unit>

internal class ChangeThemeUseCaseImpl(
    private val appSettingsRepository: AppSettingsRepository
) : ChangeThemeUseCase {
    override suspend fun invoke(input: Theme) {
        appSettingsRepository.setCurrentTheme(input)
    }
}
