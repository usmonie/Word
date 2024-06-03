package com.usmonie.word.di

import kotlin.experimental.ExperimentalNativeApi

@OptIn(ExperimentalNativeApi::class)
actual val isRelease: Boolean = !Platform.isDebugBinary
