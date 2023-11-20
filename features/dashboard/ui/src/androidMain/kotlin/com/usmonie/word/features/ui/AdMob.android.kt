package com.usmonie.word.features.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@SuppressLint("MissingPermission")
actual class AdMob(private val showInterstitialAd: (context: Context, onAddDismissed: () -> Unit) -> Unit) {
    @Composable
    actual fun Banner(adKey: String, modifier: Modifier) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                AdView(context).apply {
                    adUnitId = adKey
                    setAdSize(AdSize.FULL_BANNER)
                    loadAd(AdRequest.Builder().build())
                }
            },
        )
    }

    @Composable
    actual fun RewardedInterstitial() {
        showInterstitialAd(LocalContext.current) {}
    }

    @Composable
    actual fun Startup(adKey: String, modifier: Modifier) {
    }
}