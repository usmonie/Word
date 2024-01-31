package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.UseCase

interface IsOnboardingShowedUseCase : UseCase<Unit, Boolean>

class IsOnboardingShowedUseCaseImpl(
    private val userRepository: UserRepository
) : IsOnboardingShowedUseCase {
    override fun invoke(input: Unit) = userRepository.wasOnboardingShowed
}