package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dashboard.domain.repository.UserRepository

interface UseUserHintsCountUseCase : CoroutineUseCase<Unit, Unit>

class UseUserHintsCountUseCaseImpl(private val userRepository: UserRepository) :
    UseUserHintsCountUseCase {
    override suspend fun invoke(input: Unit) {
        userRepository.hintsCount--
    }
}
