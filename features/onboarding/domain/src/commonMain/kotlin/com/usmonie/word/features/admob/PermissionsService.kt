package com.usmonie.word.features.admob

import kotlinx.coroutines.flow.Flow

interface PermissionsService {
    fun checkPermission(permission: Permission): PermissionState
    fun checkPermissionFlow(permission: Permission): Flow<PermissionState>
    suspend fun providePermission(permission: Permission)
    fun openSettingPage(permission: Permission)

    companion object {
        const val PERMISSION_CHECK_FLOW_FREQUENCY = 1000L
    }
}