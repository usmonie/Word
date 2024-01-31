package com.usmonie.word.features.admob;

enum class PermissionState {
    NOT_DETERMINED,
    GRANTED,
    DENIED;

    fun notGranted(): Boolean {
        return this != GRANTED
    }
}