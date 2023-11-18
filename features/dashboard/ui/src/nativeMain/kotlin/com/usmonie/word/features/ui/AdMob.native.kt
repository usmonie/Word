package com.usmonie.word.features.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
actual class AdMob(private val bannerUiView: () -> UIView)  {
    @Composable
    actual fun Banner(adKey: String, modifier: Modifier) {
        println("AD BANNER STARTED $bannerUiView")
        UIKitView(
            modifier = modifier,
            factory = bannerUiView
        )
        println("AD BANNER ENDED")

    }

    @Composable
    actual fun RewardedInterstitial(adKey: String, modifier: Modifier) {
    }

    @Composable
    actual fun Startup(adKey: String, modifier: Modifier) {
    }
}