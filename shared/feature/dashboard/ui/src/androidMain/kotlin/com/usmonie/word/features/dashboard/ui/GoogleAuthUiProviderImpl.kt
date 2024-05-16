package com.usmonie.word.features.dashboard.ui

import android.content.IntentSender
import android.content.IntentSender.SendIntentException
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient


//class GoogleAuthUiProviderImpl(
//    private val onSignLaunch: (IntentSender) -> Unit,
//    private val signIn: SignInClient
//) : GoogleAuthUiProvider {
//
//    private val signInRequest = BeginSignInRequest.builder()
//        .setGoogleIdTokenRequestOptions(
//            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                .setSupported(true)
//                .setServerClientId("AppKeys.SERVER_CLIENT_ID")
//                .setFilterByAuthorizedAccounts(false)
//                .build()
//        )
//        // Automatically sign in when exactly one credential is retrieved.
//        .setAutoSelectEnabled(true)
//        .build();
//
//    override suspend fun signIn(onFailure: (Throwable) -> Unit) {
//        signIn.beginSignIn(signInRequest)
//            .addOnSuccessListener {
//                try {
//                    onSignLaunch(it.pendingIntent.intentSender)
//                } catch (e: SendIntentException) {
//                    onFailure(e)
//                }
//            }
//            .addOnFailureListener(onFailure)
//    }
//}