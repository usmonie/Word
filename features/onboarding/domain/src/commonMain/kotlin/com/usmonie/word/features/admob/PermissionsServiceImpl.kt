package com.usmonie.word.features.admob

import com.usmonie.word.features.admob.PermissionsService.Companion.PERMISSION_CHECK_FLOW_FREQUENCY
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class PermissionsServiceImpl(private val permissionDelegate: PermissionDelegate) : PermissionsService {
    override fun checkPermission(permission: Permission): PermissionState {
        return try {
            return permissionDelegate.permissionState
        } catch (e: Exception) {
            println("Failed to check permission $permission")
            e.printStackTrace()
            PermissionState.NOT_DETERMINED
        }
    }

    override fun checkPermissionFlow(permission: Permission): Flow<PermissionState> {
        return flow {
            while (true) {
                val permissionState = checkPermission(permission)
                emit(permissionState)
                delay(PERMISSION_CHECK_FLOW_FREQUENCY)
            }
        }
    }

    override suspend fun providePermission(permission: Permission) {
        try {
            permissionDelegate.providePermission()
        } catch (e: Exception) {
            println("Failed to request permission $permission")
            e.printStackTrace()
        }
    }

    override fun openSettingPage(permission: Permission) {
        println("Open settings for permission $permission")
        try {
//            permissionDelegate.openSettingPage()
        } catch (e: Exception) {
            println("Failed to open settings for permission $permission")
            e.printStackTrace()
        }
    }
}