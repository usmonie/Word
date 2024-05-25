package com.usmonie.word.features.settings.domain.usecase

import com.usmonie.core.domain.usecases.FlowUseCase
import com.usmonie.word.features.settings.domain.repository.UserRepository

interface GetUserHintsCountUseCase : FlowUseCase<Unit, Int>

class GetUserHintsCountUseCaseImpl(private val userRepository: UserRepository) :
    GetUserHintsCountUseCase {
    override fun invoke(input: Unit) = userRepository.hintsCount
}
