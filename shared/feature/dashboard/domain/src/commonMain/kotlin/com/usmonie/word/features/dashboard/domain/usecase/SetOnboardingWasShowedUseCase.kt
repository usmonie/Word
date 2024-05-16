package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.core.domain.usecases.UseCase
import com.usmonie.word.features.dashboard.domain.repository.AppSettingsRepository

interface SetOnboardingWasShowedUseCase : UseCase<Unit, Unit>

class SetOnboardingWasShowedUseCaseImpl(private val appSettingsRepository: AppSettingsRepository) :
    SetOnboardingWasShowedUseCase {
    override fun invoke(input: Unit) {
        appSettingsRepository.wasOnboardingShowed = true
    }
}
