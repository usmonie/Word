package com.usmonie.core.domain.firebase.models

data class  GoogleUser(
    val idToken: String,
    val displayName: String? = "",
    val profilePicUrl: String? = null,
)