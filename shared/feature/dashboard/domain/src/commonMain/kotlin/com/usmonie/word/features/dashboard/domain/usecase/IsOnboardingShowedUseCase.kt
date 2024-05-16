package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.core.domain.usecases.UseCase
import com.usmonie.word.features.dashboard.domain.repository.AppSettingsRepository

interface IsOnboardingShowedUseCase : UseCase<Unit, Boolean>

class IsOnboardingShowedUseCaseImpl(
    private val appSettingsRepository: AppSettingsRepository
) : IsOnboardingShowedUseCase {
    override fun invoke(input: Unit) = appSettingsRepository.wasOnboardingShowed
}
