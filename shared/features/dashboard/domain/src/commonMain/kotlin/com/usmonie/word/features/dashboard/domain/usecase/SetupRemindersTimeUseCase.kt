package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.UseCase

interface SetupRemindersTimeUseCase : UseCase<NotificationTime, Unit>

class SetupRemindersTimeUseCaseImpl(
    private val userRepository: UserRepository
) : SetupRemindersTimeUseCase {
    override fun invoke(input: NotificationTime) {
        userRepository.notificationsTime = input
    }
}