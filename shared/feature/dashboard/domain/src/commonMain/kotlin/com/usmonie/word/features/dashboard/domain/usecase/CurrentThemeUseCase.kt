package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.core.domain.usecases.UseCase
import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.repository.AppSettingsRepository

interface CurrentThemeUseCase : UseCase<Unit, Theme>

internal class CurrentThemeUseCaseImpl(private val appSettingsRepository: AppSettingsRepository) :
    CurrentThemeUseCase {
    override fun invoke(input: Unit): Theme = appSettingsRepository.currentTheme
}
