package com.usmonie.core.tools.ui

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun OpenBrowser(url: Url) {
    UIApplication.sharedApplication.openURL(NSURL(string = url.link))
}
