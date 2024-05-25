package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.core.domain.usecases.FlowUseCase
import com.usmonie.word.features.dashboard.domain.repository.AppSettingsRepository

interface IsOnboardingShowedUseCase : FlowUseCase<Unit, Boolean>

class IsOnboardingShowedUseCaseImpl(
    private val appSettingsRepository: AppSettingsRepository
) : IsOnboardingShowedUseCase {
    override fun invoke(input: Unit) = appSettingsRepository.wasOnboardingShowed
}
