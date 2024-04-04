package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.UseCase

interface SetLastPracticeTimeUseCase: UseCase<Unit, Long>


class SetLastPracticeTimeUseCaseImpl(private val userRepository: UserRepository): SetLastPracticeTimeUseCase {
    override fun invoke(input: Unit): Long {
        return userRepository.lastPracticeTime
    }
}
