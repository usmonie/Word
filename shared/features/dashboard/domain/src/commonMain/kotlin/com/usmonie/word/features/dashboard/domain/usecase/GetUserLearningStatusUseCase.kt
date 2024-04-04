package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.UserLearningStatus
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.UseCase

interface GetUserLearningStatusUseCase : UseCase<Unit, UserLearningStatus>

class GetUserLearningStatusUseCaseImpl(
    private val userRepository: UserRepository,
    private val wordsRepository: UserRepository
) :
    GetUserLearningStatusUseCase {
    override fun invoke(input: Unit): UserLearningStatus {
        val wasOnboardingShowed = userRepository.wasOnboardingShowed
        if (!wasOnboardingShowed) return UserLearningStatus.NotStarted

        val time = userRepository.notificationsTime
        val wordsPerDay = userRepository.wordsPerDayCount
        val nativeLanguage = userRepository.nativeLanguage
        val lastPracticeTime = userRepository.lastPracticeTime

        return UserLearningStatus.Started(
            12,
            10,
            8,
            124,
            wordsPerDay,
            lastPracticeTime,
            time,
            nativeLanguage
        )
    }
}