package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface AddUserHintsCountUseCase : CoroutineUseCase<Int, Unit>

class AddUserHintsCountUseCaseImpl(private val userRepository: UserRepository) :
    AddUserHintsCountUseCase {
    override suspend fun invoke(input: Int) {
        userRepository.hintsCount += input
    }
}