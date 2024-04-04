package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.UseCase

interface SetupWordsPerDayUseCase: UseCase<Int, Unit>

class SetupWordsPerDayUseCaseImpl(private val userRepository: UserRepository) : SetupWordsPerDayUseCase {
    override fun invoke(input: Int) {
        userRepository.wordsPerDayCount = input
    }
}