package com.usmonie.word.features.onboarding.ui

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import wtf.word.core.domain.firebase.models.GoogleUser

actual object FirebaseAuthenticationUtils {
    private val userFlow: MutableStateFlow<GoogleUser?> = MutableStateFlow(null)
    actual val currentUserFlow: StateFlow<GoogleUser?> = userFlow

    fun login(credential: AuthCredential) {
        Firebase.auth
            .signInWithCredential(credential)
            .addOnCompleteListener { task ->
                task.addOnSuccessListener { auth ->
                    auth.user?.let { user ->
                        userFlow.value = GoogleUser(user.uid, user.displayName)
                    }
                }
            }
    }
}