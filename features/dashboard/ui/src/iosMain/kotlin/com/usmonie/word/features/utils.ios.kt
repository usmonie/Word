package com.usmonie.word.features

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Suppress("NonSkippableComposable")
@Composable
actual fun OpenBrowser(url: Url) {
    UIApplication.sharedApplication.openURL(NSURL(string = url.url))
}