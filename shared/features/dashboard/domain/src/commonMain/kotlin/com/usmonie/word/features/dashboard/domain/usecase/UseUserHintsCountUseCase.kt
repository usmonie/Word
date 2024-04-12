package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface UseUserHintsCountUseCase : CoroutineUseCase<Unit, Unit>

class UseUserHintsCountUseCaseImpl(private val userRepository: UserRepository) :
    UseUserHintsCountUseCase {
    override suspend fun invoke(input: Unit) {
        userRepository.hintsCount--
    }
}