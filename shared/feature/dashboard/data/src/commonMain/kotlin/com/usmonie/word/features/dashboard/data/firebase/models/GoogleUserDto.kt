package com.usmonie.word.features.dashboard.data.firebase.models

data class GoogleUserDto(
    val idToken: String,
    val displayName: String = "",
    val profilePicUrl: String? = null,
)