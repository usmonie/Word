package com.usmonie.word.features.onboarding.domain

import wtf.word.core.domain.usecases.CoroutineUseCase

interface LoginViaGoogleUseCase : CoroutineUseCase<String, Unit>

class LoginViaGoogleUseCaseImpl(private val loginApi: UserRepository) : LoginViaGoogleUseCase {
    override suspend fun invoke(input: String) {
        loginApi.loginViaGoogle(input)
    }
}
