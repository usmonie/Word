package com.usmonie.word.features.dashboard.domain.usecase

import com.usmonie.word.features.dashboard.domain.models.Language
import com.usmonie.word.features.dashboard.domain.repository.UserRepository
import wtf.word.core.domain.usecases.UseCase

interface SetupNativeLanguageUseCase : UseCase<Language, Unit>

class SetupNativeLanguageUseCaseImpl(
    private val userRepository: UserRepository
) : SetupNativeLanguageUseCase {
    override fun invoke(input: Language) {
        userRepository.nativeLanguage = input
    }
}