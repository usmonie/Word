package com.usmonie.word.features.onboarding.ui

import com.usmonie.word.features.admob.PermissionDelegate
import com.usmonie.word.features.admob.PermissionState

actual class NotificationPermissionDelegate : PermissionDelegate {
    override val permissionState: PermissionState
        get() = PermissionState.NOT_DETERMINED

    override fun providePermission() {
    }
}