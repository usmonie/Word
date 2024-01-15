package com.usmonie.word.features.admob.ui

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
actual class AdMob(
    private val bannerUiView: () -> UIView,
    private val rewardedInterstitialView: () -> Unit
) {
    @Composable
    actual fun Banner(adKey: String, modifier: Modifier) {
        UIKitView(
            modifier = modifier.height(64.dp),
            factory = bannerUiView
        )
    }

    @Composable
    actual fun RewardedInterstitial(onAddDismissed: () -> Unit) {
        rewardedInterstitialView()
    }

    @Composable
    actual fun Startup(adKey: String, modifier: Modifier) {
    }

    fun onInitiateRewarded() {

    }
}