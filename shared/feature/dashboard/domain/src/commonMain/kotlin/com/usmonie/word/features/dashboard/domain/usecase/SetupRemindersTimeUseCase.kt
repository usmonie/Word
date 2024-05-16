package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.core.domain.usecases.UseCase
import com.usmonie.word.features.dashboard.domain.models.NotificationTime
import com.usmonie.word.features.dashboard.domain.repository.UserRepository

interface SetupRemindersTimeUseCase : UseCase<NotificationTime, Unit>

class SetupRemindersTimeUseCaseImpl(
    private val userRepository: UserRepository
) : SetupRemindersTimeUseCase {
    override fun invoke(input: NotificationTime) {
        userRepository.notificationsTime = input
    }
}
