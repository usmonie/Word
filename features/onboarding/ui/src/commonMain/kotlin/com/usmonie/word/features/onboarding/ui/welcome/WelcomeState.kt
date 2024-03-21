package com.usmonie.word.features.onboarding.ui.welcome

import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState
import wtf.word.core.domain.firebase.models.GoogleUser

data class WelcomeScreenState(
    val wasShowedForAlina: Boolean,
) : ScreenState

sealed class WelcomeAction : ScreenAction {
    data class SignInWithGoogle(val googleUser: GoogleUser?) : WelcomeAction()

    data object ForAlinaShowed : WelcomeAction()
}

sealed class WelcomeEvent : ScreenEvent {
    data class Authenticated(val googleUser: GoogleUser) : WelcomeEvent()

    data object ErrorWhileAuthenticating : WelcomeEvent()

    data object ForAlinaShowed : WelcomeEvent()
}

sealed class WelcomeEffect : ScreenEffect {
    class Authenticated : WelcomeEffect()
}

