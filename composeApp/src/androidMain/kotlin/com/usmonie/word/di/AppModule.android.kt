package com.usmonie.word.di

import com.usmonie.word.BuildConfig

actual val isRelease: Boolean = !BuildConfig.DEBUG
