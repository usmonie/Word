package com.usmonie.word.features.ui

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
actual class AdMob(private val bannerUiView: () -> UIView) {
    @Composable
    actual fun Banner(adKey: String, modifier: Modifier) {
        UIKitView(
            modifier = modifier.height(64.dp),
            factory = bannerUiView
        )
    }

    @Composable
    actual fun RewardedInterstitial(onAddDismissed: () -> Unit) {
    }

    @Composable
    actual fun Startup(adKey: String, modifier: Modifier) {
    }

}