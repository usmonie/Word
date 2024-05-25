package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.core.domain.usecases.FlowUseCase
import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow

fun interface CurrentThemeUseCase : FlowUseCase<Unit, Theme>

internal class CurrentThemeUseCaseImpl(
    private val appSettingsRepository: AppSettingsRepository
) : CurrentThemeUseCase {
    override fun invoke(input: Unit): Flow<Theme> = appSettingsRepository.currentTheme
}
