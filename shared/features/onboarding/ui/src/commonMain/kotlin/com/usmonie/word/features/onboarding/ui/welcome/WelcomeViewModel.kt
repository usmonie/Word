package com.usmonie.word.features.onboarding.ui.welcome

import androidx.compose.runtime.Immutable
import wtf.speech.core.ui.BaseViewModel

@Immutable
class WelcomeViewModel(
) :
    BaseViewModel<WelcomeScreenState, WelcomeAction, WelcomeEvent, WelcomeEffect>(
        WelcomeScreenState(false)
    ) {
    override fun WelcomeScreenState.reduce(event: WelcomeEvent) = when (event) {
        is WelcomeEvent.Authenticated -> this
        WelcomeEvent.ErrorWhileAuthenticating -> this
        WelcomeEvent.ForAlinaShowed -> this.copy(wasShowedForAlina = true)
    }

    override suspend fun processAction(action: WelcomeAction): WelcomeEvent {
        return when (action) {
            is WelcomeAction.SignInWithGoogle -> {
                if (action.googleUser == null) return WelcomeEvent.ErrorWhileAuthenticating
                println("IDTOKEN" + action.googleUser.idToken)

                return WelcomeEvent.Authenticated(action.googleUser)
            }

            WelcomeAction.ForAlinaShowed -> WelcomeEvent.ForAlinaShowed
        }
    }

    override suspend fun handleEvent(event: WelcomeEvent): WelcomeEffect? {
        return when (event) {
            is WelcomeEvent.Authenticated -> WelcomeEffect.Authenticated()
            else -> null
        }
    }
}