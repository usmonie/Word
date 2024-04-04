package com.usmonie.word.features.dashboard.ui

import androidx.compose.runtime.Composable
import com.usmonie.word.features.dashboard.ui.Url
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Suppress("NonSkippableComposable")
@Composable
actual fun OpenBrowser(url: Url) {
    UIApplication.sharedApplication.openURL(NSURL(string = url.url))
}