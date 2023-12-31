package com.usmonie.word.features.admob.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

expect class AdMob {

    @Composable
    fun Banner(adKey: String, modifier: Modifier = Modifier)

    @Composable
    fun RewardedInterstitial(onAddDismissed: () -> Unit)

    @Composable
    fun Startup(adKey: String, modifier: Modifier = Modifier)
}