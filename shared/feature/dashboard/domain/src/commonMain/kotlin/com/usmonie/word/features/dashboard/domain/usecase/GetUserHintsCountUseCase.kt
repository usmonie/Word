package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.core.domain.usecases.CoroutineUseCase
import com.usmonie.word.features.dashboard.domain.repository.UserRepository

interface GetUserHintsCountUseCase : CoroutineUseCase<Unit, Int>

class GetUserHintsCountUseCaseImpl(private val userRepository: UserRepository) :
    GetUserHintsCountUseCase {
    override suspend fun invoke(input: Unit) = userRepository.hintsCount
}
