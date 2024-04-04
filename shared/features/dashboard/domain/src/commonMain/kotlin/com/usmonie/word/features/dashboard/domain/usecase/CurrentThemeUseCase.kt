package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.UseCase

interface CurrentThemeUseCase : UseCase<Unit, Theme>

class CurrentThemeUseCaseImpl(private val userRepository: UserRepository) : CurrentThemeUseCase {
    override fun invoke(input: Unit): Theme = userRepository.currentTheme
}
