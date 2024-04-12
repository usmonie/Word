package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface GetUserHintsCountUseCase: CoroutineUseCase<Unit, Int>

class GetUserHintsCountUseCaseImpl(private val userRepository: UserRepository): GetUserHintsCountUseCase {
    override suspend fun invoke(input: Unit) = userRepository.hintsCount
}