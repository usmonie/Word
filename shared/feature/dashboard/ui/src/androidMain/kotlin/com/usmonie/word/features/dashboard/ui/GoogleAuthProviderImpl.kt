package com.usmonie.word.features.dashboard.ui

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import com.usmonie.core.domain.firebase.providers.GoogleAuthProvider

internal class GoogleAuthProviderImpl(
    private val credentialManager: CredentialManager
) : GoogleAuthProvider {
    override suspend fun signOut() {
        credentialManager.clearCredentialState(ClearCredentialStateRequest())
    }
}