package com.usmonie.word.features.onboarding.ui

import kotlinx.coroutines.flow.StateFlow
import wtf.word.core.domain.firebase.models.GoogleUser

expect object FirebaseAuthenticationUtils {
    val currentUserFlow: StateFlow<GoogleUser?>
}