package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.UseCase

interface SetOnboardingWasShowedUseCase: UseCase<Unit, Unit> {
}

class SetOnboardingWasShowedUseCaseImpl(private val userRepository: UserRepository): SetOnboardingWasShowedUseCase {
    override fun invoke(input: Unit) {
        userRepository.wasOnboardingShowed = true
    }
}