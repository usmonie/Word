package com.usmonie.word.features.admob

interface PermissionDelegate {

    val permissionState: PermissionState

    fun providePermission()

}