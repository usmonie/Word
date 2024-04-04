package com.usmonie.word.features.onboarding.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import wtf.word.core.domain.firebase.models.GoogleUser

actual object FirebaseAuthenticationUtils {
    private val userFlow: MutableStateFlow<GoogleUser?> = MutableStateFlow(null)
    actual val currentUserFlow: StateFlow<GoogleUser?> = userFlow

    fun login(googleUser: GoogleUser) {
        userFlow.value = googleUser
    }
}