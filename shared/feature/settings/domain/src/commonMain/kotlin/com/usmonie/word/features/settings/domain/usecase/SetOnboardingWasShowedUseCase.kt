package com.usmonie.word.features.settings.domain.usecase

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.settings.domain.repository.AppSettingsRepository

interface SetOnboardingWasShowedUseCase : CoroutineUseCase<Unit, Unit>

class SetOnboardingWasShowedUseCaseImpl(private val appSettingsRepository: AppSettingsRepository) :
    SetOnboardingWasShowedUseCase {
    override suspend fun invoke(input: Unit) {
        appSettingsRepository.setOnboardingShowed(true)
    }
}
