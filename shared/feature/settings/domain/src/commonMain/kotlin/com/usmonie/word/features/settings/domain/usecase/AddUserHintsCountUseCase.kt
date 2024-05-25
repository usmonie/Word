package com.usmonie.word.features.settings.domain.usecase

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.settings.domain.repository.UserRepository

interface AddUserHintsCountUseCase : CoroutineUseCase<Int, Unit>

class AddUserHintsCountUseCaseImpl(private val userRepository: UserRepository) :
    AddUserHintsCountUseCase {
    override suspend fun invoke(input: Int) {
        userRepository.addHints(input)
    }
}
