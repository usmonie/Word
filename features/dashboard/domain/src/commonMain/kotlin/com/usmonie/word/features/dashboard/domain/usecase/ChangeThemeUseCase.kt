package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.CoroutineUseCase

interface ChangeThemeUseCase: CoroutineUseCase<Theme, Theme>

class ChangeThemeUseCaseImpl(private val userRepository: UserRepository): ChangeThemeUseCase {
    override suspend fun invoke(input: Theme): Theme {
        userRepository.currentTheme = input
        
        return userRepository.currentTheme
    }
}
