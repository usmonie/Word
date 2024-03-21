package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.LanguageLevel
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.UseCase

interface SetupLanguageLevelUseCase : UseCase<LanguageLevel, Unit>

class SetupLanguageLevelUseCaseImpl(
    private val userRepository: UserRepository
) : SetupLanguageLevelUseCase {
    override fun invoke(input: LanguageLevel) {
        userRepository.languageLevel = input
    }
}